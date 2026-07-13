package it.unicam.cs.mpgc.rpg126322.model.strategies;

import it.unicam.cs.mpgc.rpg126322.model.GameCharacter;
import it.unicam.cs.mpgc.rpg126322.model.DiceRoller;

public class SpecialAttack implements AttackStrategy {
    @Override
    public int executeAttack(GameCharacter attacker, GameCharacter target) {

        int tiroDado = DiceRoller.roll(20);


        int dannoFinale = attacker.getStrength() + 15 + tiroDado;


        target.takeDamage(dannoFinale);
        return dannoFinale;
    }
}