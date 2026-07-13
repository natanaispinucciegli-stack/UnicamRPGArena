package it.unicam.cs.mpgc.rpg126322;

import it.unicam.cs.mpgc.rpg126322.controller.GameController;
import it.unicam.cs.mpgc.rpg126322.view.GameWindow;

public class MainApp {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {

            GameWindow window = new GameWindow();


            GameController controller = new GameController(window);


            controller.avvia();
        });
    }
}