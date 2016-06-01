# Slika iz krogov, ki se pokaže ob premikanju miške
Projekt pri predmetu Programiranje 2 (FMF).

## Navodila za zagon
Aplikacijo zaženemo z ukazom `java Aplikacija pot`, kjer je `pot` neobvezen argument poti do direktorija slik. V kolikor ga ne
podamo, se naložijo slike iz trenutnega direktorija. Če podamo neveljavno pot, se aplikacija ne bo zagnala.

## Navodila za uporabo
### Osnovne funkcije
Ob zagonu aplikacije s premiki miške razbijamo večje kroge, ki so take barve kot je povprečje barv na tistem delu slike, na
manjše, da slika postaja vedno jasnejša. Če smo dovolj vztrajni, na koncu dobimo čisto sliko, če pa nam manjka vztrajnosti, pa si
pomagamo s pritiskom na gumb `Izriši sliko`. Če se nam vmes kadarkoli zazdi, da bi radi razbijali od začetka, lahko uporabimo
gumb `Ponastavi sliko`, ki nas privede do prvotnega stanja. Ko se slike naveličamo, jo lahko zamenjamo s pritiskom na gumb
`Naslednja slika` ali z izbiro poljubne, ki si jo izberemo v meniju `Datoteka` -> `Odpri sliko`. Če pa se naveličamo krogcev, jih
lahko zamenjamo z drugimi oblikami v meniju `Izbira oblike`. V primeru, da nam je slika iz krogcev ali drugih oblik všeč, jo
lahko v kateremkoli koraku shranimo v slikovno datoteko s klikom na `Datoteka` -> `Shrani sliko`. Za zapustitev programa izberemo `Izhod` v meniju `Datoteka`.

### Menjava direktorija slik in dodajanje slik
Če smo med izvajanjem programa v izbran direktorij dodali slike, to programu sporočimo s pritiskom na `Datoteka` -> `Osveži direktorij`, ki bo nove slike dodal. Če želimo zamenjati direktorij slik, to storimo s pritiskom na `Izberi direktorij slik` v
meniju `Datoteka`.
