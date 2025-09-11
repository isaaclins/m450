# Umgebungen - Übung

## Aufgabe 1

### Frage

**Was ist der Grund für verschiedene Umgebungen?**

### Antwort

Der Grund für verschiedene Umgebungen ist die Notwendigkeit, Software in verschiedenen Phasen ihres Lebenszyklus zu entwickeln, zu testen, zu integrieren und bereitzustellen, ohne die Produktivumgebung zu beeinträchtigen. Jede Umgebung dient einem spezifischen Zweck:

- **Entwicklung neuer Funktionen** ohne Risiko für bestehende Systeme
- **Integration von Komponenten** und Überprüfung der Kompatibilität
- **Qualitätssicherung** durch umfassende Tests
- **Vorbereitung für die Veröffentlichung** unter produktionsähnlichen Bedingungen
- **Sicherer Betrieb** der Anwendung für Endbenutzer

Dies ermöglicht eine kontrollierte und sichere Entwicklung und Bereitstellung.

## Aufgabe 2

### Frage

**Wieso wollen wir, dass die Umgebungen isoliert sind?**

### Antwort

Wir wollen, dass die Umgebungen isoliert sind, um:

1. **Stabilität und Sicherheit:** Fehler oder Probleme in einer Umgebung (z.B. Entwicklung) sollen keine Auswirkungen auf andere, kritischere Umgebungen (z.B. Produktion) haben.

2. **Reproduzierbarkeit:** Jede Umgebung kann spezifische Konfigurationen und Daten haben, die für ihren Zweck optimiert sind, ohne von Änderungen in anderen Umgebungen beeinflusst zu werden.

3. **Kontrolle:** Isolation ermöglicht es, Zugriffsrechte, Daten und Konfigurationen für jede Umgebung separat zu verwalten und zu sichern.

4. **Parallele Arbeit:** Entwickler, Tester und Operations-Teams können gleichzeitig in ihren jeweiligen Umgebungen arbeiten, ohne sich gegenseitig zu stören.

## Aufgabe 3

### Frage

**Was ist ein Grund, die Isolationsgrenzen zu verletzen?**

### Antwort

Ein Grund, Isolationsgrenzen zu verletzen, könnte in Ausnahmefällen die Notwendigkeit sein:

1. **Kritisches Problem in der Produktion** schnell zu debuggen, das nur unter den spezifischen Bedingungen der Produktivumgebung auftritt und nicht in niedrigeren Umgebungen reproduziert werden kann.

2. **Daten für Testzwecke** aus der Produktion zu anonymisieren und in eine Testumgebung zu kopieren.

3. **Notfall-Wartung** bei Systemausfällen, wo direkter Zugriff auf die Produktionsumgebung erforderlich ist.

Dies sollte jedoch nur unter strengen Kontrollen und als letztes Mittel geschehen, da es erhebliche Risiken birgt.

## Aufgabe 4

### Frage

**Beschreiben Sie in welcher Reihenfolge eine Software die Umgebungen durchläuft.**

### Antwort

Typischerweise durchläuft eine Software die Umgebungen in folgender Reihenfolge:

1. **DEV (Development):** Hier wird der Code von Entwicklern geschrieben und lokal getestet.

2. **INT (Integration):** Einzelne Komponenten oder Module werden integriert und auf Kompatibilität und grundlegende Funktionalität getestet.

3. **TEST (Testing):** Umfassende Tests wie Systemtests, Regressionstests, Performance-Tests und Akzeptanztests werden durchgeführt.

4. **STAGE (Staging/Pre-Production):** Eine produktionsnahe Umgebung, die dazu dient, die Bereitstellung und den Betrieb unter realen Bedingungen zu simulieren, oft für letzte Abnahmetests durch Stakeholder.

5. **PROD (Production):** Die Live-Umgebung, in der die Software von Endbenutzern genutzt wird.

## Aufgabe 5

### Frage

**Beschreiben Sie die Umgebungen in einem kurzen Satz.**

### Antwort

#### Aufgabe 5.1 - DEV

**DEV:** Umgebung für die Entwicklung und lokale Tests neuer Funktionen durch Entwickler.

#### Aufgabe 5.2 - INT

**INT:** Umgebung zur Integration von Code-Änderungen und Komponenten sowie für grundlegende Integrationstests.

#### Aufgabe 5.3 - TEST

**TEST:** Umgebung für umfassende Qualitätssicherung und verschiedene Testarten (System-, Performance-, Akzeptanztests).

#### Aufgabe 5.4 - STAGE

**STAGE:** Produktionsnahe Umgebung zur Simulation des Betriebs und für finale Abnahmetests vor dem Produktionsrelease.

#### Aufgabe 5.5 - PROD

**PROD:** Die Live-Umgebung, die von Endbenutzern verwendet wird und echte Geschäftsdaten verarbeitet.

## Aufgabe 6

### Frage

**Die produktive Umgebung unterscheidet sich merklich von allen anderen. Nennen Sie 2 dieser Unterschiede.**

### Antwort

1. **Daten:** Die Produktivumgebung enthält echte, sensible Kundendaten und Geschäftsdaten, während andere Umgebungen oft mit anonymisierten, synthetischen oder Testdaten arbeiten.

2. **Verfügbarkeit und Performance:** Die Produktivumgebung hat die höchsten Anforderungen an Verfügbarkeit, Performance und Skalierbarkeit, da sie direkt von Endbenutzern genutzt wird und Ausfallzeiten direkte geschäftliche Auswirkungen haben.

3. **Sicherheit:** Die Produktivumgebung hat die strengsten Sicherheitsmaßnahmen und Zugriffsrechte, um Datenintegrität und -vertraulichkeit zu gewährleisten.

4. **Monitoring und Support:** Die Produktivumgebung wird rund um die Uhr überwacht und hat dedizierte Support-Teams.

## Aufgabe 7

### Frage

**In welcher Umgebung kann ich selber neue Libraries installieren?**

### Antwort

| DEV | INT | TEST | STAGE | PROD |
| :-: | :-: | :--: | :---: | :--: |
|  ✓  | (✓) |      |       |      |

- **DEV:** Vollständige Freiheit für Entwickler
- **INT:** Nur unter strenger Kontrolle und Genehmigung
- **TEST/STAGE/PROD:** Nicht erlaubt - nur über kontrollierte Deployment-Pipelines

## Aufgabe 8

### Frage

**In welcher Umgebung kann einen Debugger anschliessen?**

### Antwort

| DEV | INT | TEST | STAGE | PROD |
| :-: | :-: | :--: | :---: | :--: |
|  ✓  |  ✓  | (✓)  |       |      |

- **DEV:** Häufig und ohne Einschränkungen
- **INT:** Unter bestimmten Bedingungen und mit Vorsicht
- **TEST:** Nur in Ausnahmefällen und mit Genehmigung
- **STAGE/PROD:** Normalerweise nicht erlaubt

## Aufgabe 9

### Frage

**Was für Daten erwarten Sie in dieser Umgebung?**

### Antwort

|                       DEV                        |                          INT                           |                               TEST                                |                               STAGE                               |                           PROD                           |
| :----------------------------------------------: | :----------------------------------------------------: | :---------------------------------------------------------------: | :---------------------------------------------------------------: | :------------------------------------------------------: |
| Entwicklungsdaten, Dummy-Daten, lokale Testdaten | Integrierte Testdaten, Dummy-Daten, synthetische Daten | Umfassende Testdaten (synthetisch, anonymisiert), Edge-Case-Daten | Produktionsnahe Testdaten (anonymisiert), realistische Datensätze | Echte Kundendaten, Live-Geschäftsdaten, Produktionsdaten |

## Aufgabe 10

### Frage

**Wer hat welche Art von Zugriff auf die Umgebungen?**

### Antwort

| Wer                         |                DEV                |                INT                |               TEST                |               STAGE               |                            PROD                             |
| :-------------------------- | :-------------------------------: | :-------------------------------: | :-------------------------------: | :-------------------------------: | :---------------------------------------------------------: |
| **Nachbarskatze**           |                                   |                                   |                                   |                                   |                 APP (öffentlich zugänglich)                 |
| **Entwickler\*in**          |        SSH, DATA, LOG, APP        |        SSH, DATA, LOG, APP        |             LOG, APP              |                APP                |                     APP (eingeschränkt)                     |
| **Tester\*in**              |                                   |             LOG, APP              |        SSH, DATA, LOG, APP        |             LOG, APP              |                     APP (eingeschränkt)                     |
| **Systemadministrator\*in** | SSH, DATA, LOG, APP (vollständig) | SSH, DATA, LOG, APP (vollständig) | SSH, DATA, LOG, APP (vollständig) | SSH, DATA, LOG, APP (vollständig) | SSH, DATA, LOG, APP (vollständig, aber streng kontrolliert) |

## Aufgabe 11

### Frage

**Wieso kann ich auf gewissen Umgebungen nicht selber z. B. Libraries installieren?**

### Antwort

Auf höheren Umgebungen (TEST, STAGE, PROD) kann man nicht einfach selbst Libraries installieren, um:

1. **Stabilität zu gewährleisten:** Unerwartete Abhängigkeiten oder Kompatibilitätsprobleme können das System destabilisieren.

2. **Sicherheit zu gewährleisten:** Ungeprüfte Softwarepakete können Sicherheitslücken einführen.

3. **Reproduzierbarkeit zu gewährleisten:** Jede Änderung muss dokumentiert und nachvollziehbar sein.

4. **Kontrolle zu gewährleisten:** Nur getestete und genehmigte Softwarepakete sollen über automatisierte Deployment-Pipelines bereitgestellt werden.

## Aufgabe 12

### Frage

**Wieso ist es sinnvoll, System tests in der Testumgebung durchzuführen, und nicht z. B. auf der Integrationsumgebung?**

### Antwort

Systemtests sind sinnvoll in der Testumgebung, weil:

1. **Umfassende Abdeckung:** Die Testumgebung ist darauf ausgelegt, das gesamte System in einer produktionsnahen Konfiguration zu testen, einschließlich aller Integrationen und Abhängigkeiten. Die Integrationsumgebung konzentriert sich primär auf die korrekte Zusammenarbeit einzelner Komponenten.

2. **Stabilität und Daten:** Die Testumgebung bietet eine stabilere Umgebung mit dedizierten Testdaten, die für umfangreiche und wiederholbare Tests geeignet sind. Die Integrationsumgebung kann flüchtiger sein und häufigen Änderungen unterliegen.

3. **Ressourcen:** Systemtests erfordern oft erhebliche Ressourcen und Zeit. Eine dedizierte Testumgebung ermöglicht es, diese Tests ohne Beeinträchtigung der Entwicklungs- oder Integrationsarbeiten durchzuführen.

4. **Unabhängigkeit:** Tester können in der Testumgebung unabhängig von den Entwicklern arbeiten und das System aus Endbenutzersicht validieren.

## Aufgabe 13

### Frage

**Für die Umgebungen, überlegen Sie sich welches Risiko man eingeht, wenn man die Umgebung weglässt.**

### Antwort

#### Aufgabe 13.1 - DEV

**DEV:**

- **Risiko:** Entwickler können nicht isoliert arbeiten, was zu Konflikten und Instabilität im Code führt. Neue Funktionen können nicht ordnungsgemäß entwickelt und vorab getestet werden.
- **Folge:** Langsamere Entwicklung, mehr Fehler in höheren Umgebungen, erhöhte Entwicklungszeit und -kosten.

#### Aufgabe 13.2 - INT

**INT:**

- **Risiko:** Integration von Komponenten und Modulen wird nicht frühzeitig getestet. Kompatibilitätsprobleme und Schnittstellenfehler werden erst in späteren Phasen entdeckt.
- **Folge:** Hoher Aufwand zur Fehlerbehebung in TEST/STAGE/PROD, Verzögerungen im Release-Zyklus, erhöhte Kosten für nachträgliche Integration.

#### Aufgabe 13.3 - TEST

**TEST:**

- **Risiko:** Mangelnde Qualitätssicherung. Bugs, Performance-Probleme und Sicherheitslücken werden nicht entdeckt, bevor die Software in Produktion geht.
- **Folge:** Schlechte Benutzererfahrung, Systemausfälle, Datenverlust, Reputationsschäden, hohe Kosten für Hotfixes und Support.

#### Aufgabe 13.4 - STAGE

**STAGE:**

- **Risiko:** Unvorhergesehene Probleme bei der Bereitstellung oder im Betrieb unter produktionsähnlichen Bedingungen werden nicht erkannt. Letzte Abnahmetests durch Stakeholder sind nicht möglich.
- **Folge:** Deployment-Fehler in Produktion, unerwartetes Verhalten der Anwendung, mangelnde Akzeptanz durch Endbenutzer, erhöhte Ausfallzeiten.

#### Aufgabe 13.5 - PROD

**PROD:**

- **Risiko:** Die Anwendung ist nicht für Endbenutzer verfügbar.
- **Folge:** Direkter Geschäftsverlust, Kundenunzufriedenheit, Reputationsschäden, keine Wertschöpfung. (Diese Umgebung wegzulassen ist im Grunde das Ende des Projekts.)

---

Version 250829-41057625
