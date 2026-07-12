package it.unicam.cs.mpgc.rpg126322.model.factory;

import it.unicam.cs.mpgc.rpg126322.model.*;

public class CharacterFactory {

    public static GameCharacter createCharacter(String type, String name) {
        switch (type.toLowerCase()) {
            case "warrior":
                return new Warrior(name);
            case "wizard":
                return new Wizard(name);
            case "rogue":
                return new Rogue(name);
            case "golem":
                return new Golem(name);
            default:
                throw new IllegalArgumentException("Tipo di personaggio non supportato: " + type);
        }
    }
}