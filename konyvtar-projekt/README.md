# Könyvtári Nyilvántartó Rendszer (Java OOP – Prog 2)

## Problémafelvetés (Problem Statement)
A kis könyvtárak és gyűjtemények kezelői számára szükséges egy egyszerű, átlátható konzolos nyilvántartó alkalmazás, amellyel különböző típusú kiadványokat (könyveket és folyóiratokat) rögzíthetnek, ár vagy cím szerint rendezhetnek, és az állományt munkamenetek között fájlban megőrizhetik.

## Előfeltételek
- **Java JDK 17** vagy újabb
- **Apache Maven 3.8+**

## Fordítás, tesztelés és futtatás

```bash
# Fordítás és JUnit tesztek futtatása
mvn clean test

# Alkalmazás indítása
mvn compile exec:java
```

## Példa használat
```text
=== KÖNYVTÁRI NYILVÁNTARTÓ ===
1. Új könyv hozzáadása
2. Új folyóirat hozzáadása
3. Összes dokumentum listázása
4. Rendezés ár szerint
5. Rendezés cím szerint
0. Mentés és kilépés
Választás: 1
Cím: A kis herceg
Szerző: Antoine de Saint-Exupéry
Ár (Ft): 2990
Könyv sikeresen hozzáadva.
```

## Megvalósított technikai követelmények
- **Tokozás (Encapsulation):** Minden domain osztály (`Dokumentum`, `Konyv`, `Folyoirat`) `private` mezőkkel, validáló konstruktorokkal és getterekkel rendelkezik.
- **Absztrakció, öröklődés és polimorfizmus:** A `Dokumentum` absztrakt ősosztály definiálja a `getTipusLeiras()` metódust, amelyet a `Konyv` és a `Folyoirat` eltérően valósít meg futásidőben.
- **Kapcsolatok:** A `KonyvtarKezelo` aggregációval (`1` – `0..*`) tárolja a `Dokumentum` példányokat, a `KonzolUI` pedig kompozícióval birtokolja a kezelőt.
- **Gyűjtemények és rendezés:** `ArrayList<Dokumentum>` tároló és `Comparator` alapú rendezés ár és cím szerint.
- **Kivételkezelés:** Saját `HibasAdatException` osztály az üzleti szabályok megsértésének jelzésére.
- **Perzisztencia:** UTF-8 kódolású CSV mentés és visszatöltés `try-with-resources` blokkokkal (`AdatKezelo`).
- **Tesztek:** JUnit 5 egységtesztek a `src/test/java` könyvtárban.

## Tervezési minta (Design Pattern)
- **Választott minta:** **Factory Method**
- **Részt vevő osztályok:** `DokumentumFactory` (creator), `Dokumentum` (abstract product), `Konyv` és `Folyoirat` (concrete products).
- **Haszna a tervezésben:** A felhasználói felület (`KonzolUI`) és a fájlbetöltő (`AdatKezelo`) nincs közvetlenül hozzákötve a konkrét osztályok példányosításához; a típus alapú objektumlétrehozás és a típus-specifikus konverzió egyetlen helyen, a `DokumentumFactory.letrehoz(...)` metódusban központosul.

## Tervezési döntések és korlátok
- **Döntés:** Egyszerű pontosvesszővel tagolt szöveges fájl (`adatok.csv`) használata külső JSON függőségek helyett a tiszta szabványos könyvtári perzisztencia érdekében.
- **Korlát:** A mezők értéke nem tartalmazhat pontosvessző (`;`) karaktert, mert az a CSV elválasztójele.
- **UML diagram:** Megtekinthető a `docs/uml_diagram.png` és `docs/uml.puml` fájlokban.
