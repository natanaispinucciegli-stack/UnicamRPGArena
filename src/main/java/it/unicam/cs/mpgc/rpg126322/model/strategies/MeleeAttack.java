package it.unicam.cs.mpgc.rpg126322.model.strategies;

import it.unicam.cs.mpgc.rpg126322.model.GameCharacter;
import it.unicam.cs.mpgc.rpg126322.model.DiceRoller;

public class MeleeAttack implements AttackStrategy {
    @Override
    public int executeAttack(GameCharacter attacker, GameCharacter target) {
        // Lanciamo un dado da 6 per il bonus danno
        int bonusDado = DiceRoller.roll(6);
        int dannoBase = attacker.getStrength() + bonusDado;

        // Se esce 6 sul dado, è un COLPO CRITICO (Raddoppia il danno!)
        if (bonusDado == 6) {
            dannoBase *= 2;
        }

        // Calcoliamo il danno effettivo sottraendo la difesa del bersaglio (minimo 5 danni)
        int dannoFinale = Math.max(5, dannoBase - target.getDefense());

        target.takeDamage(dannoFinale);
        return dannoFinale;
    }
}