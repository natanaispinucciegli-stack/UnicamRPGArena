package it.unicam.cs.mpgc.rpg126322.model.strategies;

import it.unicam.cs.mpgc.rpg126322.model.GameCharacter;

public class HealStrategy implements AttackStrategy {
    @Override
    public int executeAttack(GameCharacter attacker, GameCharacter target) {

        int cura = 25;
        attacker.takeDamage(-cura);
        return cura;
    }
}