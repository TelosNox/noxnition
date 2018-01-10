# Feuerwerk Zündmodul + Android App
Selbstbauprojekt für Funkzündmodule

## Zielgruppe
Das Projekt richtet sich an interessierte Privatmenschen mit technischen Kenntnissen, die gerne elektrisch ihr Silvesterfeuerwerk zünden wollen.
Man sollte wissen, wie man grundsätzlich einen Schaltplan liest und wie man an Bauteilen die passenden Pins erkennt (Datenblatt). Und man sollte mit dem Lötkolben umgehen können.

Dieses Projekt richtet sich NICHT an Profis. Wer etwas für professionelle Einsatzzwecke sucht, ist hier leider falsch.
Dennoch darf natürlich gerne reingeschaut werden.

## Eckdaten
#### Zündmodul
- Funkzündmodul mit n x 8 Kanälen
- Manuelle Scharfschaltung
- Elektrische Scharfschaltung
- Durchgangsprüfung der Zünder
- Zündspannung 12V max 7A, Prüfstrom 1,2mA
- Kommunikation über WiFi (WPA gesichert)
- Einfache API über HTTP
#### Android App
- Automatische suche von Modulen
- Direkte Einzelbedienung (Scharfschalten, Durchgangsprüfung, Zündung)
- Feuerwerksplanung mit virtuellen Kanälen (Zusammenfassung mehrerer Kanäle auch unterschiedlicher Module unter einem Namen)
- Ablaufplanung mit Zeitverzögerungen (Sekundenbereich)
- Semiautomatisches Schießen des Feuerwerks

## Motivation
Das Projekt entstand durch mehrere Faktoren. Einerseits war ich gerade für den Kettenheizöler Noxmatic mit dem NodeMCU am experimentieren, andererseits hatte ich gerade ein Silvester hinter mir, an dem wir viele Batterien hatte aber diese manuell zündeten.
Ich hatte die Nase voll vom hin und herlaufen und den Pausen dazwischen. Ich hatte Bock darauf, mein Feuerwerk mal elektisch zu zünden. Keinen Bock hingegen hatte ich daruf, für eine kleine 4 Kanal China Zündanlage schon mindestens 40€ hinzulegen zumal ich ja wusste, wie billig man schon den NodeMCU und die als Treiber nötigen Komponenten bekommt.
Ich hatte auch Bock darauf, sowas mal selbst zu entwickeln, also brachte ich die Idee im Feuerwerk Forum (www.feuerwerk-forum.de) mal zur Sprache und bekam dort viele hilfreiche Ratschläge von Mitgliedern, die elektrotechnisch noch einiges fitter sind, als ich. Für diese Hilfe bin ich sehr Dankbar, denn bei einer Zündanlage geht es auch um Sicherheit und da sollte man nicht am falschen Ende sparen.

## Die Idee
Ich wollte eine Zündanlage für den Selbstbau entwickeln, die möglichst einfach und möglichst günstig nachzubauen und zu bedienen ist. Dabei sollte sie möglichst offen für Erweiterungen sein.
Kommuniziert wird über WLAN, das Protokoll ist HTTP und als Fernsteuerung dient einfach ein beliebiges Android Gerät (hat eigentlich jeder zu Hause).

## Entstehung des Zündmoduls
Ursprünglicher Plan war, dass ein Zündmodul genau 8 Kanäle + eine elektrische Scharfschaltung hat. Der NodeMCU hat genau 9 schaltbare digitale Ausgänge, das reicht für die 8 Kanäle sowie den einen Ausgang für die Scharfschaltung.
An die Ausgänge einfach direkt MosFet als Low Side Schalter dran (ohne weitere Beschaltung) und die Scharfschaltung ebenso als Low Side Schalter für die Gesamtmasse.

Hat so leider nicht funktioniert.
Also gab es folgende Schritte, die zum aktuellen Schaltplan geführt haben.

#### MosFet ordentlich beschalten
Da hier pyrotechnische Gegenstände zum Einsatz kommen, von denen eine gewisse Gefahr ausgeht, muss die Treiberschaltung ordentlich sein. Daher war es gegeben, die MosFet mit einer ordentlichen Schaltung zu versehen.

#### Scharfschaltung mit Low Side Schalter geht nicht
Man kann nicht die Masse eines Low Side MosFet wieder als Busspannung für einen anderen Low Side MosFet verwenden. Das funktioniert so einfach nicht. Also musste eine High Side Schaltung mit einem P-MosFet her. Damit der NodeMCU das aber korret ansteuern kann, wird trotzdem noch ein Low Side gebraucht. Somit war ein zusätzlicher MosFet und Widerstände nötig.
Da P-MosFet keinen so tollen Innenwiderstand wie N-MosFet haben ist der zusätzliche MosFet auch leider nicht ganz so billig.

#### Der NodeMCU zappelt
Leider leider hat so ein NodeMCU 3 Pins, die beim booten kurz zappeln. Die elektrische Scharfschaltung verhindert, dass beim Einschalten irgendwas passieren kann. Wenn allerdings Scharf geschalten ist und dann der Controller einen Reset durchführt, zappeln die Pins und es liegt kurz Zündspannung an.
Meine A-Zünder haben beim Versuch nicht ausgelöst. Trotzdem war das zu unsicher.
Da keine weiteren Pins verfügbar waren und eine Anlage mit nur 5 Kanälen wenig Sinn macht, wurde das Schieberegister hinzugefügt. Somit war das n x 8 Modul geboren, denn die Schaltung erlaubt es jetzt beliebig viele Schieberegister hintereinander zu reihen und so ein beliebiges vielfaches von 8 Kanälen umzusetzen.
Ich selbst habe mir Module mit 8 und 16 Kanälen gebaut.

#### Zu viel Strom!
Da so ein Zünder quasi einen Kurzschluss darstellt und auch Parallelschaltungen von Zündern funktionieren sollen ohne dass Akku oder MosFet abrauchen, wurde noch eine Strombegrenzung eingeführt. Das ist ein simpler großer 10W Draht Keramik Lastwiderstand mit 1,8Ohm. Somit liegt der maximal mögliche Strom bei nominal 12V Spannung (real dann knapp 13) bei ca. 7A.
Natürlich kann das nicht als Dauerstrom betrieben werden. Der P-MosFet erhitzt sich. Für Dauerstrom bräuchte er eine Kühlung.
Der Lastwiderstand ist mit den so anliegenden gut 70W ebenso überfordert, kann das über kurze Zeiträume aber über Wärme abpuffern.
Wen man ganz Safe gehen will, kann man den Widerstand auf 5,6Ohm erhöhen. Damit liegt der Strom bei 2,3A und die Leistung bei 25W. Größere Parallelschaltungen könnten dann aber schwierig werden.

Ergänzt man das alles jetzt noch mit der Spannungsmessung und ein paar Schaltern sowie LEDs und Terminals, kommt man zu dem Schaltplan, der schließlich entstanden ist und umgesetzt wurde.


## Aufbau des Zündmoduls
### Bauteile (Basis)
Die folgenden Bauteile sind nötig, um überhaupt die Grundlage des Moduls zu bilden. Sie bleiben immer gleich, egal wieviele Kanäle man umsetzt.
- 1x NodeMCU V3 (Lolin)
- 2x Kippschalter (am besten unterschiedlich, so dass man Hauptschalter von Zündschalter unterscheiden kann)
- 1x Draht Keramik Lastwiderstand 1,8Ohm 10Watt (R1)
- 1x IRF9540N (Elektrische Scharfschaltung)
- 1x IRF3708 (Elektrische Scharfschaltung)
- 5x R 10k
- 1x R 4,7k
- 2x R 1k
- 1x C 100nF
- 3x LED 12V (Rot, Gelb, Grün)
- 1x Bleiakku 12v (z.B. Ultracell UL1.3-12 den verwende ich)
- 2x Kabelschuhe zum Anschluss des Akkus
- Ausreichend Draht zum verbinden (Mehrfarbig ist Hilfreich) - 0,5mm Querschnitt für Haupt und Zündleitungen, Steuerleitungen dürfen kleiner sein
- Flexible Litze für den Akku und sonstige bewegliche Teile (es ist praktisch, wenn man die Platine aus dem Gehäuse nehmen kann, um bei Bedarf Software aufzuspielen)

### Bauteile je 8 Kanäle
Die folgenden Bauteile sind nötig, um jeweils 8 Kanäle umzusetzen. Diese kann man beliebig vervielfachen.
- 1x 74HC595 (Schieberegister)
- 8x R 100
- 8x R 10k
- 8x Anschlussterminal (Lautsprecherklemmen z.B.)

Will man 16 Kanäle umsetzen, dann braucht man das einfach doppelt, bei 24 Kanäle 3x etc..

### Schaltplan Erläuterung
Ich bin kein Profi, was KiCad angeht, daher ist sicherlich nicht alles optimal.
Für die Schalter hatte ich kein Bauteil gefunden. Daher die gestrichelte Linie und den entprechenden Namen (Hauptschalter, Zündschalter).
Verbunden ist bei Überkreuzungen nur das, was einen Punkt hat.
Die Schieberegister hatten keinen Pin für Vcc, daher ist da ein extra Punkt eingezeichnet und benannt. Das muss an den entsprechenden Pin angeschlossen werden.
Der Schaltplan zeigt den Aufbau für 16 Kanäle. Will man nur 8 Kanäle umsetzen, so lässt man das rechte Schieberegister und alle daran angeschlossenen Treiber einfach weg. Die Leitung, die auf Pin 9 ankommt, muss dann stattdessen beim linken Schieberegister auf Pin 9 angeschlossen werden.
Warum?
Die Schieberegister sind seriell durchgeschleift, indem der serielle Ausgang (Pin 9) des ersten Registers an den Eingang (Pin 14) des nächsten Registers geht. Das LETZTE Register geht dann wieder zurück zum Controller. Will man noch mehr Schieberegister nutzen, so fügt man sie nach diesem Schema einfach zwischen die beiden Register des Schaltplans ein.
Alle anderen Datenleitungen sind als Bus ausgeführt. An jedem Register kommt das selbe an.
Dadurch, dass das letzte Register (bei 8 Kanälen ist das auch das Erste) diesen Ausgang an den Controller zurückgibt, kann der Controller zählen, wieviele Kanäle vorhanden sind. Mit diesem Trick funktioniert die selbe Software mit beliebig vielen Registern. Es wäre sogar möglich, ein einzelnes größeres Register zu verwenden, solange die gleichen Eigenschaften vorliegen.

### Aufbau
Für das Zündmodul gibt es keine vorgefertigte Platine. Es bietet sich an, die Bauteile auf Lochraster zu löten.
Zu aller erst sollte man sich Gedanken machen, wo und wie man alle Bauteile platziert.
Generelle Strategie: An einem Ende Controller und Basisbeschaltung zusammen. Dann mittig Schieberegister und am anderen Ende die MosFet mit Widerstanden.

Wenn man Schritt für Schritt vorgehen will, lötet man zunächst nur den Basisaufbau (nicht übersehen, dass die LED vom Zündschalter auf Masse geht, die Leitung läuft durch die ganzen Treiber).
Temporär muss man jetzt D2 und D3 (über den vorhandenen 1k Widerstand) überbrücken. Dann kann man Software aufspielen und testen. Die LEDs sollten schon korrekt reagieren (Power LED beim Hauptschalter, Zünd LED beim Zündschalter und die Zündleitungs LED bei Scharfschaltung). Anmerkung: Die Zündleitungs LED leuchtet bei eingeschalteter Power LED schwach. Das ist normal, da Prüfspannung auf der Zündleitung anliegt.
Ebenso sollte die Spannungsanzeige in der App funktionieren (etwa Nennspannung bei eingeschalteter Zündung und ansonsten 2-3V).

Falls an dieser Stelle irgendwas nicht wie beschrieben geht: Fehler suchen.

Anschließend ist es praktisch die MosFet jeweils in Reihen zu setzen, so dass man die Massebeine direkt aufeinander löten kann. Aber Platz lassen, da müssen noch Widerstände hin. Die ebenfalls einlöten, so dass nur noch das Schaltsignal am R100 Widerstand fehlt und der Ausgang zum Anschlussterminal.
Zuletzt die Schieberegister platzieren und verlöten. Nicht vergessen: Die Brücke zwischen D2 und D3 aufmachen und an den Registern korrekt anschließen.
