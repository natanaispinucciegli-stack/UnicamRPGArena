package it.unicam.cs.mpgc.rpg126322.model;
import it.unicam.cs.mpgc.rpg126322.model.strategies.MeleeAttack;

public class Warrior extends GameCharacter {
    public Warrior(String name) { super(name, 100, 12, 8, new MeleeAttack()); }

    @Override
    public String getBattutaInizio() { return "🛡️ " + getName() + ": \"La mia spada difenderà l'onore di queste terre! Fatti avanti!\""; }
    @Override
    public String getUrloCritico() { return "🗣️ " + getName() + ": \"PER L'ONORE DI UNICAM! Assaggia il mio acciaio!!!\""; }
    @Override
    public String getProvocazioneSaluteBassa() { return "💬 " + getName() + " ha il fiatone e stringe lo scudo: \"Non... non cederò così facilmente!\""; }
    @Override
    public String getDichiarazioneVittoria() { return "💬 " + getName() + " rinfodera la spada: \"La giustizia ha trionfato oggi.\""; }
}