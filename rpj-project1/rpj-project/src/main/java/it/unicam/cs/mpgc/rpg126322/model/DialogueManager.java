package it.unicam.cs.mpgc.rpg126322.model;

public class DialogueManager {

    public static String getInizioBattaglia(String eroe, String nemico) {
        return "💬 " + nemico + ": \"Finalmente sei qui, " + eroe + ". Questa arena sarà la tua tomba!\"\n" +
                " 💬 " + eroe + ": \"Risparmia il fiato, stregone! La mia spada parlerà per me!\"";
    }

    public static String getCriticoEroe(String eroe) {
        return "🗣️ " + eroe + ": \"PER L'ONORE DI UNICAM! Assaggia questo!!!\"";
    }

    public static String getCriticoNemico(String nemico) {
        return "🗣️ " + nemico + ": \"Sciocco mortale, piegati al potere del vuoto!!!\"";
    }

    public static String getProvocazioneSaluteBassa(String personaggio, boolean isEroe) {
        if (isEroe) {

            return "💬 " + personaggio + " ha il fiatone e stringe i denti: \"Non... non finisce qui! Posso farcela!\"";
        } else {
            return "💬 " + personaggio + " ride malignamente: \"Pensi davvero che basti questo per scalfire la mia magia?\"";
        }
    }

    public static String getVittoriaEroe(String eroe, String nemico) {
        return "💬 " + eroe + " rinfodera la spada: \"La giustizia ha trionfato. Riposa in pace, " + nemico + ".\"";
    }

    public static String getSconfittaEroe(String nemico) {
        return "💬 " + nemico + " svetta sul tuo corpo: \"Un altro insolente cancellato dalla storia. Prevedibile.\"";
    }
}