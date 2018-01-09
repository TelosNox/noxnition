# Feuerwerk Zündmodul + Android App

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


tbc...
