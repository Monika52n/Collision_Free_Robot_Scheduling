# Számítógépes hálózatok és osztott rendszerek

## Cikk összegzés
Megtalálható a `documentation` mappában lévő **README** file-ban. ([Navigálás a file-ra](/Documentation/README.md))

## Készített szoftver

### Alapötlet

A gráfok tekinthetőek dinamikus labirintusoknak, melyben a falat a robotok képezik. A labirintus a robotok mozgásától lesz dinamikus. Mivel az a node, mely foglalt, nem közelíthető meg más robotok által, a labirintusban falat alkotnak. A labirintusból való "kijutás" helyét egy robot számára a következő elvégezendő feladat pozíciója jelöli.

Minden lépés pontosan egy egység időt vesz igénybe a robotok számára.

Egy gráfot a következő képpen lehet labirintusra leképezni egy példán keresztül:

```
Legyen Nx egy feladat, ahol X jelöli a feladat idejét. Két Nx közöttí úton a szám a két feladat közti távolságot jelöli.
Tegyük fel, hogy a program inicializásakor 2 robot van a "pályán". Az egyik a középső node-on, míg a másik a jobb alsó sarokban helyezkedik el és a középen álló robot olyan node-on áll, mely egy számára elvégezendő feladatot "rejt". A jobb alsó node-on álló robot számára a középső bal node tartalmazza következő feladatot.
Az első ciklusban a középső robot (továbbiakban R1) felismeri, hogy egy elvégezendő feladaton áll, így elkezdi csinálni. A jobb alsó sarokban lévő robot (továbbiakban R2) ismervén a hozzárendelt feladatok pozícióját, "felfedezésre" indul a gráf labirintusban.

Az első ciklusban elindul a felfelé, mivel az tűnik a legrövidebb útnak (mivel minden el nem végezendő feladat csak 1 egységnyi időt vesz igénybe az áthaladás), hiszen a következő lépéseket követve: [fel, balra, balra] összesen 9 egység időt vesz igénybe a célba jutás. Lefut 5 ciklus és megérkezik a középső node-hoz, melyen R1 még 3 ciklusig feladatot végez, így R2-nek újra kell gondolnia az útvonalat.

Mivel az eltelt 5 ciklus alatt minden robot gondolkozik, hogy merre menjen, R2 kap egy "joker"-t, miszerint 5 cikluson keresztül gondolkozhat, anélkül, hogy a többiek tovább lépnének. Íj módon figyelembe véve azt, hogy melyik út nem célra vezető újra tervezhet, viszont mégse lesz lemaradva a többiekhez képest.

Ezzel felmerül a probléma, hogy mivan akkor, ha egy másik robot már volt az új úton amit R2 tervez megtenni. Annak érdekében, hogy ez ne fordulhasson elő, minden node rendelkezik egy pontosan olyan hosszú listával, amennyi idő egységig elhelyezkedett rajta egy robot. Ebben a listában az objektumok tárolják, hogy ki és mikor helyezkedett el rajta. Így mikor R2 újra tervez, le tudja ellenőrizni a node-ot, hogy amikor ő a 3. ciklusban eléri az alsó, középen lévő node-ot, foglalt volt-e. Amennyiben igen, úgy ismét kap 5 ciklusnyi időt, hogy újra számolja az útvonalát, azonban ezúttal ismerni fogja mind a 2 "zsákutcát".

Amennyiben az új út, melyet R2 talált még nincs blokkolva az 5. ciklusig (AKA 5. idő pillanatig), úgy a számláló tovább megy minden robot számára. Amennyiben egy robot számára elfogyott az utolsó elvégezendő feladat is, úgy ott marad az utolsó node-on, ahol végezte a feladatát.

Az algoritmus akkor ér véget, ha minden a gráfon elhelyezkedő robot elvégezte a számára kiosztott összes feladatot.

N1--4--N3--2--N1 
|      |      |
2      2      3
|      |      |
N1--2--N8--2--N1
|      |      |
4      2      2
|      |      |
N5--3--N4--2--N3
```

Ezzel a módszerrel végtelen mennyiségű node- és legfeljebb annyi robot, ahány node adható meg. A végeredmény egy minden robot számára optimális út lesz, melyben elvégzik az összes számukra kijelölt feladatot. Minden robot saját maga tárolja az útvonalát, majd az algoritmus az életciklusa végén begyűjti az összes robottól az adatot és egy listát készít belőle, melyben a leghosszabb lista lesz a teljes futási idő hossza.

Mivel a cikk célja az optimalizáció, így az algoritmust le kell lehessen futtattani egy robotra is. Amennyiben ez megvalósul, az utolsó (leglassabban végző robot) számára lehet építeni egy pályát a többi robot listája alapján, azonban most egyedül lesz "játékos". A többiek X időben foglalt helye statikus és nem változtatható, így a leglassabb robot egyedül keres optimálisabb utat. Amennyiben talál és az rövidebb ideig tart, mint az egyel előtte gyorsabban végző robot, úgy le kell futtatni az algoritmust arra az egy robotra is. Ez addig megy, míg a leglassabb robot már nem tud jobb útvonalat találni és ő marad a leglassabb. Ebben az esetben a probléma megoldottnak tekinthető és elkészült a legoptimálisabb útvonal minden robot számára.

### Pseudo kód - Útkeresés

### Ismert problémák

### Fejlesztési lehetőségek

#### Dokumentációt illetően

- Pseudo kódhoz diagrammot készíteni
- Ennek a "specifikációnak" az angol nyelvű változatának elkészítése

#### Algoritmust illetően
  
- A robotok tudjanak kommunikálni, hogy abban az esetben, hogyha egy utat blokkolnak egy másik robot elől, de csak várakoznak, akkor tudják úgy módosítani az útjukat, hogy vagy később érjenek oda, hogy a kérvényező robot elmehessen, vagy térjen az ezt követő legoptimálisabb útvonalra

## Kapcsolódó dokumentumok

- [Az útvonal kereséshez magyarázó videó](https://www.youtube.com/watch?v=-L-WgKMFuhE&t=348s)
