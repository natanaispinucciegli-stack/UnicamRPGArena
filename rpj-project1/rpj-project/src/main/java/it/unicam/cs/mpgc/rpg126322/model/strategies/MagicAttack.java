package it.unicam.cs.mpgc.rpg126322.model.strategies;

import it.unicam.cs.mpgc.rpg126322.model.GameCharacter;
import it.unicam.cs.mpgc.rpg126322.model.DiceRoller;

public class MagicAttack implements AttackStrategy {
    @Override
    public int executeAttack(GameCharacter attacker, GameCharacter target) {

        int potereMagico = DiceRoller.roll(12);
        int dannoBase = (attacker.getStrength() * 2) + potereMagico;


        int difesaMitigata = target.getDefense() / 2;
        int dannoFinale = Math.max(10, dannoBase - difesaMitigata);

        target.takeDamage(dannoFinale);
        return dannoFinale;
    }
}