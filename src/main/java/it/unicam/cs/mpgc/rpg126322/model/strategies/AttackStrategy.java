package it.unicam.cs.mpgc.rpg126322.model.strategies;

import it.unicam.cs.mpgc.rpg126322.model.GameCharacter;

public interface AttackStrategy {
    /**
     * Esegue l'attacco da un personaggio a un bersaglio.
     * @param attacker Chi attacca
     * @param target Chi subisce
     * @return I punti danno inflitti
     */
    int executeAttack(GameCharacter attacker, GameCharacter target);
}
