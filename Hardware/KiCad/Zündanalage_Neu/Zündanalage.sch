EESchema Schematic File Version 2
LIBS:power
LIBS:device
LIBS:transistors
LIBS:conn
LIBS:linear
LIBS:regul
LIBS:74xx
LIBS:cmos4000
LIBS:adc-dac
LIBS:memory
LIBS:xilinx
LIBS:microcontrollers
LIBS:dsp
LIBS:microchip
LIBS:analog_switches
LIBS:motorola
LIBS:texas
LIBS:intel
LIBS:audio
LIBS:interface
LIBS:digital-audio
LIBS:philips
LIBS:display
LIBS:cypress
LIBS:siliconi
LIBS:opto
LIBS:atmel
LIBS:contrib
LIBS:valves
LIBS:nodemcu
LIBS:Zündanalage-cache
EELAYER 25 0
EELAYER END
$Descr A4 11693 8268
encoding utf-8
Sheet 1 1
Title ""
Date ""
Rev ""
Comp ""
Comment1 ""
Comment2 ""
Comment3 ""
Comment4 ""
$EndDescr
$Comp
L IRF540N Q3
U 1 1 5887DF83
P 5350 1700
F 0 "Q3" H 5600 1775 50  0000 L CNN
F 1 "IRF3708" H 5600 1700 50  0000 L CNN
F 2 "Power_Integrations:TO-220" H 5600 1625 50  0001 L CIN
F 3 "" H 5350 1700 50  0000 L CNN
	1    5350 1700
	1    0    0    -1  
$EndComp
$Comp
L R R8
U 1 1 5887E1DB
P 5000 1750
F 0 "R8" V 5080 1750 50  0000 C CNN
F 1 "100" V 5000 1750 50  0000 C CNN
F 2 "Resistors_THT:R_Axial_DIN0309_L9.0mm_D3.2mm_P12.70mm_Horizontal" V 4930 1750 50  0001 C CNN
F 3 "" H 5000 1750 50  0000 C CNN
	1    5000 1750
	0    1    1    0   
$EndComp
$Comp
L R R2
U 1 1 5887E33F
P 1300 1250
F 0 "R2" V 1380 1250 50  0000 C CNN
F 1 "10k" V 1300 1250 50  0000 C CNN
F 2 "Resistors_THT:R_Axial_DIN0309_L9.0mm_D3.2mm_P12.70mm_Horizontal" V 1230 1250 50  0001 C CNN
F 3 "" H 1300 1250 50  0000 C CNN
	1    1300 1250
	1    0    0    -1  
$EndComp
Text Label 3800 700  0    60   ~ 0
Zündschalter
$Comp
L NodeMCU_Amica_R2 U1
U 1 1 58887AC4
P 1900 2900
F 0 "U1" H 1900 3700 50  0000 C CNN
F 1 "NodeMCU_Amica_R2" H 1900 2050 50  0000 C CNN
F 2 "nodemcu:NodeMCU_Amica_R2" H 2150 2900 50  0001 C CNN
F 3 "" H 2150 2900 50  0000 C CNN
	1    1900 2900
	1    0    0    -1  
$EndComp
$Comp
L +12V #PWR1
U 1 1 588888FF
P 850 650
F 0 "#PWR1" H 850 500 50  0001 C CNN
F 1 "+12V" H 850 790 50  0000 C CNN
F 2 "" H 850 650 50  0000 C CNN
F 3 "" H 850 650 50  0000 C CNN
	1    850  650 
	1    0    0    1   
$EndComp
Text Label 900  950  0    60   ~ 0
Hauptschalter
$Comp
L GND #PWR2
U 1 1 58889038
P 1300 5900
F 0 "#PWR2" H 1300 5650 50  0001 C CNN
F 1 "GND" H 1300 5750 50  0000 C CNN
F 2 "" H 1300 5900 50  0000 C CNN
F 3 "" H 1300 5900 50  0000 C CNN
	1    1300 5900
	1    0    0    -1  
$EndComp
$Comp
L R R3
U 1 1 58889A03
P 1300 1950
F 0 "R3" V 1380 1950 50  0000 C CNN
F 1 "4,7k" V 1300 1950 50  0000 C CNN
F 2 "Resistors_THT:R_Axial_DIN0309_L9.0mm_D3.2mm_P12.70mm_Horizontal" V 1230 1950 50  0001 C CNN
F 3 "" H 1300 1950 50  0000 C CNN
	1    1300 1950
	1    0    0    -1  
$EndComp
$Comp
L R R4
U 1 1 58889A7C
P 1300 2600
F 0 "R4" V 1380 2600 50  0000 C CNN
F 1 "1k" V 1300 2600 50  0000 C CNN
F 2 "Resistors_THT:R_Axial_DIN0309_L9.0mm_D3.2mm_P12.70mm_Horizontal" V 1230 2600 50  0001 C CNN
F 3 "" H 1300 2600 50  0000 C CNN
	1    1300 2600
	1    0    0    -1  
$EndComp
$Comp
L CONN_01X02 P1
U 1 1 5888B027
P 6900 1700
F 0 "P1" H 6900 1850 50  0000 C CNN
F 1 "Zünder1" V 7000 1700 50  0000 C CNN
F 2 "Socket_Strips:Socket_Strip_Angled_1x02" H 6900 1700 50  0001 C CNN
F 3 "" H 6900 1700 50  0000 C CNN
	1    6900 1700
	0    1    1    0   
$EndComp
$Comp
L IRF9540N Q2
U 1 1 588A10E1
P 4800 800
F 0 "Q2" H 5050 875 50  0000 L CNN
F 1 "IRF9540N" H 5050 800 50  0000 L CNN
F 2 "Power_Integrations:TO-220" H 5050 725 50  0001 L CIN
F 3 "" H 4800 800 50  0000 L CNN
	1    4800 800 
	1    0    0    1   
$EndComp
$Comp
L IRF540N Q1
U 1 1 588A1703
P 4500 950
F 0 "Q1" H 4700 850 50  0000 L CNN
F 1 "IRF3708" H 4150 1100 50  0000 L CNN
F 2 "Power_Integrations:TO-220" H 4750 875 50  0001 L CIN
F 3 "" H 4500 950 50  0000 L CNN
	1    4500 950 
	1    0    0    -1  
$EndComp
$Comp
L R R5
U 1 1 588A1F41
P 2050 750
F 0 "R5" V 2130 750 50  0000 C CNN
F 1 "10k" V 2050 750 50  0000 C CNN
F 2 "Resistors_THT:R_Axial_DIN0309_L9.0mm_D3.2mm_P12.70mm_Horizontal" V 1980 750 50  0001 C CNN
F 3 "" H 2050 750 50  0000 C CNN
	1    2050 750 
	0    1    1    0   
$EndComp
$Comp
L R R7
U 1 1 588A21A9
P 4450 1150
F 0 "R7" V 4530 1150 50  0000 C CNN
F 1 "10k" V 4450 1150 50  0000 C CNN
F 2 "Resistors_THT:R_Axial_DIN0309_L9.0mm_D3.2mm_P12.70mm_Horizontal" V 4380 1150 50  0001 C CNN
F 3 "" H 4450 1150 50  0000 C CNN
	1    4450 1150
	0    -1   -1   0   
$EndComp
$Comp
L R R1
U 1 1 5888CACC
P 1100 1100
F 0 "R1" V 1180 1100 50  0000 C CNN
F 1 "1,8" V 1100 1100 50  0000 C CNN
F 2 "Resistors_THT:R_Axial_DIN0309_L9.0mm_D3.2mm_P12.70mm_Horizontal" V 1030 1100 50  0001 C CNN
F 3 "" H 1100 1100 50  0000 C CNN
	1    1100 1100
	0    1    1    0   
$EndComp
$Comp
L CONN_01X02 P2
U 1 1 588CEFF8
P 6900 2100
F 0 "P2" H 6900 2250 50  0000 C CNN
F 1 "Zünder2" V 7000 2100 50  0000 C CNN
F 2 "Socket_Strips:Socket_Strip_Angled_1x02" H 6900 2100 50  0001 C CNN
F 3 "" H 6900 2100 50  0000 C CNN
	1    6900 2100
	0    1    1    0   
$EndComp
$Comp
L CONN_01X02 P3
U 1 1 588CF077
P 6900 2550
F 0 "P3" H 6900 2700 50  0000 C CNN
F 1 "Zünder3" V 7000 2550 50  0000 C CNN
F 2 "Socket_Strips:Socket_Strip_Angled_1x02" H 6900 2550 50  0001 C CNN
F 3 "" H 6900 2550 50  0000 C CNN
	1    6900 2550
	0    1    1    0   
$EndComp
$Comp
L CONN_01X02 P4
U 1 1 588CF0EF
P 6900 2950
F 0 "P4" H 6900 3100 50  0000 C CNN
F 1 "Zünder4" V 7000 2950 50  0000 C CNN
F 2 "Socket_Strips:Socket_Strip_Angled_1x02" H 6900 2950 50  0001 C CNN
F 3 "" H 6900 2950 50  0000 C CNN
	1    6900 2950
	0    1    1    0   
$EndComp
$Comp
L CONN_01X02 P5
U 1 1 588CF132
P 6900 3400
F 0 "P5" H 6900 3550 50  0000 C CNN
F 1 "Zünder5" V 7000 3400 50  0000 C CNN
F 2 "Socket_Strips:Socket_Strip_Angled_1x02" H 6900 3400 50  0001 C CNN
F 3 "" H 6900 3400 50  0000 C CNN
	1    6900 3400
	0    1    1    0   
$EndComp
$Comp
L CONN_01X02 P6
U 1 1 588CF17C
P 6900 3800
F 0 "P6" H 6900 3950 50  0000 C CNN
F 1 "Zünder6" V 7000 3800 50  0000 C CNN
F 2 "Socket_Strips:Socket_Strip_Angled_1x02" H 6900 3800 50  0001 C CNN
F 3 "" H 6900 3800 50  0000 C CNN
	1    6900 3800
	0    1    1    0   
$EndComp
$Comp
L CONN_01X02 P7
U 1 1 588CF69E
P 6900 4250
F 0 "P7" H 6900 4400 50  0000 C CNN
F 1 "Zünder7" V 7000 4250 50  0000 C CNN
F 2 "Socket_Strips:Socket_Strip_Angled_1x02" H 6900 4250 50  0001 C CNN
F 3 "" H 6900 4250 50  0000 C CNN
	1    6900 4250
	0    1    1    0   
$EndComp
$Comp
L CONN_01X02 P8
U 1 1 588CF6F4
P 6900 4650
F 0 "P8" H 6900 4800 50  0000 C CNN
F 1 "Zünder8" V 7000 4650 50  0000 C CNN
F 2 "Socket_Strips:Socket_Strip_Angled_1x02" H 6900 4650 50  0001 C CNN
F 3 "" H 6900 4650 50  0000 C CNN
	1    6900 4650
	0    1    1    0   
$EndComp
$Comp
L IRF540N Q7
U 1 1 588D48FF
P 6200 2000
F 0 "Q7" H 6450 2075 50  0000 L CNN
F 1 "IRF3708" H 6450 2000 50  0000 L CNN
F 2 "Power_Integrations:TO-220" H 6450 1925 50  0001 L CIN
F 3 "" H 6200 2000 50  0000 L CNN
	1    6200 2000
	1    0    0    -1  
$EndComp
$Comp
L R R12
U 1 1 588D4905
P 5850 2050
F 0 "R12" V 5930 2050 50  0000 C CNN
F 1 "100" V 5850 2050 50  0000 C CNN
F 2 "Resistors_THT:R_Axial_DIN0309_L9.0mm_D3.2mm_P12.70mm_Horizontal" V 5780 2050 50  0001 C CNN
F 3 "" H 5850 2050 50  0000 C CNN
	1    5850 2050
	0    1    1    0   
$EndComp
$Comp
L IRF540N Q4
U 1 1 588D49B2
P 5350 2550
F 0 "Q4" H 5600 2625 50  0000 L CNN
F 1 "IRF3708" H 5600 2550 50  0000 L CNN
F 2 "Power_Integrations:TO-220" H 5600 2475 50  0001 L CIN
F 3 "" H 5350 2550 50  0000 L CNN
	1    5350 2550
	1    0    0    -1  
$EndComp
$Comp
L R R9
U 1 1 588D49B8
P 5000 2600
F 0 "R9" V 5080 2600 50  0000 C CNN
F 1 "100" V 5000 2600 50  0000 C CNN
F 2 "Resistors_THT:R_Axial_DIN0309_L9.0mm_D3.2mm_P12.70mm_Horizontal" V 4930 2600 50  0001 C CNN
F 3 "" H 5000 2600 50  0000 C CNN
	1    5000 2600
	0    1    1    0   
$EndComp
$Comp
L IRF540N Q8
U 1 1 588D51F0
P 6200 2850
F 0 "Q8" H 6450 2925 50  0000 L CNN
F 1 "IRF3708" H 6450 2850 50  0000 L CNN
F 2 "Power_Integrations:TO-220" H 6450 2775 50  0001 L CIN
F 3 "" H 6200 2850 50  0000 L CNN
	1    6200 2850
	1    0    0    -1  
$EndComp
$Comp
L R R13
U 1 1 588D51F6
P 5850 2900
F 0 "R13" V 5930 2900 50  0000 C CNN
F 1 "100" V 5850 2900 50  0000 C CNN
F 2 "Resistors_THT:R_Axial_DIN0309_L9.0mm_D3.2mm_P12.70mm_Horizontal" V 5780 2900 50  0001 C CNN
F 3 "" H 5850 2900 50  0000 C CNN
	1    5850 2900
	0    1    1    0   
$EndComp
$Comp
L IRF540N Q5
U 1 1 588D58A2
P 5350 3400
F 0 "Q5" H 5600 3475 50  0000 L CNN
F 1 "IRF3708" H 5600 3400 50  0000 L CNN
F 2 "Power_Integrations:TO-220" H 5600 3325 50  0001 L CIN
F 3 "" H 5350 3400 50  0000 L CNN
	1    5350 3400
	1    0    0    -1  
$EndComp
$Comp
L R R10
U 1 1 588D58A8
P 5000 3450
F 0 "R10" V 5080 3450 50  0000 C CNN
F 1 "100" V 5000 3450 50  0000 C CNN
F 2 "Resistors_THT:R_Axial_DIN0309_L9.0mm_D3.2mm_P12.70mm_Horizontal" V 4930 3450 50  0001 C CNN
F 3 "" H 5000 3450 50  0000 C CNN
	1    5000 3450
	0    1    1    0   
$EndComp
$Comp
L IRF540N Q6
U 1 1 588D6A9C
P 5350 4250
F 0 "Q6" H 5600 4325 50  0000 L CNN
F 1 "IRF3708" H 5600 4250 50  0000 L CNN
F 2 "Power_Integrations:TO-220" H 5600 4175 50  0001 L CIN
F 3 "" H 5350 4250 50  0000 L CNN
	1    5350 4250
	1    0    0    -1  
$EndComp
$Comp
L R R11
U 1 1 588D6AA2
P 5000 4300
F 0 "R11" V 5080 4300 50  0000 C CNN
F 1 "100" V 5000 4300 50  0000 C CNN
F 2 "Resistors_THT:R_Axial_DIN0309_L9.0mm_D3.2mm_P12.70mm_Horizontal" V 4930 4300 50  0001 C CNN
F 3 "" H 5000 4300 50  0000 C CNN
	1    5000 4300
	0    1    1    0   
$EndComp
$Comp
L IRF540N Q9
U 1 1 588D783B
P 6200 3700
F 0 "Q9" H 6450 3775 50  0000 L CNN
F 1 "IRF3708" H 6450 3700 50  0000 L CNN
F 2 "Power_Integrations:TO-220" H 6450 3625 50  0001 L CIN
F 3 "" H 6200 3700 50  0000 L CNN
	1    6200 3700
	1    0    0    -1  
$EndComp
$Comp
L R R14
U 1 1 588D7841
P 5850 3750
F 0 "R14" V 5930 3750 50  0000 C CNN
F 1 "100" V 5850 3750 50  0000 C CNN
F 2 "Resistors_THT:R_Axial_DIN0309_L9.0mm_D3.2mm_P12.70mm_Horizontal" V 5780 3750 50  0001 C CNN
F 3 "" H 5850 3750 50  0000 C CNN
	1    5850 3750
	0    1    1    0   
$EndComp
$Comp
L IRF540N Q10
U 1 1 588D78BE
P 6200 4550
F 0 "Q10" H 6450 4625 50  0000 L CNN
F 1 "IRF3708" H 6450 4550 50  0000 L CNN
F 2 "Power_Integrations:TO-220" H 6450 4475 50  0001 L CIN
F 3 "" H 6200 4550 50  0000 L CNN
	1    6200 4550
	1    0    0    -1  
$EndComp
$Comp
L R R15
U 1 1 588D78C4
P 5850 4600
F 0 "R15" V 5930 4600 50  0000 C CNN
F 1 "100" V 5850 4600 50  0000 C CNN
F 2 "Resistors_THT:R_Axial_DIN0309_L9.0mm_D3.2mm_P12.70mm_Horizontal" V 5780 4600 50  0001 C CNN
F 3 "" H 5850 4600 50  0000 C CNN
	1    5850 4600
	0    1    1    0   
$EndComp
$Comp
L LED D1
U 1 1 588EB4C6
P 1050 3900
F 0 "D1" H 1050 4000 50  0000 C CNN
F 1 "LED 12v Power" H 1050 3800 50  0000 C CNN
F 2 "LEDs:LED_D5.0mm_Horicontal_O1.27mm" H 1050 3900 50  0001 C CNN
F 3 "" H 1050 3900 50  0000 C CNN
	1    1050 3900
	0    -1   -1   0   
$EndComp
$Comp
L LED D2
U 1 1 588ED6ED
P 5600 900
F 0 "D2" H 5600 1000 50  0000 C CNN
F 1 "LED 12v Zündschalter" H 5600 800 50  0000 C CNN
F 2 "LEDs:LED_D5.0mm_Horicontal_O1.27mm" H 5600 900 50  0001 C CNN
F 3 "" H 5600 900 50  0000 C CNN
	1    5600 900 
	0    -1   -1   0   
$EndComp
$Comp
L LED D3
U 1 1 588F2669
P 10250 5650
F 0 "D3" H 10250 5750 50  0000 C CNN
F 1 "LED 12v Zündleitung" H 10250 5550 50  0000 C CNN
F 2 "LEDs:LED_D5.0mm_Horicontal_O1.27mm" H 10250 5650 50  0001 C CNN
F 3 "" H 10250 5650 50  0000 C CNN
	1    10250 5650
	1    0    0    -1  
$EndComp
$Comp
L 74HC595 U2
U 1 1 5890568C
P 3950 4900
F 0 "U2" H 4100 5500 50  0000 C CNN
F 1 "74HC595" H 3950 4300 50  0000 C CNN
F 2 "" H 3950 4900 50  0000 C CNN
F 3 "" H 3950 4900 50  0000 C CNN
	1    3950 4900
	0    -1   -1   0   
$EndComp
$Comp
L IRF540N Q11
U 1 1 58929869
P 9400 1700
F 0 "Q11" H 9650 1775 50  0000 L CNN
F 1 "IRF3708" H 9650 1700 50  0000 L CNN
F 2 "Power_Integrations:TO-220" H 9650 1625 50  0001 L CIN
F 3 "" H 9400 1700 50  0000 L CNN
	1    9400 1700
	1    0    0    -1  
$EndComp
$Comp
L R R17
U 1 1 5892986F
P 9050 1750
F 0 "R17" V 9130 1750 50  0000 C CNN
F 1 "100" V 9050 1750 50  0000 C CNN
F 2 "Resistors_THT:R_Axial_DIN0309_L9.0mm_D3.2mm_P12.70mm_Horizontal" V 8980 1750 50  0001 C CNN
F 3 "" H 9050 1750 50  0000 C CNN
	1    9050 1750
	0    1    1    0   
$EndComp
$Comp
L CONN_01X02 P9
U 1 1 58929875
P 10950 1700
F 0 "P9" H 10950 1850 50  0000 C CNN
F 1 "Zünder9" V 11050 1700 50  0000 C CNN
F 2 "Socket_Strips:Socket_Strip_Angled_1x02" H 10950 1700 50  0001 C CNN
F 3 "" H 10950 1700 50  0000 C CNN
	1    10950 1700
	0    1    1    0   
$EndComp
$Comp
L CONN_01X02 P10
U 1 1 5892987B
P 10950 2100
F 0 "P10" H 10950 2250 50  0000 C CNN
F 1 "Zünder10" V 11050 2100 50  0000 C CNN
F 2 "Socket_Strips:Socket_Strip_Angled_1x02" H 10950 2100 50  0001 C CNN
F 3 "" H 10950 2100 50  0000 C CNN
	1    10950 2100
	0    1    1    0   
$EndComp
$Comp
L CONN_01X02 P11
U 1 1 58929881
P 10950 2550
F 0 "P11" H 10950 2700 50  0000 C CNN
F 1 "Zünder11" V 11050 2550 50  0000 C CNN
F 2 "Socket_Strips:Socket_Strip_Angled_1x02" H 10950 2550 50  0001 C CNN
F 3 "" H 10950 2550 50  0000 C CNN
	1    10950 2550
	0    1    1    0   
$EndComp
$Comp
L CONN_01X02 P12
U 1 1 58929887
P 10950 2950
F 0 "P12" H 10950 3100 50  0000 C CNN
F 1 "Zünder12" V 11050 2950 50  0000 C CNN
F 2 "Socket_Strips:Socket_Strip_Angled_1x02" H 10950 2950 50  0001 C CNN
F 3 "" H 10950 2950 50  0000 C CNN
	1    10950 2950
	0    1    1    0   
$EndComp
$Comp
L CONN_01X02 P13
U 1 1 5892988D
P 10950 3400
F 0 "P13" H 10950 3550 50  0000 C CNN
F 1 "Zünder13" V 11050 3400 50  0000 C CNN
F 2 "Socket_Strips:Socket_Strip_Angled_1x02" H 10950 3400 50  0001 C CNN
F 3 "" H 10950 3400 50  0000 C CNN
	1    10950 3400
	0    1    1    0   
$EndComp
$Comp
L CONN_01X02 P14
U 1 1 58929893
P 10950 3800
F 0 "P14" H 10950 3950 50  0000 C CNN
F 1 "Zünder14" V 11050 3800 50  0000 C CNN
F 2 "Socket_Strips:Socket_Strip_Angled_1x02" H 10950 3800 50  0001 C CNN
F 3 "" H 10950 3800 50  0000 C CNN
	1    10950 3800
	0    1    1    0   
$EndComp
$Comp
L CONN_01X02 P15
U 1 1 58929899
P 10950 4250
F 0 "P15" H 10950 4400 50  0000 C CNN
F 1 "Zünder15" V 11050 4250 50  0000 C CNN
F 2 "Socket_Strips:Socket_Strip_Angled_1x02" H 10950 4250 50  0001 C CNN
F 3 "" H 10950 4250 50  0000 C CNN
	1    10950 4250
	0    1    1    0   
$EndComp
$Comp
L CONN_01X02 P16
U 1 1 5892989F
P 10950 4650
F 0 "P16" H 10950 4800 50  0000 C CNN
F 1 "Zünder16" V 11050 4650 50  0000 C CNN
F 2 "Socket_Strips:Socket_Strip_Angled_1x02" H 10950 4650 50  0001 C CNN
F 3 "" H 10950 4650 50  0000 C CNN
	1    10950 4650
	0    1    1    0   
$EndComp
$Comp
L IRF540N Q15
U 1 1 589298B6
P 10250 2000
F 0 "Q15" H 10500 2075 50  0000 L CNN
F 1 "IRF3708" H 10500 2000 50  0000 L CNN
F 2 "Power_Integrations:TO-220" H 10500 1925 50  0001 L CIN
F 3 "" H 10250 2000 50  0000 L CNN
	1    10250 2000
	1    0    0    -1  
$EndComp
$Comp
L R R21
U 1 1 589298BC
P 9900 2050
F 0 "R21" V 9980 2050 50  0000 C CNN
F 1 "100" V 9900 2050 50  0000 C CNN
F 2 "Resistors_THT:R_Axial_DIN0309_L9.0mm_D3.2mm_P12.70mm_Horizontal" V 9830 2050 50  0001 C CNN
F 3 "" H 9900 2050 50  0000 C CNN
	1    9900 2050
	0    1    1    0   
$EndComp
$Comp
L IRF540N Q12
U 1 1 589298C2
P 9400 2550
F 0 "Q12" H 9650 2625 50  0000 L CNN
F 1 "IRF3708" H 9650 2550 50  0000 L CNN
F 2 "Power_Integrations:TO-220" H 9650 2475 50  0001 L CIN
F 3 "" H 9400 2550 50  0000 L CNN
	1    9400 2550
	1    0    0    -1  
$EndComp
$Comp
L R R18
U 1 1 589298C8
P 9050 2600
F 0 "R18" V 9130 2600 50  0000 C CNN
F 1 "100" V 9050 2600 50  0000 C CNN
F 2 "Resistors_THT:R_Axial_DIN0309_L9.0mm_D3.2mm_P12.70mm_Horizontal" V 8980 2600 50  0001 C CNN
F 3 "" H 9050 2600 50  0000 C CNN
	1    9050 2600
	0    1    1    0   
$EndComp
$Comp
L IRF540N Q16
U 1 1 589298CE
P 10250 2850
F 0 "Q16" H 10500 2925 50  0000 L CNN
F 1 "IRF3708" H 10500 2850 50  0000 L CNN
F 2 "Power_Integrations:TO-220" H 10500 2775 50  0001 L CIN
F 3 "" H 10250 2850 50  0000 L CNN
	1    10250 2850
	1    0    0    -1  
$EndComp
$Comp
L R R22
U 1 1 589298D4
P 9900 2900
F 0 "R22" V 9980 2900 50  0000 C CNN
F 1 "100" V 9900 2900 50  0000 C CNN
F 2 "Resistors_THT:R_Axial_DIN0309_L9.0mm_D3.2mm_P12.70mm_Horizontal" V 9830 2900 50  0001 C CNN
F 3 "" H 9900 2900 50  0000 C CNN
	1    9900 2900
	0    1    1    0   
$EndComp
$Comp
L IRF540N Q13
U 1 1 589298DB
P 9400 3400
F 0 "Q13" H 9650 3475 50  0000 L CNN
F 1 "IRF3708" H 9650 3400 50  0000 L CNN
F 2 "Power_Integrations:TO-220" H 9650 3325 50  0001 L CIN
F 3 "" H 9400 3400 50  0000 L CNN
	1    9400 3400
	1    0    0    -1  
$EndComp
$Comp
L R R19
U 1 1 589298E1
P 9050 3450
F 0 "R19" V 9130 3450 50  0000 C CNN
F 1 "100" V 9050 3450 50  0000 C CNN
F 2 "Resistors_THT:R_Axial_DIN0309_L9.0mm_D3.2mm_P12.70mm_Horizontal" V 8980 3450 50  0001 C CNN
F 3 "" H 9050 3450 50  0000 C CNN
	1    9050 3450
	0    1    1    0   
$EndComp
$Comp
L IRF540N Q14
U 1 1 589298E9
P 9400 4250
F 0 "Q14" H 9650 4325 50  0000 L CNN
F 1 "IRF3708" H 9650 4250 50  0000 L CNN
F 2 "Power_Integrations:TO-220" H 9650 4175 50  0001 L CIN
F 3 "" H 9400 4250 50  0000 L CNN
	1    9400 4250
	1    0    0    -1  
$EndComp
$Comp
L R R20
U 1 1 589298EF
P 9050 4300
F 0 "R20" V 9130 4300 50  0000 C CNN
F 1 "100" V 9050 4300 50  0000 C CNN
F 2 "Resistors_THT:R_Axial_DIN0309_L9.0mm_D3.2mm_P12.70mm_Horizontal" V 8980 4300 50  0001 C CNN
F 3 "" H 9050 4300 50  0000 C CNN
	1    9050 4300
	0    1    1    0   
$EndComp
$Comp
L IRF540N Q17
U 1 1 589298F6
P 10250 3700
F 0 "Q17" H 10500 3775 50  0000 L CNN
F 1 "IRF3708" H 10500 3700 50  0000 L CNN
F 2 "Power_Integrations:TO-220" H 10500 3625 50  0001 L CIN
F 3 "" H 10250 3700 50  0000 L CNN
	1    10250 3700
	1    0    0    -1  
$EndComp
$Comp
L R R23
U 1 1 589298FC
P 9900 3750
F 0 "R23" V 9980 3750 50  0000 C CNN
F 1 "100" V 9900 3750 50  0000 C CNN
F 2 "Resistors_THT:R_Axial_DIN0309_L9.0mm_D3.2mm_P12.70mm_Horizontal" V 9830 3750 50  0001 C CNN
F 3 "" H 9900 3750 50  0000 C CNN
	1    9900 3750
	0    1    1    0   
$EndComp
$Comp
L IRF540N Q18
U 1 1 58929903
P 10250 4550
F 0 "Q18" H 10500 4625 50  0000 L CNN
F 1 "IRF3708" H 10500 4550 50  0000 L CNN
F 2 "Power_Integrations:TO-220" H 10500 4475 50  0001 L CIN
F 3 "" H 10250 4550 50  0000 L CNN
	1    10250 4550
	1    0    0    -1  
$EndComp
$Comp
L R R24
U 1 1 58929909
P 9900 4600
F 0 "R24" V 9980 4600 50  0000 C CNN
F 1 "100" V 9900 4600 50  0000 C CNN
F 2 "Resistors_THT:R_Axial_DIN0309_L9.0mm_D3.2mm_P12.70mm_Horizontal" V 9830 4600 50  0001 C CNN
F 3 "" H 9900 4600 50  0000 C CNN
	1    9900 4600
	0    1    1    0   
$EndComp
$Comp
L 74HC595 U3
U 1 1 58939C30
P 7950 4900
F 0 "U3" H 8100 5500 50  0000 C CNN
F 1 "74HC595" H 7950 4300 50  0000 C CNN
F 2 "" H 7950 4900 50  0000 C CNN
F 3 "" H 7950 4900 50  0000 C CNN
	1    7950 4900
	0    -1   -1   0   
$EndComp
$Comp
L R R6
U 1 1 5898B621
P 3100 5500
F 0 "R6" V 3180 5500 50  0000 C CNN
F 1 "10k" V 3100 5500 50  0000 C CNN
F 2 "" V 3030 5500 50  0000 C CNN
F 3 "" H 3100 5500 50  0000 C CNN
	1    3100 5500
	1    0    0    -1  
$EndComp
$Comp
L R R16
U 1 1 58991F6E
P 2600 4500
F 0 "R16" V 2680 4500 50  0000 C CNN
F 1 "10k" V 2600 4500 50  0000 C CNN
F 2 "" V 2530 4500 50  0000 C CNN
F 3 "" H 2600 4500 50  0000 C CNN
	1    2600 4500
	0    1    1    0   
$EndComp
$Comp
L R R25
U 1 1 5899AB2B
P 4150 1000
F 0 "R25" V 4230 1000 50  0000 C CNN
F 1 "100" V 4150 1000 50  0000 C CNN
F 2 "" V 4080 1000 50  0000 C CNN
F 3 "" H 4150 1000 50  0000 C CNN
	1    4150 1000
	0    1    1    0   
$EndComp
Text Label 4400 5450 0    60   ~ 0
Vcc
Text Label 7800 5400 0    60   ~ 0
Vcc
$Comp
L CP C1
U 1 1 589AC242
P 2050 5500
F 0 "C1" H 2075 5600 50  0000 L CNN
F 1 "100nF" H 2075 5400 50  0000 L CNN
F 2 "" H 2088 5350 50  0000 C CNN
F 3 "" H 2050 5500 50  0000 C CNN
	1    2050 5500
	1    0    0    -1  
$EndComp
Text Label 8350 5450 0    60   ~ 0
Vcc
Text Label 2900 5900 0    60   ~ 0
Reset
Text Label 3250 6000 0    60   ~ 0
Store
Text Label 3400 5800 0    60   ~ 0
Shift
Text Label 2850 6100 0    60   ~ 0
OE
Text Label 2450 6200 0    60   ~ 0
Vcc
Connection ~ 4350 600 
Connection ~ 1900 750 
Wire Wire Line
	1300 2750 1300 3500
Wire Wire Line
	1300 3500 1300 4050
Wire Wire Line
	1300 4050 1300 5650
Wire Wire Line
	1300 5650 1300 5900
Wire Wire Line
	4350 600  4900 600 
Wire Wire Line
	4900 600  5600 600 
Connection ~ 850  1100
Connection ~ 850  850 
Wire Wire Line
	850  850  850  650 
Wire Notes Line
	850  850  850  1100
Wire Wire Line
	850  1100 950  1100
Connection ~ 1300 1400
Wire Wire Line
	1300 1400 4900 1400
Wire Wire Line
	4900 1400 7100 1400
Wire Wire Line
	7100 1400 11150 1400
Wire Wire Line
	1300 1400 1300 1800
Connection ~ 1300 1100
Wire Wire Line
	1900 1100 1300 1100
Wire Wire Line
	1300 1100 1250 1100
Connection ~ 1300 3500
Connection ~ 1300 2200
Wire Wire Line
	1300 2100 1300 2200
Wire Wire Line
	1300 2200 1300 2450
Wire Wire Line
	1300 2200 1450 2200
Wire Wire Line
	1300 3500 1450 3500
Wire Wire Line
	850  3600 1050 3600
Wire Wire Line
	1050 3600 1450 3600
Wire Wire Line
	850  3600 850  1100
Wire Wire Line
	4600 1150 5600 1150
Connection ~ 4600 1150
Connection ~ 4900 1400
Wire Wire Line
	4900 1400 4900 1000
Connection ~ 7100 1400
Wire Wire Line
	7100 1500 6950 1500
Wire Wire Line
	7100 1900 6950 1900
Connection ~ 7100 1500
Wire Wire Line
	7100 2350 6950 2350
Connection ~ 7100 1900
Wire Wire Line
	7100 2750 6950 2750
Connection ~ 7100 2350
Wire Wire Line
	7100 3200 6950 3200
Connection ~ 7100 2750
Wire Wire Line
	7100 3600 6950 3600
Connection ~ 7100 3200
Wire Wire Line
	7100 4050 6950 4050
Connection ~ 7100 3600
Wire Wire Line
	7100 4450 6950 4450
Connection ~ 7100 4050
Wire Wire Line
	5450 3200 5450 3250
Wire Wire Line
	5600 3600 5450 3600
Wire Wire Line
	5600 4450 5450 4450
Wire Wire Line
	6300 3500 6850 3600
Wire Wire Line
	6850 3200 5450 3200
Wire Wire Line
	5600 5650 5600 4900
Wire Wire Line
	5600 4900 5600 4450
Wire Wire Line
	5600 4450 5600 4000
Wire Wire Line
	5600 4000 5600 3600
Wire Wire Line
	5600 3600 5600 3150
Wire Wire Line
	5600 3150 5600 2750
Wire Wire Line
	5600 2750 5600 2300
Wire Wire Line
	5600 2300 5600 1900
Wire Wire Line
	5600 1900 5600 1150
Wire Wire Line
	5600 1150 5600 1050
Connection ~ 5600 3600
Connection ~ 5600 4450
Connection ~ 5600 3150
Wire Wire Line
	6850 4050 5450 4050
Connection ~ 5600 4000
Wire Wire Line
	6300 4350 6850 4450
Connection ~ 1300 5650
Connection ~ 6850 1900
Connection ~ 6950 1900
Connection ~ 6850 1500
Connection ~ 6950 1500
Connection ~ 6850 2350
Connection ~ 6950 2350
Connection ~ 6850 2750
Connection ~ 6950 2750
Connection ~ 6950 3200
Connection ~ 6850 3200
Connection ~ 6850 3600
Connection ~ 6950 3600
Connection ~ 6950 4050
Connection ~ 6850 4050
Connection ~ 6850 4450
Connection ~ 6950 4450
Wire Wire Line
	1050 3750 1050 3600
Connection ~ 1050 3600
Wire Wire Line
	1050 4050 1300 4050
Connection ~ 1300 4050
Connection ~ 4900 600 
Connection ~ 7100 4450
Connection ~ 5600 5650
Wire Wire Line
	6000 2900 6000 2900
Wire Wire Line
	3900 3450 4850 3450
Wire Wire Line
	4650 4600 5700 4600
Wire Wire Line
	3200 2900 2350 2900
Wire Wire Line
	6300 1800 6850 1900
Wire Wire Line
	6850 1500 5450 1500
Wire Wire Line
	5600 1900 5450 1900
Connection ~ 5600 1900
Connection ~ 5600 2300
Wire Wire Line
	5450 2750 5600 2750
Connection ~ 5600 2750
Wire Wire Line
	5450 2350 6850 2350
Wire Wire Line
	6300 2650 6850 2750
Connection ~ 11150 1400
Wire Wire Line
	11150 1500 11000 1500
Wire Wire Line
	11150 1900 11000 1900
Connection ~ 11150 1500
Wire Wire Line
	11150 2350 11000 2350
Connection ~ 11150 1900
Wire Wire Line
	11150 2750 11000 2750
Connection ~ 11150 2350
Wire Wire Line
	11150 3200 11000 3200
Connection ~ 11150 2750
Wire Wire Line
	11150 3600 11000 3600
Connection ~ 11150 3200
Wire Wire Line
	11150 4050 11000 4050
Connection ~ 11150 3600
Wire Wire Line
	11150 4450 11000 4450
Connection ~ 11150 4050
Wire Wire Line
	9500 3200 9500 3250
Wire Wire Line
	9650 3600 9500 3600
Wire Wire Line
	9650 4450 9500 4450
Wire Wire Line
	10350 3500 10900 3600
Wire Wire Line
	10900 3200 9500 3200
Wire Wire Line
	9650 5650 9650 4850
Wire Wire Line
	9650 4850 9650 4450
Wire Wire Line
	9650 4450 9650 4000
Wire Wire Line
	9650 4000 9650 3600
Wire Wire Line
	9650 3600 9650 3150
Wire Wire Line
	9650 3150 9650 2750
Wire Wire Line
	9650 2750 9650 2300
Wire Wire Line
	9650 2300 9650 1900
Connection ~ 9650 3600
Connection ~ 9650 4450
Connection ~ 9650 3150
Wire Wire Line
	10900 4050 9500 4050
Wire Wire Line
	11150 1400 11150 1500
Wire Wire Line
	11150 1500 11150 1900
Wire Wire Line
	11150 1900 11150 2350
Wire Wire Line
	11150 2350 11150 2750
Wire Wire Line
	11150 2750 11150 3200
Wire Wire Line
	11150 3200 11150 3600
Wire Wire Line
	11150 3600 11150 4050
Wire Wire Line
	11150 4050 11150 4450
Wire Wire Line
	11150 4450 11150 5650
Connection ~ 9650 4000
Connection ~ 9650 4850
Wire Wire Line
	10350 4350 10900 4450
Connection ~ 10900 1900
Connection ~ 11000 1900
Connection ~ 10900 1500
Connection ~ 11000 1500
Connection ~ 10900 2350
Connection ~ 11000 2350
Connection ~ 10900 2750
Connection ~ 11000 2750
Connection ~ 11000 3200
Connection ~ 10900 3200
Connection ~ 10900 3600
Connection ~ 11000 3600
Connection ~ 11000 4050
Connection ~ 10900 4050
Connection ~ 10900 4450
Connection ~ 11000 4450
Connection ~ 11150 4450
Wire Wire Line
	10050 2900 10050 2900
Wire Wire Line
	10350 1800 10900 1900
Wire Wire Line
	10900 1500 9500 1500
Wire Wire Line
	9650 1900 9500 1900
Connection ~ 9650 1900
Connection ~ 9650 2300
Wire Wire Line
	9500 2750 9650 2750
Connection ~ 9650 2750
Wire Wire Line
	9500 2350 10900 2350
Wire Wire Line
	10350 2650 10900 2750
Wire Notes Line
	4350 600  3800 600 
Connection ~ 3800 600 
Wire Wire Line
	3800 600  1900 600 
Wire Wire Line
	1900 600  1900 750 
Wire Wire Line
	1900 750  1900 1100
Wire Wire Line
	5600 600  5600 750 
Wire Wire Line
	4600 750  2200 750 
Wire Wire Line
	2550 2300 2550 1000
Connection ~ 5450 1900
Wire Wire Line
	2350 2300 2550 2300
Connection ~ 5600 1150
Wire Wire Line
	7100 1400 7100 1500
Wire Wire Line
	7100 1500 7100 1900
Wire Wire Line
	7100 1900 7100 2350
Wire Wire Line
	7100 2350 7100 2750
Wire Wire Line
	7100 2750 7100 3200
Wire Wire Line
	7100 3200 7100 3600
Wire Wire Line
	7100 3600 7100 4050
Wire Wire Line
	7100 4050 7100 4450
Connection ~ 9650 5650
Wire Wire Line
	11150 5650 10400 5650
Wire Wire Line
	2550 1000 4000 1000
Connection ~ 4400 5450
Connection ~ 8350 5450
Connection ~ 2350 3600
Wire Wire Line
	4600 5600 7500 5600
Wire Wire Line
	4600 5600 4600 4200
Wire Wire Line
	8100 6100 8100 5600
Wire Wire Line
	2750 6100 4100 6100
Wire Wire Line
	4100 6100 8100 6100
Wire Wire Line
	8000 6000 8000 5600
Wire Wire Line
	3200 6000 4000 6000
Wire Wire Line
	4000 6000 8000 6000
Wire Wire Line
	7800 5900 7800 5600
Wire Wire Line
	7700 5800 7700 5600
Wire Wire Line
	3000 5800 3700 5800
Wire Wire Line
	3700 5800 7700 5800
Wire Wire Line
	2350 6200 4400 6200
Wire Wire Line
	4400 6200 8350 6200
Wire Wire Line
	8350 6200 8350 5450
Wire Wire Line
	8900 1750 7500 1750
Wire Wire Line
	7500 1750 7500 4200
Wire Wire Line
	7600 4200 7600 2050
Wire Wire Line
	7600 2050 9750 2050
Wire Wire Line
	7700 4200 7700 2600
Wire Wire Line
	7700 2600 8900 2600
Wire Wire Line
	7800 4200 7800 2900
Wire Wire Line
	7800 2900 9750 2900
Wire Wire Line
	7900 4200 7900 3450
Wire Wire Line
	7900 3450 8900 3450
Wire Wire Line
	8000 4200 8000 3750
Wire Wire Line
	8000 3750 9750 3750
Wire Wire Line
	8100 4200 8100 3950
Wire Wire Line
	8100 3950 8900 3950
Wire Wire Line
	8900 3950 8900 4300
Wire Wire Line
	8900 4300 8900 4450
Wire Wire Line
	8200 4200 8200 4050
Wire Wire Line
	8200 4050 8800 4050
Wire Wire Line
	8800 4050 8800 4600
Wire Wire Line
	8800 4600 9750 4600
Wire Wire Line
	8400 4200 8600 4200
Wire Wire Line
	8600 4200 8600 6400
Wire Wire Line
	8600 6400 2800 6400
Wire Wire Line
	4600 4200 4400 4200
Wire Wire Line
	4650 4600 4650 4100
Wire Wire Line
	4650 4100 4200 4100
Wire Wire Line
	4200 4100 4200 4200
Wire Wire Line
	4100 4200 4100 3950
Wire Wire Line
	4100 3950 4850 3950
Wire Wire Line
	4850 3950 4850 4300
Wire Wire Line
	4850 4300 4850 4450
Wire Wire Line
	4000 4200 4000 3750
Wire Wire Line
	4000 3750 5700 3750
Wire Wire Line
	3900 4200 3900 3450
Wire Wire Line
	3800 4200 3800 2900
Wire Wire Line
	3800 2900 5700 2900
Wire Wire Line
	3700 4200 3700 2600
Wire Wire Line
	3700 2600 4850 2600
Wire Wire Line
	3600 4200 3600 2050
Wire Wire Line
	3600 2050 5700 2050
Wire Wire Line
	3500 4200 3500 1750
Wire Wire Line
	3500 1750 4850 1750
Wire Wire Line
	4100 5600 4100 6100
Wire Wire Line
	4000 6000 4000 5600
Wire Wire Line
	3800 5900 3800 5600
Wire Wire Line
	3700 5800 3700 5600
Wire Wire Line
	3200 2900 3200 5350
Wire Wire Line
	3200 5350 3200 6000
Connection ~ 4000 6000
Wire Wire Line
	3100 5350 3200 5350
Connection ~ 3200 5350
Connection ~ 3100 5650
Wire Wire Line
	4400 5450 4400 6200
Wire Wire Line
	2350 3600 2350 4500
Wire Wire Line
	2350 4500 2350 5350
Wire Wire Line
	2350 5350 2350 6200
Connection ~ 4400 6200
Wire Wire Line
	2050 5350 2350 5350
Connection ~ 2350 5350
Connection ~ 2050 5650
Wire Wire Line
	1300 5650 2050 5650
Wire Wire Line
	2050 5650 3100 5650
Wire Wire Line
	3100 5650 5600 5650
Wire Wire Line
	5600 5650 9650 5650
Wire Wire Line
	9650 5650 10100 5650
Wire Wire Line
	3300 2500 3300 5600
Wire Wire Line
	3300 5600 3500 5600
Wire Wire Line
	2350 3000 3000 3000
Connection ~ 3800 5900
Wire Wire Line
	3000 3000 3000 5800
Connection ~ 3700 5800
Wire Wire Line
	2900 3200 2900 5900
Wire Wire Line
	2900 5900 3800 5900
Wire Wire Line
	3800 5900 7800 5900
Wire Wire Line
	2900 3200 2350 3200
Wire Wire Line
	2350 3100 2750 3100
Wire Wire Line
	2750 3100 2750 6100
Connection ~ 4100 6100
Wire Wire Line
	2450 4500 2350 4500
Connection ~ 2350 4500
Wire Wire Line
	2800 6400 2800 2400
Text Label 2850 6400 0    60   ~ 0
DataBack
Text Label 3050 2500 0    60   ~ 0
Data
$Comp
L R R27
U 1 1 58956874
P 5300 1900
F 0 "R27" V 5380 1900 50  0000 C CNN
F 1 "10k" V 5300 1900 50  0000 C CNN
F 2 "" V 5230 1900 50  0000 C CNN
F 3 "" H 5300 1900 50  0000 C CNN
	1    5300 1900
	0    1    1    0   
$EndComp
$Comp
L R R28
U 1 1 58956C27
P 5300 2750
F 0 "R28" V 5380 2750 50  0000 C CNN
F 1 "10k" V 5300 2750 50  0000 C CNN
F 2 "" V 5230 2750 50  0000 C CNN
F 3 "" H 5300 2750 50  0000 C CNN
	1    5300 2750
	0    1    1    0   
$EndComp
$Comp
L R R29
U 1 1 58956D1F
P 5300 3600
F 0 "R29" V 5380 3600 50  0000 C CNN
F 1 "10k" V 5300 3600 50  0000 C CNN
F 2 "" V 5230 3600 50  0000 C CNN
F 3 "" H 5300 3600 50  0000 C CNN
	1    5300 3600
	0    1    1    0   
$EndComp
$Comp
L R R33
U 1 1 58959EA6
P 6150 3900
F 0 "R33" V 6230 3900 50  0000 C CNN
F 1 "10k" V 6150 3900 50  0000 C CNN
F 2 "" V 6080 3900 50  0000 C CNN
F 3 "" H 6150 3900 50  0000 C CNN
	1    6150 3900
	0    1    1    0   
$EndComp
$Comp
L R R30
U 1 1 5895A110
P 5300 4450
F 0 "R30" V 5380 4450 50  0000 C CNN
F 1 "10k" V 5300 4450 50  0000 C CNN
F 2 "" V 5230 4450 50  0000 C CNN
F 3 "" H 5300 4450 50  0000 C CNN
	1    5300 4450
	0    1    1    0   
$EndComp
$Comp
L R R34
U 1 1 5895A254
P 6150 4750
F 0 "R34" V 6230 4750 50  0000 C CNN
F 1 "10k" V 6150 4750 50  0000 C CNN
F 2 "" V 6080 4750 50  0000 C CNN
F 3 "" H 6150 4750 50  0000 C CNN
	1    6150 4750
	0    1    1    0   
$EndComp
Wire Wire Line
	6300 4750 6300 4900
Wire Wire Line
	6300 4900 5600 4900
Connection ~ 5600 4900
Wire Wire Line
	6300 3900 6300 4000
Wire Wire Line
	6300 4000 5600 4000
$Comp
L R R32
U 1 1 58970033
P 6150 3050
F 0 "R32" V 6230 3050 50  0000 C CNN
F 1 "10k" V 6150 3050 50  0000 C CNN
F 2 "" V 6080 3050 50  0000 C CNN
F 3 "" H 6150 3050 50  0000 C CNN
	1    6150 3050
	0    1    1    0   
$EndComp
Wire Wire Line
	6300 3050 6300 3150
Wire Wire Line
	6300 3150 5600 3150
$Comp
L R R31
U 1 1 5897315B
P 6150 2200
F 0 "R31" V 6230 2200 50  0000 C CNN
F 1 "10k" V 6150 2200 50  0000 C CNN
F 2 "" V 6080 2200 50  0000 C CNN
F 3 "" H 6150 2200 50  0000 C CNN
	1    6150 2200
	0    1    1    0   
$EndComp
Wire Wire Line
	6300 2200 6300 2300
Wire Wire Line
	6300 2300 5600 2300
$Comp
L R R35
U 1 1 5897C6F1
P 9350 1900
F 0 "R35" V 9430 1900 50  0000 C CNN
F 1 "10k" V 9350 1900 50  0000 C CNN
F 2 "" V 9280 1900 50  0000 C CNN
F 3 "" H 9350 1900 50  0000 C CNN
	1    9350 1900
	0    1    1    0   
$EndComp
$Comp
L R R39
U 1 1 5897C7FB
P 10200 2200
F 0 "R39" V 10280 2200 50  0000 C CNN
F 1 "10k" V 10200 2200 50  0000 C CNN
F 2 "" V 10130 2200 50  0000 C CNN
F 3 "" H 10200 2200 50  0000 C CNN
	1    10200 2200
	0    1    1    0   
$EndComp
$Comp
L R R36
U 1 1 5897C973
P 9350 2750
F 0 "R36" V 9430 2750 50  0000 C CNN
F 1 "10k" V 9350 2750 50  0000 C CNN
F 2 "" V 9280 2750 50  0000 C CNN
F 3 "" H 9350 2750 50  0000 C CNN
	1    9350 2750
	0    1    1    0   
$EndComp
$Comp
L R R37
U 1 1 5897CAD7
P 9350 3600
F 0 "R37" V 9430 3600 50  0000 C CNN
F 1 "10k" V 9350 3600 50  0000 C CNN
F 2 "" V 9280 3600 50  0000 C CNN
F 3 "" H 9350 3600 50  0000 C CNN
	1    9350 3600
	0    1    1    0   
$EndComp
$Comp
L R R38
U 1 1 5897CC18
P 9350 4450
F 0 "R38" V 9430 4450 50  0000 C CNN
F 1 "10k" V 9350 4450 50  0000 C CNN
F 2 "" V 9280 4450 50  0000 C CNN
F 3 "" H 9350 4450 50  0000 C CNN
	1    9350 4450
	0    1    1    0   
$EndComp
$Comp
L R R40
U 1 1 5897CD44
P 10200 3050
F 0 "R40" V 10280 3050 50  0000 C CNN
F 1 "10k" V 10200 3050 50  0000 C CNN
F 2 "" V 10130 3050 50  0000 C CNN
F 3 "" H 10200 3050 50  0000 C CNN
	1    10200 3050
	0    1    1    0   
$EndComp
$Comp
L R R41
U 1 1 5897CE67
P 10200 3900
F 0 "R41" V 10280 3900 50  0000 C CNN
F 1 "10k" V 10200 3900 50  0000 C CNN
F 2 "" V 10130 3900 50  0000 C CNN
F 3 "" H 10200 3900 50  0000 C CNN
	1    10200 3900
	0    1    1    0   
$EndComp
$Comp
L R R42
U 1 1 5897CFA3
P 10200 4750
F 0 "R42" V 10280 4750 50  0000 C CNN
F 1 "10k" V 10200 4750 50  0000 C CNN
F 2 "" V 10130 4750 50  0000 C CNN
F 3 "" H 10200 4750 50  0000 C CNN
	1    10200 4750
	0    1    1    0   
$EndComp
Wire Wire Line
	10350 4750 10350 4850
Wire Wire Line
	10350 4850 9650 4850
Wire Wire Line
	9650 4000 10350 4000
Wire Wire Line
	10350 4000 10350 3900
Wire Wire Line
	9650 3150 10350 3150
Wire Wire Line
	10350 3150 10350 3050
Wire Wire Line
	9650 2300 10350 2300
Wire Wire Line
	10350 2300 10350 2200
Wire Wire Line
	4300 1150 4000 1150
Wire Wire Line
	4000 1150 4000 1000
Wire Wire Line
	5150 1900 4850 1900
Wire Wire Line
	4850 1900 4850 1750
Wire Wire Line
	5150 2750 4850 2750
Wire Wire Line
	4850 2750 4850 2600
Wire Wire Line
	5150 3600 4850 3600
Wire Wire Line
	4850 3600 4850 3450
Wire Wire Line
	4850 4450 5150 4450
Wire Wire Line
	6000 4750 5700 4750
Wire Wire Line
	5700 4750 5700 4600
Wire Wire Line
	6000 3900 5700 3900
Wire Wire Line
	5700 3900 5700 3750
Wire Wire Line
	6000 3050 5700 3050
Wire Wire Line
	5700 3050 5700 2900
Wire Wire Line
	6000 2200 5700 2200
Wire Wire Line
	5700 2200 5700 2050
Wire Wire Line
	8900 4450 9200 4450
Connection ~ 8900 4300
Wire Wire Line
	9200 3600 8900 3600
Wire Wire Line
	8900 3600 8900 3450
Wire Wire Line
	9200 2750 8900 2750
Wire Wire Line
	8900 2750 8900 2600
Wire Wire Line
	10050 4750 9750 4750
Wire Wire Line
	9750 4750 9750 4600
Wire Wire Line
	10050 3900 9750 3900
Wire Wire Line
	9750 3900 9750 3750
Wire Wire Line
	10050 3050 9750 3050
Wire Wire Line
	9750 3050 9750 2900
Wire Wire Line
	10050 2200 9750 2200
Wire Wire Line
	9750 2200 9750 2050
Wire Wire Line
	9200 1900 8900 1900
Wire Wire Line
	8900 1900 8900 1750
$Comp
L R R26
U 1 1 5893DEE9
P 2500 2400
F 0 "R26" V 2580 2400 50  0000 C CNN
F 1 "1k" V 2500 2400 50  0000 C CNN
F 2 "" V 2430 2400 50  0000 C CNN
F 3 "" H 2500 2400 50  0000 C CNN
	1    2500 2400
	0    1    1    0   
$EndComp
Connection ~ 8900 3450
Connection ~ 8900 2600
Connection ~ 4850 4300
Connection ~ 4850 3450
Connection ~ 4850 2600
Connection ~ 4850 1750
Connection ~ 4000 1000
Connection ~ 8900 1750
Connection ~ 9750 2050
Connection ~ 9750 2900
Connection ~ 9750 3750
Connection ~ 9750 4600
Connection ~ 5700 4600
Connection ~ 5700 3750
Connection ~ 5700 2900
Connection ~ 5700 2050
Wire Wire Line
	2800 2400 2650 2400
Wire Wire Line
	2350 2500 3300 2500
$EndSCHEMATC
