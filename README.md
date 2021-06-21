# Szakdolgozati téma vázlat

## Témaválasztás indoklás
Szorgalmi feladat kapcsán kerültem először kapcsolatba az Android programozással. Az elején nehézséget okozott, a JAVA programozási nyelv, de ahogy haladt előre a feladat úgy vált számomra egyértelműbbé, hogy semmivel sem nehezebb, mint a C#, csak más a szintaxisa. Mikor készen lettem a programmal örömmel néztem vissza a sok munkára, mivel egy olyan alkalmazás volt a kezemben ami ugyan sok mindent nem csinált, de látszatja volt, nem csak konzolon pár sor.
Ezért az egyetemi éveim alatt a Mobilprogramozás ami a legjobban magával ragadott, így mikor témát kellett választani nem is volt kérdés, tudtam, hogy a szakdolgozatomban egy Androidos alkalmazást szeretnék fejleszteni. 
Egy olyan ötleten kezdtem el gondolkodni amit a későbbiekben tovább lehetne fejelszteni és hasznos is lenne. Így született meg az ötlet, hogy egyfajta határidő naplót valósítsak meg. 

## Alapötlet
A határidőnapló elgondolásnál egy olyan gondolat fogalmazódott meg bennem, hogy ezekkel az eszközökkel van egy probléma, hogy az akkumulátor kapacitás és a mobiladat keret véges és a készüléket el lehet hagyni. Ezt a gondolatmenetet folytatva arra jutottam, hogy az Androidos applikáció mellé legyen egy webes eléhetőség is. Így ha a fent említett események egyike bekövetkezik már el is érhetjük az eseményeink gyújteményét egy böngészőből is.

## Androidos alkalmazás
Az alkalmazásba egy bejelentkező felületet fogok elhelyezni, hogy a felhasználót tudjuk azonosítani. Az azonosítás után lehetősége lesz az eseményeket megtekinteni és láthatatlanná tenni. Az aplikációban a láthatatlan eseményeket már nem lehet elérni, csak az aktívakat. A telefonos adatforgalmi problémák miatt a telefonban lenne egy noSQL adatbázis ami szinkronizálódna a szerverrel ahonnan a böngészős alaklamzást el lehet érni. Az alkalmazásban egy menü szerkezet lenne kialakítva a funkcióknak megfelelően. Az alkalmazást Java nyelven tervezem megvalósítani.
### Indítási oldal
Amikor a felhasználó megnyitja az alkalmazást be kell jelentkeznie. A bejelentkezéshez szükséges, hogy már legyen egy regisztrált profil amit a webes felületen tud megtenni.
### Kezdőoldal
A kezdőoldalon kilennének listázva a felhasználó aktív eseményei. Amikor a felhasználó kiválasztja a kívánt eseményt az adott esemény lapja nyílik meg ahol lehetősége van az esemény adatait szerkeszteni és inaktívvá tenni. Ha egy eseményt inaktívvá tesz az aktív események lap jelenik meg.
### Esemény hozzáadása
A felhasználónak lehetősége van új eseményt felvenni a naplójába. Itt tudja megadni az esemény nevét, leírását, helyszínét és dátumát.
## Webes felület
A webes felületen lehetősége van a felhasználónak bejelentkezni és regisztrálni. Regisztráció utána a felhasználó beléphet az webes felületre ahol található egy navigációs felület és egy esemény lista. Az esemény listában a még aktív eseményeket láthatja. A webes felület PHP nyelven lenne írva, keretrendszer használatával. 
A navigációs felület a következő menüpontokból állhat: Aktív események | Inaktív események | Új esemény | Profil
### Aktív események oldal:
Az oldalon kilistázásra kerülnek az aktív események neve és a dátuma. Amennyiben kiválasztunk egy eseményt, annak az adatlapját nyitjuk meg ahol lehetőség van az eseménynek a nevét, leírását, helyszínét, dátumát és állapotot módosítani. 
### Inaktív események oldal:
Az oldalon kilistázásra kerülnek az inaktív események neve és a dátuma. Amennyiben kiválasztunk egy eseményt, annak az adatlapját nyitjuk meg ahol lehetőség van az eseménynek az állapotát módosítani. Ha módosítjuk az állapotot az esemény aktív lesz és a dátum az aktiválás dátuma lesz, ami után az aktív események között kell megkeresni és beállítani a pontos adatokat. 
### Új esemény
Mikor az Új esemény oldalt választjuk ki lehetőségünk lesz az esemény nevét, leírását, helyszínét és dátumot állítani. Ezek után az esemény az aktív események közé kerül.
### Profil
A profilon lehetőségünk van a felhasználóról tárolt adatokat módosítani és a profilt törölni. Ha törölni kívánjuk a profilt az alkalmazásba nem lehet utána bejelentkezni. 
## Használni kívánt tecnológiák
- Java programozási nyelv az android fejlesztéshez (Laravel vagy React)
- PHP keretrendszer a webes fejlesztéshez
- MYSQL vagy NOSQL használat az adatbázishoz
