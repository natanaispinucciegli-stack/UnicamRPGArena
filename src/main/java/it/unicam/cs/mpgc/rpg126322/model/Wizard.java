package it.unicam.cs.mpgc.rpg126322.model;
import it.unicam.cs.mpgc.rpg126322.model.strategies.MagicAttack;

public class Wizard extends GameCharacter {
    public Wizard(String name) { super(name, 90, 10, 3, new MagicAttack()); }

    @Override
    public String getBattutaInizio() { return "🔮 " + getName() + ": \"Sciocchi mortali, l'arena sarà consumata dal potere del vuoto!\""; }
    @Override
    public String getUrloCritico() { return "🗣️ " + getName() + ": \"Piegati davanti alle fiamme arcane dell'oblio!!!\""; }
    @Override
    public String getProvocazioneSaluteBassa() { return "💬 " + getName() + " ride malignamente mentre fluttua: \"Pensi che la carne scalfisca la magia?\""; }
    @Override
    public String getDichiarazioneVittoria() { return "💬 " + getName() + " evoca un cerchio magico: \"Un altro insolente cancellato dalla storia.\""; }
}