package it.unicam.cs.mpgc.rpg126322.view;

import javax.swing.*;
import java.awt.*;

public class BattleLogPanel extends JPanel {
    private final JTextArea logArea;

    public BattleLogPanel() {
        setLayout(new BorderLayout());
        logArea = new JTextArea(6, 30);
        logArea.setEditable(false);
        logArea.setBackground(new Color(30, 30, 30));
        logArea.setForeground(new Color(0, 255, 64));
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Cronologia Battaglia"));
    }

    public void addLog(String message) {
        logArea.append(" » " + message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
}