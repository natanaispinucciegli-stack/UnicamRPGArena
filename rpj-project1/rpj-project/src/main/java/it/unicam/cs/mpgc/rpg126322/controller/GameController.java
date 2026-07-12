package it.unicam.cs.mpgc.rpg126322.controller;

import it.unicam.cs.mpgc.rpg126322.model.*;
import it.unicam.cs.mpgc.rpg126322.model.factory.CharacterFactory;
import it.unicam.cs.mpgc.rpg126322.persistence.*;
import it.unicam.cs.mpgc.rpg126322.view.GameWindow;
import javax.swing.Timer;


public class GameController {
    private final GameWindow view;
    private final GameSaveRepository repository = new JsonGameSaveRepository();
    private GameCharacter eroe;
    private GameCharacter nemico;
    private Potion pozioneCura;


    private int currentLevel = 1;
    private int specialCooldown = 0;
    private boolean provNemico = false;
    private boolean provEroe = false;

    public GameController(GameWindow view) {
        this.view = view;
        initGame();
    }


    public void initGame() {
        String[] opzioniAvvio = {"Nuova Partita ⚔️", "Carica Salvataggio Precedente 💾"};
        String sceltaAvvio = (String) javax.swing.JOptionPane.showInputDialog(
                null, "Benvenuto nell'Arena. Cosa vuoi fare?", "Menu Principale",
                javax.swing.JOptionPane.QUESTION_MESSAGE, null, opzioniAvvio, opzioniAvvio[0]
        );

        if (sceltaAvvio != null && sceltaAvvio.contains("Carica")) {
            Integer[] slots = {1, 2, 3};
            Integer slotScelto = (Integer) javax.swing.JOptionPane.showInputDialog(
                    null, "Seleziona lo Slot da cui caricare i tuoi progressi:", "Carica Partita 💾",
                    javax.swing.JOptionPane.QUESTION_MESSAGE, null, slots, slots[0]
            );

            if (slotScelto != null) {
                if (repository.exists(slotScelto)) {
                    try {

                        GameSaveData data = repository.load(slotScelto);

                        if (data != null) {

                            this.eroe = CharacterFactory.createCharacter(data.getHeroType(), data.getHeroName());
                            int dannoEroe = this.eroe.getMaxHealth() - data.getHeroHealth();
                            if (dannoEroe > 0) {
                                this.eroe.takeDamage(dannoEroe);
                            }


                            this.currentLevel = data.getCurrentLevel();


                            this.nemico = CharacterFactory.createCharacter(data.getEnemyType(), data.getEnemyName());
                            int dannoNemico = this.nemico.getMaxHealth() - data.getEnemyHealth();
                            if (dannoNemico > 0) {
                                this.nemico.takeDamage(dannoNemico);
                            }


                            this.pozioneCura = new Potion("Pozione di Vita", 30, data.getPotionQuantity());
                            this.specialCooldown = data.getSpecialCooldown();

                            view.getLogPanel().addLog("⚔️ Sistema: Partita caricata con successo dallo Slot " + slotScelto);
                            javax.swing.JOptionPane.showMessageDialog(null, "Eroe e Nemico caricati con successo dallo Slot " + slotScelto + "!\nBentornato, " + eroe.getName());
                            aggiornaStatoView();
                            return;
                        }
                    } catch (Exception e) {
                        javax.swing.JOptionPane.showMessageDialog(null, "Errore durante la lettura del file di salvataggio.", "Errore I/O ❌", javax.swing.JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                    }
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "Lo Slot " + slotScelto + " è vuoto!\nProcediamo con la creazione di un nuovo eroe.", "Slot Vuoto ⏳", javax.swing.JOptionPane.WARNING_MESSAGE);
                }
            }
        }


        String[] eroi = {"Guerriero (Arthur)", "Ladro (Shadow)"};
        String sEroe = (String) javax.swing.JOptionPane.showInputDialog(null, "Scegli la tua classe per la scalata dei 3 Livelli:", "Selezione Campione 🛡️", 1, null, eroi, eroi[0]);
        this.eroe = (sEroe == null || sEroe.contains("Guerriero")) ? CharacterFactory.createCharacter("warrior", "Arthur") : CharacterFactory.createCharacter("rogue", "Shadow");

        this.currentLevel = 1;
        caricaNemicoPerLivello();
        this.pozioneCura = new Potion("Pozione di Vita", 30, 3);
        this.specialCooldown = 0;

        view.getLogPanel().addLog("🎮 Benvenuto nell'arena! La tua scalata ha inizio.");
        aggiornaStatoView();
    }



    private void caricaNemicoPerLivello() {
        this.provNemico = false;
        switch (currentLevel) {
            case 1:
                this.nemico = CharacterFactory.createCharacter("wizard", "Malekith");
                break;
            case 2:
                this.nemico = CharacterFactory.createCharacter("golem", "Roccia");
                break;
            case 3:

                this.nemico = CharacterFactory.createCharacter("warrior", "Arthur Oscuro");
                break;
        }
    }

    public void avvia() {
        view.configuraInterfaccia(this);
        view.getLogPanel().addLog("🏟️ BENVENUTO NELL'ARENA DI UNICAM - LIVELLO " + currentLevel + " 🏟️");
        view.getLogPanel().addLog(eroe.getBattutaInizio());
        view.getLogPanel().addLog(nemico.getBattutaInizio());
        aggiornaStatoView();
    }

    public void gestisciAttacco() {
        if (!eroe.isAlive() || !nemico.isAlive()) return;
        view.disabilitaAzioni();

        int danno = eroe.performAttack(nemico);
        if (danno >= 25) view.getLogPanel().addLog(eroe.getUrloCritico());
        view.getLogPanel().addLog("⚔️ " + eroe.getName() + " infligge " + danno + " danni!");

        if (specialCooldown > 0) specialCooldown--;
        if (controllaFinePartita()) return;
        controllaSaluteBassa();

        new Timer(1200, e -> {
            if (nemico.isAlive() && eroe.isAlive()) {
                eseguiTurnoNemico();
                if (eroe.isAlive() && nemico.isAlive()) view.riattivaAzioni();
            }
            ((Timer)e.getSource()).stop();
        }).start();
    }

    public void gestisciAttaccoSpeciale() {
        if (!eroe.isAlive() || !nemico.isAlive() || specialCooldown > 0) return;
        view.disabilitaAzioni();

        eroe.setAttackStrategy(new it.unicam.cs.mpgc.rpg126322.model.strategies.SpecialAttack());
        int danno = eroe.performAttack(nemico);

        view.getLogPanel().addLog(eroe.getUrloCritico());
        view.getLogPanel().addLog("🔥 SPECIALE: " + eroe.getName() + " lancia un colpo da " + danno + " danni!");

        eroe.setAttackStrategy(new it.unicam.cs.mpgc.rpg126322.model.strategies.MeleeAttack());
        specialCooldown = 3;

        if (controllaFinePartita()) return;
        controllaSaluteBassa();

        new Timer(1200, e -> {
            if (nemico.isAlive()) eseguiTurnoNemico();
            if (eroe.isAlive() && nemico.isAlive()) view.riattivaAzioni();
            ((Timer)e.getSource()).stop();
        }).start();
    }

    public void gestisciCura() {
        if (pozioneCura.use()) {
            view.disabilitaAzioni();
            eroe.takeDamage(-pozioneCura.getHealAmount());
            view.getLogPanel().addLog("🧪 " + eroe.getName() + " consuma una pozione (+30 HP).");
            if (specialCooldown > 0) specialCooldown--;
            aggiornaStatoView();

            new Timer(1200, e -> {
                if (nemico.isAlive()) eseguiTurnoNemico();
                if (eroe.isAlive() && nemico.isAlive()) view.riattivaAzioni();
                ((Timer)e.getSource()).stop();
            }).start();
        }
    }

    public void gestisciSalvataggio() {

        if (eroe == null || !eroe.isAlive()) {
            javax.swing.JOptionPane.showMessageDialog(null, "Impossibile salvare: non c'è nessun eroe in vita nell'arena!", "Errore Salvataggio ❌", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }


        Integer[] slots = {1, 2, 3};
        Integer slotScelto = (Integer) javax.swing.JOptionPane.showInputDialog(
                null,
                "Scegli lo Slot dove sovrascrivere o salvare i tuoi progressi attuali:",
                "Salva Partita 💾",
                javax.swing.JOptionPane.QUESTION_MESSAGE,
                null,
                slots,
                slots[0]
        );


        if (slotScelto != null) {
            try {

                String enemyType = (currentLevel == 1) ? "golem" : (currentLevel == 2) ? "mage" : "warrior";


                GameSaveData saveData = new GameSaveData(
                        getHeroType(eroe),
                        eroe.getName(),
                        eroe.getHealth(),
                        enemyType,
                        nemico.getName(),
                        nemico.getHealth(),
                        nemico.getMaxHealth(),
                        currentLevel,
                        pozioneCura.getQuantity(),
                        specialCooldown
                );


                repository.save(saveData, slotScelto);

                view.getLogPanel().addLog("💾 Sistema: Stato di eroe e nemico registrato nello Slot " + slotScelto);
                javax.swing.JOptionPane.showMessageDialog(null, "Gioco salvato correttamente nello Slot " + slotScelto + "!", "Salvataggio Completato", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(null, "Errore imprevisto durante il salvataggio su file.", "Errore I/O ❌", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private int normalizzaLivello(int level) {
        return Math.max(1, Math.min(3, level));
    }

    private String getHeroType(GameCharacter character) {
        if (character instanceof Rogue) {
            return "rogue";
        }
        return "warrior";
    }

    private void eseguiTurnoNemico() {
        int danno = nemico.performAttack(eroe);
        if (danno >= 30) view.getLogPanel().addLog(nemico.getUrloCritico());
        view.getLogPanel().addLog("🔮 " + nemico.getName() + " risponde infliggendo " + danno + " danni!");
        controllaFinePartita();
        controllaSaluteBassa();
    }

    private void controllaSaluteBassa() {
        if (nemico.getHealth() <= 35 && !provNemico) {
            view.getLogPanel().addLog(nemico.getProvocazioneSaluteBassa());
            provNemico = true;
        }
        if (eroe.getHealth() <= 30 && !provEroe) {
            view.getLogPanel().addLog(eroe.getProvocazioneSaluteBassa());
            provEroe = true;
        }
    }


    private boolean controllaFinePartita() {
        aggiornaStatoView();


        if (!nemico.isAlive()) {
            view.getLogPanel().addLog(eroe.getDichiarazioneVittoria());
            view.getLogPanel().addLog("💀 " + nemico.getName() + " è stato sconfitto!");

            if (currentLevel < 3) {

                currentLevel++;
                javax.swing.JOptionPane.showMessageDialog(null, "Hai superato il Livello " + (currentLevel - 1) + "!\nPreparati per la prossima ondata.", "Livello Superato! ⚔️", 1);


                caricaNemicoPerLivello();


                eroe.takeDamage(-25);
                specialCooldown = 0;

                view.getLogPanel().addLog("\n═══════════════════════════════════════");
                view.getLogPanel().addLog("🏟️ INIZIO LIVELLO " + currentLevel + " 🏟️");
                view.getLogPanel().addLog(eroe.getBattutaInizio());
                view.getLogPanel().addLog(nemico.getBattutaInizio());

                view.riattivaAzioni();
                aggiornaStatoView();
                return true;
            } else {
                // Vittoria totale del terzo livello
                view.getLogPanel().addLog("🏆 COMPLIMENTI! Hai ripulito l'intera Arena di Unicam e vinto il gioco!");
                view.disabilitaAzioni();
                return true;
            }
        }


        if (!eroe.isAlive()) {
            view.getLogPanel().addLog(nemico.getDichiarazioneVittoria());
            view.getLogPanel().addLog("💀 GAME OVER! Sei caduto al Livello " + currentLevel);
            view.disabilitaAzioni();
            return true;
        }
        return false;
    }

    private void aggiornaStatoView() {
        String tSpec = (specialCooldown == 0) ? "🔥 Mossa Speciale" : "⏳ Ricarica (" + specialCooldown + ")";


        view.aggiornaGraficaDinamica(
                eroe.getName() + " (Lvl " + currentLevel + ")", eroe.getHealth(), eroe.getMaxHealth(),
                nemico.getName(), nemico.getHealth(), nemico.getMaxHealth(),
                "❤️ Cura (" + pozioneCura.getQuantity() + ")", tSpec, specialCooldown == 0
        );
    }
}
