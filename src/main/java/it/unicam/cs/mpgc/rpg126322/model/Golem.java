package it.unicam.cs.mpgc.rpg126322.model;
import it.unicam.cs.mpgc.rpg126322.model.strategies.MeleeAttack;

public class Golem extends GameCharacter {
    public Golem(String name) { super(name, 130, 11, 12, new MeleeAttack()); }

    @Override
    public String getBattutaInizio() { return "🪨 " + getName() + ": *I terreni tremano... Il golem emette un ruggito di pietra cupo*"; }
    @Override
    public String getUrloCritico() { return "🗣️ " + getName() + ": *SBAM! Il Golem si scaglia al suolo con un impatto sismico!*"; }
    @Override
    public String getProvocazioneSaluteBassa() { return "💬 " + getName() + " perde pezzi di roccia: *La pietra crepita, ma il nucleo energetico pulsa più forte!*"; }
    @Override
    public String getDichiarazioneVittoria() { return "💬 " + getName() + ": *Il colosso calpesta i resti dell'avversario e torna immobile*"; }
}