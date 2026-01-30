# M450 Abschlussprojekt: 10x10-Variantenschach (CLI)

version 251206-078d7e62  
2025-12-06  
Dies ist ein bewertetes Projekt.

Dieses Repository implementiert **abgeändertes Schach auf einem 10x10 Brett** als **textbasiertes CLI-Spiel** (inkl. Tests, JaCoCo Coverage und CI).

## Projektstruktur

Erstellen Sie ein privates Repository und fügen Sie mich als Collaborator hinzu.

Das Projekt hat einen CI/CD-Build:

- Der CI-Build wird on push / on pull request ausgeführt.
- JaCoCO ist eingebunden
  - ins Projekt
  - in den CI/CD-Build
  - Report als Artefakt hochgeladen

Es gibt ein TODO.txt oder .md o.Ä.-File im root directory.

## Prozess-Anforderungen

Entwicklung mit TDD (und dem TODO file) — das TODO File erhält die nächsten Features die zu implementieren sind:

- zuoberst ist immer das aktuelle Feature
- es hat immer mindestens eine Zeile / ein Feature (ausser ganz am Ende, wenn man fertig ist)
- bereits implementierte Features können entfernt oder in einen “erledigt”-Abschnitt verschoben werden
- die Einträge dürfen beliebig umhergeschoben, ergänzt, verändert und entfernt werden

Der CI/CD-Build läuft mindestens ein mal pro Doppelstunde, gerne aber mehr (z.B. einmal pro Feature).  
Mindestens ein Commit pro Stunde (egal in welchem Zustand das Projekt ist).  
Das kann gerne auf einem separaten branch passieren, dass main immer baut.

## Auftrag

Ziel ist es, das Spiel **Schach** zu implementieren, jedoch mit den vorgegebenen **Variantenregeln** (10x10, Lover, geänderte Bewegungen, spezielle Rochade) und mit **TDD**.

Zusätzlich gelten Prozess-/Qualitätsanforderungen:

- CI/CD Build on push / PR
- JaCoCo Coverage Verification: mindestens **80% Line + 80% Branch**
- TODO-Liste im Root

## Variantenregeln (Kurzfassung)

Alle Standard-Schachregeln gelten weiterhin, außer die folgenden Änderungen:

- **Brettgröße:** 10x10 (Files A–J, Ranks 1–10)
- **Startposition:** Figuren sind horizontal zentriert, vertikal an den Rändern; **Weiß beginnt**

- **Neue Figur: Lover (❤/🖤)**

  - bewegt sich wie ein König (1 Feld in alle Richtungen)
  - **zählt nicht** für Schach/Schachmatt-Erkennung (nur der König zählt)
  - Start: Weiß **A1**, Schwarz **J10**

- **Dame:** wie gewohnt, bis max. 10 Felder (entspricht der Brettgröße)

- **Läufer:** diagonal, aber **maximal 6** Felder pro Zug

- **Springer:** "3 vorwärts + 1 seitlich" oder "1 vorwärts + 3 seitlich" (vorwärts ist farbabhängig)

- **Türme:** pro Seite **3** Türme

- **Bauern:** pro Seite **10** Bauern (A–J)

- **Rochade:** nur zur **Lover-Seite** (inkl. Bedingungen: König/Lover/Turm unverschoben, kein Schach, keine bedrohten Felder, keine Figuren dazwischen)

## Implementierungsstand

Stand: **alle Features aus der Aufgabenstellung sind umgesetzt** (Basis + erweiterte Features).

- [x] Spielsetup (neues Spiel) inkl. 10x10 Startaufstellung, Weiß beginnt
- [x] Spielstatus-Tracking/-Anzeige (Brett + aktueller Spieler + Status)
- [x] Spielzug-Eingabe (Text/Schachnotation) inkl. Formatvalidierung
- [x] Zug-Validierung inkl. Verhindern von Selbst-Schach
- [x] Automatische Schach-Erkennung (Lover zählt nicht)
- [x] Entfernung/Tracking geschlagener Figuren
- [x] Automatische Schachmatt-Erkennung inkl. Gewinnerausgabe
- [x] Remis-Erkennung (Patt/Stalemate) inkl. Ausgabe
- [x] Rochade (nur Lover-Seite) inkl. aller Bedingungen
- [x] Schachuhr inkl. Anzeige + Timeout

Das Interface ist textbasiert:

- 10x10 Brett mit Koordinaten A–J und 1–10
- Unicode-Symbole für Figuren + "." für leere Felder
- Befehle: `quit`, `new game`

## Timeline

Die Lektionen stehen alle für das Projekt zur Verfügung.  
Abgabe: Freitag 2026-01-16 18:00 Uhr

## Bewertungskriterien

- Anforderungen oben sind eingehalten
- TDD wurde befolgt
- Insbesondere auch Refactoring
- JaCoCO hat mindestens 80% branch- und line-coverage
- Schach ist richtig implementiert
- Gute Tests
- Mocks wo sinnvoll, aber nur wenn nötig
- sinnvolle Aufteilung (Unit/Integration etc.)
- sinnvoller Einsatz von lifecycle methods
- sinnvoller Einsatz von parametrisierten Tests
- Clean Code
- auch in Tests
- Verspätete Abgaben geben Abzug
- Sie können Tools verwenden, aber sie müssen ihren Code erklären können. Wenn Sie das nicht können, gibt es Abzug.

| Name               | Figure |
| ------------------ | ------ |
| white chess king   | ♔      |
| white chess queen  | ♕      |
| white chess rook   | ♖      |
| white chess bishop | ♗      |
| white chess knight | ♘      |
| white chess pawn   | ♙      |
| black chess king   | ♚      |
| black chess queen  | ♛      |
| black chess rook   | ♜      |
| black chess bishop | ♝      |
| black chess knight | ♞      |
| black chess pawn   | ♟      |

## Projekt-Setup & Nutzung

- Voraussetzungen: Java 17
- Build & Tests: `./gradlew test jacocoTestReport jacocoTestCoverageVerification`
- Coverage Report: `app/build/reports/jacoco/test/html/index.html`
- Run (interaktiv): `./gradlew :app:run`
- Run (Script-Modus): `./gradlew :app:run --args="--script scripts/demo.txt"`
- CI: GitHub Actions Workflow (siehe `ci.txt`) führt Tests + Coverage aus und lädt den HTML-Report als Artefakt hoch.

## Bedienung (CLI)

Beim Start wird die Bedenkzeit abgefragt (5/10/20 Minuten; Default 10). Danach läuft das Spiel im Loop:

- Move Input: z.B. `A2 A3`, `A2-A3`, `e2e4`, `J10 A9`
- Befehle: `quit`, `new game`
- Status-Ausgaben: `CHECK`, `CHECKMATE - Winner: ...`, `DRAW - Stalemate`, Captured-Liste, Schachuhr pro Spieler

## Qualitätssicherung

- TDD-orientierte Entwicklung mit Unit/Integration-Tests
- JaCoCo Coverage Verification ist Teil von `check` (80% Line + 80% Branch)

## Dev Tools

- `./gradlew :app:findMate` sucht eine kurze Matt-Sequenz ab Startposition und schreibt sie nach `scripts/mate-asap.txt`
