#include <EEPROM.h>
#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include <WiFiUdp.h>

#define fireTimeMillis 500

const char* config_ssid = "noxnition";
const char* config_password = "noxnition";

const int pinMain = D1;
const int pinData = D3;
const int pinDataBack = D2;
const int pinShift = D6;
const int pinReset = D8;
const int pinOE = D7;
const int pinStore = D5;

ESP8266WebServer server(80);

long *channelTimers = 0;
boolean *channelStates = 0;

String ssid;
String password;
String moduleName;

float voltage;

boolean armed = false;

int channels = 0;

WiFiUDP Udp;
unsigned int localPort = 2390; 
char incomingPacket[255];

void setup() {
  initPins();

  delay(100);

  channels = getChannelCount();
  channelStates = new boolean[channels];
  channelTimers = new long[channels];
  for(int i=0; i < channels; i++) {
    channelTimers[i] = 0;
    channelStates[i] = LOW;
  }

  digitalWrite(pinReset, LOW);

  Serial.begin(115200);
  EEPROM.begin(512);
  delay(10);
  digitalWrite(pinReset, HIGH);
  setRegisterOutput();
  digitalWrite(pinOE, LOW);

  String esid;
  for (int i = 0; i < 32; ++i) {
    esid += char(EEPROM.read(i));
  }
  ssid = esid.c_str();
  Serial.print("SSID: ");
  Serial.println(ssid);
  Serial.println("Reading EEPROM pass");
  String epass = "";
  for (int i = 32; i < 96; ++i) {
    epass += char(EEPROM.read(i));
  }
  password = epass.c_str();
  Serial.print("PASS: ");
  Serial.println(password);  
  Serial.println("Reading EEPROM name");
  String ename = "";
  for (int i = 96; i < 128; ++i) {
    ename += char(EEPROM.read(i));
  }
  Serial.print("NAME: ");
  Serial.println(ename);  

  moduleName = ename.c_str();

  Serial.print("channels: ");
  Serial.println(channels);
  
  // Connect to WiFi network
  Serial.println();

  boolean clientMode = initWifiClient(ssid, password);
  
  Serial.println(WiFi.localIP());
  
  if (!clientMode) {
    Serial.println("AP Mode for Settings");
    initWifiAP();
    initConfigServer();
  } else {
    Serial.println("Module Mode");
    initModuleServer();
  }

  Udp.begin(localPort);
}

void initConfigServer() {
  server.begin();
  server.on("/", settings);
}

void initModuleServer() {
  server.begin();
  server.on("/state", getStatus);
  server.on("/arm", arm);
  server.on("/disarm", disarm);
  server.on("/fire", fire);
  server.on("/check", check);
  server.on("/getConfig", getConfig);
}

void getConfig() {
  String temp = "name=";
  temp += moduleName;
  temp += "\nchannels=";
  temp += channels;
  server.send(200, "application/json", temp);
}

boolean writeSetting(String qsid, String qpass, String qname) {
  if (qsid.length() > 0 && qpass.length() > 0 && qname.length() > 0) {
    Serial.println("clearing eeprom");
    for (int i = 0; i < 128; ++i) { EEPROM.write(i, 0); }
    Serial.println(qsid);
    Serial.println("");
    Serial.println(qpass);
    Serial.println("");
    
    Serial.println("writing eeprom ssid:");
    for (int i = 0; i < qsid.length(); ++i) {
      EEPROM.write(i, qsid[i]);
      Serial.print("Wrote: ");
      Serial.println(qsid[i]); 
    }
    Serial.println("writing eeprom pass:"); 
    for (int i = 0; i < qpass.length(); ++i) {
      EEPROM.write(32+i, qpass[i]);
      Serial.print("Wrote: ");
      Serial.println(qpass[i]); 
    }    
  
    Serial.println("writing eeprom name:"); 
    for (int i = 0; i < qname.length(); ++i) {
      EEPROM.write(96+i, qname[i]);
      Serial.print("Wrote: ");
      Serial.println(qname[i]); 
    }    

    moduleName = qname;
    password = qpass;
    ssid = qsid;
  
    EEPROM.commit();
    return true;
  }
  return false;
}

void settings() {
  String action = server.arg("action");
  String add = "";
  if (action == "save") {
    String qsid = server.arg("ssid");
    String qpass = server.arg("pass");
    String qname = server.arg("name");
    boolean success = writeSetting(qsid, qpass, qname);
    if (success) {
      add += "{\"Success\":\"saved to eeprom... reset to boot into new wifi\"}";
    }
  }
  String temp = getSettingsHtml();
  temp += add;
  server.send(200, "text/html", temp);
}

String getSettingsHtml() {
  String temp = "<html><body><form action=\"\" method=\"post\"><input type=\"hidden\" name=\"action\" id=\"action\" value=\"save\"><table border=\"1\" cellspacing=\"0\"><tr><td>Name</td><td><input type=\"text\" name=\"name\" id=\"name\" maxlength=\"40\" value=\"";
  temp += moduleName;
  temp += "\"></td></tr><tr><td>SSID</td><td><input type=\"text\" name=\"ssid\" id=\"ssid\" maxlength=\"40\" value=\"";
  temp += ssid;
  temp += "\"></td></tr><tr><td>Password</td><td><input type=\"text\" name=\"pass\" id=\"pass\" maxlength=\"40\" value=\"";
  temp += password;
  temp += "\"></td></tr></table><button type=\"submit\">Send</button></form></body></html>";
  return temp;
}

void initPins() {
  pinMode(pinMain, OUTPUT);
  digitalWrite(pinMain, LOW);

  pinMode(pinData, OUTPUT);
  digitalWrite(pinData, LOW);

  pinMode(pinDataBack, INPUT);
  digitalWrite(pinDataBack, LOW);

  pinMode(pinShift, OUTPUT);
  digitalWrite(pinShift, LOW);

  pinMode(pinReset, OUTPUT);
  digitalWrite(pinReset, HIGH);

  pinMode(pinOE, OUTPUT);
  digitalWrite(pinOE, HIGH);

  pinMode(pinStore, OUTPUT);
  digitalWrite(pinStore, LOW);
}

int getChannelCount() {
  digitalWrite(pinReset, LOW);
  digitalWrite(pinReset, HIGH);
  boolean state = LOW;
  int channelCount = 0;
  digitalWrite(pinData, HIGH);
  while(!state) {
    channelCount++;
    digitalWrite(pinShift, LOW);
    digitalWrite(pinShift, HIGH);
    state = digitalRead(pinDataBack);
  }
  digitalWrite(pinData, LOW);
  return channelCount;
}

void initWifiAP() {
  WiFi.disconnect(); 
  WiFi.mode(WIFI_AP);
  IPAddress ip(192,168,0,128);  //Node static IP
  IPAddress gateway(192,168,0,128);
  IPAddress subnet(255,255,255,0);
  WiFi.softAPConfig(ip, gateway, subnet);   // subnet FF FF FF 00
  WiFi.softAP(config_ssid, config_password);
}

boolean initWifiClient(String ssid, String password) {
  WiFi.disconnect(); 
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid.c_str(), password.c_str());
  int retry = 0;
  while (WiFi.status() != WL_CONNECTED) {
    retry++;
    if (retry > 50) {
      return false;
    }
    delay(500);
  }
  return true;
}

void fire() {
  String channelString = server.arg("channel");
  int channel = channelString.toInt();
  Serial.print("channel to fire: ");
  Serial.println(channel);
  int index = channel-1;
  long fireEnd = millis() + fireTimeMillis;
  channelTimers[index] = fireEnd;
  channelStates[index] = HIGH;
  setRegisterOutput();
  String temp = "operation=1\nchannel=";
  temp += channel;
  server.send(200, "application/json", temp);

}

void setRegisterOutput() {
  digitalWrite(pinStore, LOW);

  for(int i=channels-1; i >= 0; i--) {
    digitalWrite(pinShift, LOW);
    digitalWrite(pinData, channelStates[i]);
    digitalWrite(pinShift, HIGH);
  }
  digitalWrite(pinStore, HIGH);
}

void arm() {
  armInternal(true);

  server.send(200, "application/json", "operation=1");
}

void disarm() {
  armInternal(false);

  server.send(200, "application/json", "operation=1");
}

void armInternal(boolean arm) {
  armed = arm;
  digitalWrite(pinMain, armed);
}

void check() {
  for(int i=0; i < channels; i++) {
    channelStates[i] = LOW;
  }
  setRegisterOutput();
  
  boolean wasArmed = armed;
  if (armed) {
    armInternal(false);
    delay(20);
  }
  int voltageIn = analogRead(A0);
  if (voltageIn > 200) {
    server.send(200, "application/json", "operation=0");
    return;
  }

  if (voltageIn < 50) {
    server.send(200, "application/json", "operation=0");
    return;
  }

  String temp = "operation=1";

  for(int i=0; i < channels; i++) {
    boolean pinState = false;
    temp += "\nc";
    temp += i+1;
    temp += "=";
    int pinVoltageIn = analogRead(A0);
    if (pinVoltageIn > 50) {
      channelStates[i] = HIGH;
      setRegisterOutput();
      delay(5);
      pinVoltageIn = analogRead(A0);
      pinState = pinVoltageIn < 30;
      channelStates[i] = LOW;
      setRegisterOutput();
      delay(5);
    }
    temp += pinState;
  }
  server.send(200, "application/json", temp);
}

void getStatus() {
  String temp = "voltage=";
  temp += voltage;
  temp += "\n";
  temp += "a=";
  temp += armed;

  for(int i=0; i < channels; i++) {
    temp += "\n";
    temp += "c";
    temp += i+1;
    temp += "=";
    temp += channelStates[i];
  }

  server.send(200, "application/json", temp);
}

void processVoltage() {
  static long nextMillis = 0;
  static const float factor = 3.3 * 5.85;

  long currentMillis = millis();
  if (currentMillis > nextMillis) {
    float averageMes = average(analogRead(A0));
    voltage = factor * averageMes / 1024;
    nextMillis = currentMillis + 50;
  }
}

float average(int value) {
  static const int numberOfValues = 10;
  static int values[numberOfValues], counter=0;
  
  int sum=0;
  values[counter] = value;
  for(int i=0; i < numberOfValues; i++) {
    sum += values[i];
  }
  counter++;
  if(counter >= numberOfValues) {
    counter=0;
  }
  return (float) sum / numberOfValues; 
}

void handleFire() {
  long currentMillis = millis();
  for(int i=0; i < channels; i++) {
    long fireEnd = channelTimers[i];
    if (fireEnd != 0 && currentMillis > fireEnd) {
      channelTimers[i] = 0;
      channelStates[i] = LOW;
      setRegisterOutput();
    }
  }
}
 
void loop() {
  int packetSize = Udp.parsePacket();
if (packetSize) {
  int len = Udp.read(incomingPacket, 255);
  if (len > 0) {
    incomingPacket[len] = 0;
  }

  WiFiClient client;
  if (client.connect(Udp.remoteIP().toString().c_str(), 2410)) {
    String temp = "name=";
    temp += moduleName;
    temp += "\nchannels=";
    temp += channels;
    temp += "\nrequest=";
    temp += incomingPacket;
    client.print(temp);
  }
  client.stop();
}
  processVoltage();
  handleFire();
  server.handleClient();
}
