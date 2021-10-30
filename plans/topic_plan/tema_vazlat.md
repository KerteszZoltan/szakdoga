# Szakdolgozati téma vázlat
### Határidő napló
Készítette: Kertész Zoltán
Neptun kód: F541OL
Szak: Programtervező Informatikus Bsc.
Képzés: Nappali
Témavezető tanár. Dr. Tajti Tibor Gábor
## Témaválasztás indoklás
Azért választottam a mobil és a webes platformot, mivel ez a két legelterjedtebb napjainkban. Szakdolgozatom célja, hogy fejlődjek és ötvözzem a kettőről szerzett tudásom és bővítsem azt. Mindemellett egy olyan témát szerettem volna választani amiben lehetőségem van a fejlődésre és egy hasznos programot készíthetek.
## Alapötlet
A határidőnapló elgondolásnál egy olyan gondolat fogalmazódott meg bennem, hogy ezekkel az eszközökkel van egy probléma, hogy az akkumulátor kapacitás és a mobiladat keret véges és a készüléket el lehet hagyni. Ezt a gondolatmenetet folytatva arra jutottam, hogy az Androidos applikáció mellé legyen egy webes elehetőség is. Így ha a fent említett események egyike bekövetkezik már el is érhetjük az eseményeink gyűjteményét egy böngészőből is.
## Androidos alkalmazás
Az alkalmazásba egy bejelentkező felületet fogok elhelyezni, hogy a felhasználót tudjuk azonosítani. Az azonosítás után lehetősége lesz az eseményeket megtekinteni és láthatatlanná tenni. Az applikációban a láthatatlan eseményeket már nem lehet elérni, csak az aktívakat. A telefonos adatforgalmi problémák miatt a telefonban lenne egy noSQL adatbázis ami szinkronizálódna a szerverrel ahonnan a böngészős alkalmazást el lehet érni. Az alkalmazásban egy menü szerkezet lenne kialakítva a funkcióknak megfelelően. Az alkalmazást Java nyelven tervezem megvalósítani.
## Indítási oldal
Amikor a felhasználó megnyitja az alkalmazást be kell jelentkeznie. A bejelentkezéshez szükséges, hogy már legyen egy regisztrált profil amit a webes felületen tud megtenni.
## Kezdőoldal
A kezdőoldalon kilennének listázva a felhasználó aktív eseményei. Amikor a felhasználó kiválasztja a kívánt eseményt az adott esemény lapja nyílik meg ahol lehetősége van az esemény adatait szerkeszteni és inaktívvá tenni. Ha egy eseményt inaktívra állít az aktív események lap jelenik meg.
Esemény hozzáadása
A felhasználónak lehetősége van új eseményt felvenni a naplójába. Itt tudja megadni az esemény nevét, leírását, helyszínét és dátumát.
## Webes felület
A webes felületen lehetősége van a felhasználónak bejelentkezni és regisztrálni. Regisztráció utána a felhasználó beléphet az webes felületre ahol található egy navigációs felület és egy esemény lista. Az esemény listában a még aktív eseményeket láthatja. A webes felület PHP nyelven lenne írva, keretrendszer használatával.
A navigációs felület a következő menüpontokból állhat: Aktív események | Inaktív események | Új esemény | Profil
## Aktív események oldal:
Az oldalon kilistázásra kerülnek az aktív események neve és a dátuma. Amennyiben kiválasztunk egy eseményt, annak az adatlapját nyitjuk meg ahol lehetőség van az eseménynek a nevét, leírását, helyszínét, dátumát és állapotot módosítani.
## Inaktív események oldal:
Az oldalon kilistázásra kerülnek az inaktív események neve és a dátuma. Amennyiben kiválasztunk egy eseményt, annak az adatlapját nyitjuk meg ahol lehetőség van az eseménynek az állapotát módosítani. Ha módosítjuk az állapotot az esemény aktív lesz és a dátum az aktiválás dátuma lesz, ami után az aktív események között kell megkeresni és beállítani a pontos adatokat.
## Új esemény
Mikor az Új esemény oldalt választjuk ki lehetőségünk lesz az esemény nevét, leírását, helyszínét és dátumot állítani. Ezek után az esemény az aktív események közé kerül.
## Profil
A profilon lehetőségünk van a felhasználóról tárolt adatokat módosítani és a profilt törölni. Ha törölni kívánjuk a profilt az alkalmazásba nem lehet utána bejelentkezni.
## Használni kívánt technológiák
- Android studio használata a mobil fejlesztéshez(Java)
- Laravel, MVC alapú keretrendszer használata a webes felülethez
- MYSQL vagy NOSQL használat az adatbázishoz
n