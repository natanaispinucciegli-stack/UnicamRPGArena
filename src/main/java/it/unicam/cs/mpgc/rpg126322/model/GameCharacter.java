package it.unicam.cs.mpgc.rpg126322.model;

import it.unicam.cs.mpgc.rpg126322.model.strategies.AttackStrategy;

public abstract class GameCharacter {
    private final String name;
    private int health;
    private final int maxHealth;
    private final int strength;
    private final int defense;
    private AttackStrategy attackStrategy;

    public GameCharacter(String name, int health, int strength, int defense, AttackStrategy attackStrategy) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.strength = strength;
        this.defense = defense;
        this.attackStrategy = attackStrategy;
    }

    // --- AGGIUNGI O CONTROLLA QUESTO METODO ---
    public boolean isAlive() {
        return this.health > 0;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
        if (this.health > this.maxHealth) {
            this.health = this.maxHealth;
        }
    }

    public int performAttack(GameCharacter target) {
        return this.attackStrategy.executeAttack(this, target);
    }


    public void setAttackStrategy(AttackStrategy attackStrategy) {
        this.attackStrategy = attackStrategy;
    }


    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getStrength() { return strength; }
    public int getDefense() { return defense; }
    public abstract String getBattutaInizio();
    public abstract String getUrloCritico();
    public abstract String getProvocazioneSaluteBassa();
    public abstract String getDichiarazioneVittoria();
}