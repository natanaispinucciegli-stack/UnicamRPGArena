# 📌 Unicam RPG Arena

Unicam RPG Arena è un gioco di ruolo a turni sviluppato in Java con interfaccia grafica Swing.
Il giocatore sceglie un eroe, affronta tre livelli di combattimento nell'arena, può attaccare, usare una mossa speciale, curarsi con pozioni e salvare i progressi principali della partita su file JSON.

Il progetto è stato realizzato per l'esame di Metodologie della Programmazione e mostra l'uso di classi, ereditarietà, polimorfismo, interfacce, pattern Strategy, Factory, Repository e separazione Model-View-Controller.

---

## 🚀 Come eseguire il progetto

### Prerequisiti

- JDK 25
- Gradle 9.1.0 oppure Gradle Wrapper incluso nel progetto
- Ambiente desktop con supporto grafico, necessario per visualizzare la finestra Swing

Il progetto include i file `gradlew` e `gradlew.bat`, configurati per usare Gradle 9.1.0, quindi può essere compilato ed eseguito anche senza un'installazione globale di Gradle.
Nel file `build.gradle` la toolchain Java è impostata su JDK 25.

### Istruzioni

Aprire un terminale nella cartella principale del progetto, cioè la cartella che contiene il file `build.gradle`.

```bash
cd rpj-project
```

Se il progetto è stato scaricato in una cartella esterna che contiene un'altra sottocartella `rpj-project`, entrare nella sottocartella interna che contiene `build.gradle`.

### Build del progetto

Linux/macOS:

```bash
./gradlew build
```

Windows PowerShell:

```powershell
.\gradlew.bat build
```

### Esecuzione

Linux/macOS:

```bash
./gradlew run
```

Windows PowerShell:

```powershell
.\gradlew.bat run
```

La classe principale configurata in Gradle è:

```text
it.unicam.cs.mpgc.rpg126322.MainApp
```

All'avvio viene mostrato un menu iniziale da cui è possibile iniziare una nuova partita oppure caricare un salvataggio esistente.

---

## 🧪 Test

Il progetto contiene test unitari in:

```text
src/test/java/it/unicam/cs/mpgc/rpg126322/model/CombatTest.java
```

Per eseguire i test:

Linux/macOS:

```bash
./gradlew test
```

Windows PowerShell:

```powershell
.\gradlew.bat test
```

I test verificano il comportamento base del combattimento:

- un attacco produce un danno positivo;
- i punti vita del bersaglio diminuiscono in modo coerente con il danno subito;
- la vita di un personaggio sconfitto non scende sotto zero.

Nel repository analizzato i comandi `.\gradlew.bat clean test`, `.\gradlew.bat test` e `.\gradlew.bat build` sono stati eseguiti con successo.

---

## 📁 Struttura del progetto

```text
.
|-- build.gradle
|-- settings.gradle
|-- gradlew
|-- gradlew.bat
|-- src/
|   |-- main/java/it/unicam/cs/mpgc/rpg126322/
|   |   |-- MainApp.java
|   |   |-- Launcher.java
|   |   |-- controller/
|   |   |   `-- GameController.java
|   |   |-- model/
|   |   |   |-- GameCharacter.java
|   |   |   |-- Warrior.java
|   |   |   |-- Rogue.java
|   |   |   |-- Wizard.java
|   |   |   |-- Golem.java
|   |   |   |-- Potion.java
|   |   |   |-- DiceRoller.java
|   |   |   |-- DialogueManager.java
|   |   |   |-- factory/
|   |   |   |   `-- CharacterFactory.java
|   |   |   `-- strategies/
|   |   |       |-- AttackStrategy.java
|   |   |       |-- MeleeAttack.java
|   |   |       |-- MagicAttack.java
|   |   |       |-- SpecialAttack.java
|   |   |       `-- HealStrategy.java
|   |   |-- persistence/
|   |   |   |-- GameSaveData.java
|   |   |   |-- GameSaveRepository.java
|   |   |   `-- JsonGameSaveRepository.java
|   |   `-- view/
|   |       |-- GameWindow.java
|   |       `-- BattleLogPanel.java
|   `-- test/java/it/unicam/cs/mpgc/rpg126322/model/
|       `-- CombatTest.java
|-- salvataggio.json
|-- save_slot_2.json
`-- save_slot_3.json
```

---

## 🧩 Architettura

Il progetto segue una struttura ispirata al pattern MVC.

- `model`: contiene personaggi, statistiche, dadi, pozioni e strategie di attacco.
- `view`: contiene l'interfaccia grafica Swing.
- `controller`: gestisce il flusso della partita e collega modello e interfaccia.
- `persistence`: gestisce salvataggio e caricamento su file JSON.

Classi principali:

- `MainApp`: avvia l'applicazione Swing e crea vista e controller.
- `GameController`: coordina nuova partita, caricamento, attacchi, cura, salvataggio, passaggio di livello e fine partita.
- `GameCharacter`: classe astratta base per tutti i personaggi.
- `Warrior`, `Rogue`, `Wizard`, `Golem`: personaggi concreti con statistiche e dialoghi specifici.
- `AttackStrategy`: interfaccia comune per le strategie di attacco.
- `MeleeAttack`, `MagicAttack`, `SpecialAttack`: implementazioni delle strategie di combattimento.
- `CharacterFactory`: crea i personaggi in base al tipo richiesto.
- `GameSaveRepository` e `JsonGameSaveRepository`: gestiscono la persistenza dei salvataggi.
- `GameWindow` e `BattleLogPanel`: costruiscono e aggiornano l'interfaccia grafica.

Scelte progettuali principali:

- uso di ereditarietà e polimorfismo per i personaggi;
- uso del pattern Strategy per separare le logiche di attacco;
- uso del pattern Factory per centralizzare la creazione dei personaggi;
- uso del pattern Repository per isolare la persistenza JSON;
- separazione tra logica di gioco e interfaccia grafica.

---

## 🎮 Funzionalità principali

- scelta dell'eroe tra Guerriero (`Arthur`) e Ladro (`Shadow`);
- combattimento a turni contro tre nemici progressivi;
- attacco base, attacco speciale con cooldown e cura tramite pozioni;
- log testuale della battaglia;
- barre della vita per eroe e nemico;
- salvataggio su tre slot JSON;
- caricamento dei progressi principali della partita.

Il salvataggio ripristina:

- tipo e nome dell'eroe;
- punti vita dell'eroe;
- livello corrente;
- quantità di pozioni;
- cooldown della mossa speciale.

Il salvataggio non ripristina lo stato del singolo combattimento in corso: quando si carica una partita, il nemico viene ricreato all'inizio del livello salvato.

---

## 🤖 Uso di strumenti di AI

Durante lo sviluppo e la revisione del progetto è stato utilizzato ChatGPT come strumento di supporto.

L'intelligenza artificiale è stata usata per:

- revisione del codice;
- individuazione di possibili errori o miglioramenti;
- revisione e organizzazione della documentazione;
- miglioramento della chiarezza del README;
- suggerimenti su struttura del progetto, leggibilità e separazione delle responsabilità;
- supporto nella correzione della persistenza dei salvataggi;
- supporto nella verifica dei comandi Gradle e dei test.

I suggerimenti prodotti dall'AI sono stati valutati, modificati e verificati prima dell'integrazione nel progetto.
Il codice finale, le scelte progettuali, la comprensione del funzionamento del programma e la verifica del corretto comportamento restano responsabilità degli autori del progetto.

L'AI è stata quindi utilizzata come supporto alla revisione e alla documentazione, non come sostituto del lavoro di progettazione, implementazione e verifica.

Per una descrizione più dettagliata dell'uso dell'AI, è possibile utilizzare la Wiki del repository.

---

## ⚠️ Note

- Il progetto usa Swing, quindi deve essere eseguito in un ambiente con interfaccia grafica.
- Il file `salvataggio.json` è presente nella cartella principale, ma la persistenza attuale usa gli slot `save_slot_1.json`, `save_slot_2.json` e `save_slot_3.json`.
- Le classi `DialogueManager` e `HealStrategy` sono presenti nel codice, ma il flusso principale usa i dialoghi dei personaggi e la cura tramite `Potion`.
- Gli autori non sono indicati nei file presenti nel repository analizzato.

---

## Autori

Natanaiel Armin Spinucci Egli 
Matricola:126322
