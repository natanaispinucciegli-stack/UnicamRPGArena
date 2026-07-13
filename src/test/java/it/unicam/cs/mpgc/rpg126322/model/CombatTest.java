package it.unicam.cs.mpgc.rpg126322.model;

import it.unicam.cs.mpgc.rpg126322.model.factory.CharacterFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CombatTest {

    @Test
    public void testAttaccoEComputazioneDanni() {
        GameCharacter eroe = CharacterFactory.createCharacter("warrior", "Arthur");
        GameCharacter nemico = CharacterFactory.createCharacter("wizard", "Malekith");

        int hpInizialiNemico = nemico.getHealth(); // 90 per il wizard aggiornato
        int dannoInflitto = eroe.performAttack(nemico);

        // Con il dado il danno del guerriero oscilla, ma deve essere almeno > 0
        assertTrue(dannoInflitto > 0, "Il danno inflitto deve essere maggiore di zero");

        // Verifichiamo che la vita del nemico sia effettivamente scesa del valore del danno
        assertEquals(hpInizialiNemico - dannoInflitto, nemico.getHealth(), "La vita del nemico deve ridursi del danno subito");
    }

    @Test
    public void testMortePersonaggio() {
        GameCharacter eroe = CharacterFactory.createCharacter("warrior", "Arthur");
        GameCharacter nemico = CharacterFactory.createCharacter("wizard", "Malekith");

        // Forza l'eroe ad attaccare molte volte per azzerare i 90 HP del nemico
        for (int i = 0; i < 10; i++) {
            eroe.performAttack(nemico);
        }

        assertFalse(nemico.isAlive(), "Il nemico dopo 10 attacchi dovrebbe essere morto");
        assertEquals(0, nemico.getHealth(), "La vita non deve scendere sotto lo zero");
    }
}