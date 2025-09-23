<div id="header">

# Testkonzept

<div class="details">

<span id="revnumber">version 250905-45eedbf8,</span> <span id="revdate">2025-09-05</span>

</div>

<div id="toc" class="toc">

<div id="toctitle">

Table of Contents

</div>

- [Motivation](#_motivation)
- [Testkonzept - inhaltliche & formale Anforderungen](#_testkonzept_inhaltliche_formale_anforderungen)
  - [Titel / Einleitung](#_titel_einleitung)
  - [Testziele](#_testziele)
  - [Teststrategie und Teststufen](#_teststrategie_und_teststufen)
  - [Testobjekte](#_testobjekte)
  - [Testabdeckung](#_testabdeckung)
  - [Testrahmen](#_testrahmen)
  - [Testumgebung](#_testumgebung)
  - [Testfallbeschreibungen](#_testfallbeschreibungen)
  - [Testplan](#_testplan)
  - [Testorganisation und Zuständigkeiten](#_testorganisation_und_zuständigkeiten)
  - [Testinfrastruktur](#_testinfrastruktur)

</div>

</div>

<div id="content">

<div id="preamble">

<div class="sectionbody">

<div class="paragraph">

Wir orientieren uns hier primär an [HERMES](https://de.wikipedia.org/wiki/Hermes_(Projektmanagementmethode)) (**H**andbuch der **e**lektronischen **R**echenzentren des Bundes, eine **M**ethode zur **E**ntwicklung von **S**ystemen), dem offenen Standard zur Führung und Abwicklung von IT-Projekten des Bundes.

</div>

<div class="paragraph">

Es gibt auch noch den [829-2008 - IEEE Standard for Software and System Test Documentation](https://ieeexplore.ieee.org/document/4578383), an dem man sich auch orientieren kann (das ist aber ein riesiges Dokument mit über 100 Seiten).

</div>

</div>

</div>

<div class="sect1">

## Motivation

<div class="sectionbody">

<div class="paragraph">

Die Beschreibung zeigt auch schon die Ziele auf:

</div>

<div class="quoteblock">

> <div class="paragraph">
>
> Das Testkonzept beschreibt die Testziele, Testobjekte, Testarten, Testinfrastruktur sowie Testorganisation. Es umfasst ebenfalls die Testplanung und die Testfallbeschreibungen. Für jeden Testfall wird eine detaillierte Testfallbeschreibung erstellt. Diese stellt die Spezifikation des Tests dar. Die Testplanung legt den logischen und zeitlichen Ablauf der Tests fest. Das Testkonzept bildet die Grundlage, auf der die Testorganisation und die Testinfrastruktur bereitgestellt und die Tests durchgeführt werden. Tauchen neue Erkenntnisse auf, wird das Testkonzept nachgeführt.
>
> </div>

<div class="attribution">

— HERMES online  
[Testkonzept](https://www.hermes.admin.ch/de/projektmanagement/ergebnisse/testkonzept.html)

</div>

</div>

<div class="paragraph">

Das Testkonzept ist also dazu da, um unsere Tests zu organisieren, also eine Übersicht zu haben über:

</div>

<div class="ulist">

- Was wird getestet

- Wie wird getestet (Strategie)

- Wie sehen die Testfälle aus

- Welche Ressourcen werden benötigt (Zeit, Personen, Infrastruktur, …​)

- Was ist der Zeit-/Abhängigkeitsplan

</div>

<div class="paragraph">

Das Testkonzept ist ähnlich wie ein Projektplan oder ein Scrum Backlog nicht technischer Teil eines Projekts, sondern ein organisatorisches Artefakt.

</div>

</div>

</div>

<div class="sect1">

## Testkonzept - inhaltliche & formale Anforderungen

<div class="sectionbody">

<div class="paragraph">

Auf der HERMES-Website findet sich auch eine [Dokumentenvorlage](https://www.hermes.admin.ch/de/projektmanagement/ergebnisse/testkonzept.html).

</div>

<div class="paragraph">

Diese muss nicht 1:1 übernommen werden, aber folgende Elemente gehören dazu:

</div>

<div class="sect2">

### Titel / Einleitung

<div class="paragraph">

Folgende Elemente sollten vorhanden sein:

</div>

<div class="ulist">

- Projektname

- Projektinformationen

  <div class="ulist">

  - verantwortliche Personen (Projektleitung, Autor\*innen)

  - Kontaktdaten

  - Version / Status des Dokuments

  - wenn längeres Projekt: zugehörige Releaseversion

  </div>

- Inhaltsübersicht

- Kurzbeschreibung der Software

</div>

</div>

<div class="sect2">

### Testziele

<div class="paragraph">

Hier werden die groben, globalen Ziele der Tests beschrieben.

</div>

<div class="paragraph">

Eine Tabelle oder Nummerierung ist sinnvoll, um auf die Testziele Bezug nehmen zu können.

</div>

</div>

<div class="sect2">

### Teststrategie und Teststufen

<div class="paragraph">

Hier werden die gewählten Teststrategien (manuelle Tests, automatisierte Tests, white-/black-/grey-box Tests, …​) sowie deren Wichtigkeit beschrieben.

</div>

<div class="paragraph">

Überlegungen/Risikoabschätzungen wieso Dinge so gewählt wurden können auch hier erwähnt werden (z.B. Applikation mit Bestellung, korrekte Abrechnung ist enorm wichtig →Fokus auf System-tests).

</div>

<div class="paragraph">

Die verwendeten Teststufen werden erwähnt (Unit-test, Integration-test, …​).

</div>

</div>

<div class="sect2">

### Testobjekte

<div class="paragraph">

Hier wird beschrieben, welche Teile der Software getestet werden.

</div>

<div class="paragraph">

Das können Komponenten sein, Skripte/Batch jobs, User interfaces, …​.

</div>

</div>

<div class="sect2">

### Testabdeckung

<div class="paragraph">

Hier wird beschrieben, welche [Testziele](#_testziele) wie gut abgedeckt werden.

</div>

<div class="paragraph">

Ebenso werden hier Aspekte, die nicht getestet werden, erwähnt (z.B. Performance).

</div>

<div class="paragraph">

Dazu gibt es eine Übersicht über die Testfälle.

</div>

<div class="paragraph">

Eine Beurteilung der Testziele und Testabdeckung findet sich auch hier.

</div>

</div>

<div class="sect2">

### Testrahmen

<div class="paragraph">

Hier werden benötigte Voraussetzungen beschrieben ([Testumgebung](#_testumgebung) später), wie

</div>

<div class="ulist">

- benötigte Personen

- benötigte Zeit

</div>

<div class="paragraph">

Ebenso werden Kriterien für den Erfolg der Tests festgelegt (*pass/fail criteria*), sowie mögliche Fehlerstufen.

</div>

<div class="ulist">

- leichter Fehler

- schwerer Fehler

- …​

</div>

<div class="paragraph">

Ebenso werden Start- und Abbruchbedingungen für Tests festgelegt (z.B. Infrastruktur nicht verfügbar →Abbruch)

</div>

</div>

<div class="sect2">

### Testumgebung

<div class="paragraph">

Hier wird die Testumgebung beschrieben.

</div>

<div class="paragraph">

Anforderungen sind im Abschnitt [Testinfrastruktur](#_testinfrastruktur)

</div>

</div>

<div class="sect2">

### Testfallbeschreibungen

<div class="paragraph">

Hier werden die einzelnen Testfälle detailliert beschrieben.

</div>

<div class="paragraph">

Dazu gehören:

</div>

<div class="ulist">

- Voraussetzungen

- Testschritte

- erwartetes Ergebnis

</div>

<div class="paragraph">

Es muss nicht jeder einzelne Unittest so beschrieben werden.

</div>

</div>

<div class="sect2">

### Testplan

<div class="paragraph">

Hier wird beschrieben, wie Tests geplant sind.

</div>

</div>

<div class="sect2">

### Testorganisation und Zuständigkeiten

<div class="paragraph">

Hier werden die verantwortlichen Leute erwähnt.

</div>

<div class="paragraph">

Falls es Abhängigkeiten zwischen Tests gibt, werden diese auch hier erwähnt.

</div>

</div>

<div class="sect2">

### Testinfrastruktur

<div class="paragraph">

Hier werden die Anforderungen der Testumgebung beschrieben.

</div>

<div class="paragraph">

Unterkapitel, falls sinnvoll:

</div>

<div class="ulist">

- Testsystem

- Testdaten

- Testhilfsmittel

</div>

<div class="paragraph">

This document © 2025 by M. Herrmann is licensed under [CC BY-SA 4.0](https://creativecommons.org/licenses/by-sa/4.0/) <span class="image"><img src="https://mirrors.creativecommons.org/presskit/icons/cc.svg" width="20" alt="CC" /></span> <span class="image"><img src="https://mirrors.creativecommons.org/presskit/icons/by.svg" width="20" alt="BY" /></span> <span class="image"><img src="https://mirrors.creativecommons.org/presskit/icons/sa.svg" width="20" alt="SA" /></span>

</div>

</div>

</div>

</div>

</div>

<div id="footer">

<div id="footer-text">

Version 250905-45eedbf8  

</div>

</div>
