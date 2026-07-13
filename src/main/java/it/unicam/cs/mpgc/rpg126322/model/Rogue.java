package it.unicam.cs.mpgc.rpg126322.model;

import it.unicam.cs.mpgc.rpg126322.model.strategies.MeleeAttack;


public class Rogue extends GameCharacter {

    public Rogue(String name) {

        super(name, 85, 14, 5, new MeleeAttack());
    }

    @Override
    public String getBattutaInizio() {
        return "🗡️ " + getName() + ": \"Sei troppo lento per i miei pugnali. Non mi vedrai nemmeno arrivare.\"";
    }

    @Override
    public String getUrloCritico() {
        return "🗣️ " + getName() + ": \"Dritto dritto ai punti vitali! COLPO BASSO!\"";
    }

    @Override
    public String getProvocazioneSaluteBassa() {

        return "💬 " + getName() + " sputa sangue e scompare nelle ombre: \"Pensi di avermi preso? Guarda meglio!\"";
    }

    @Override
    public String getDichiarazioneVittoria() {
        return "💬 " + getName() + " pulisce i pugnali: \"Troppo facile. Le tue tasche ora appartengono a me.\"";
    }
}