package it.unicam.cs.mpgc.rpg126322.view;

import it.unicam.cs.mpgc.rpg126322.controller.GameController;
import javax.swing.*;
import java.awt.*;

public class GameWindow {
    private JProgressBar eroeBar;
    private JProgressBar nemicoBar;
    private JLabel eroeIconLabel;
    private JLabel nemicoIconLabel;
    private JButton attackButton;
    private JButton specialButton;
    private JButton healButton;
    private JButton saveButton;


    private BattleLogPanel logPanel = new BattleLogPanel();
    private JFrame frame;

    public void configuraInterfaccia(GameController controller) {
        frame = new JFrame("⚔️ Unicam RPG Arena - Edizione Mitica ⚔️");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 600);
        frame.setLocationRelativeTo(null);


        JPanel mainPanel = new JPanel(new BorderLayout(0, 15)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(138, 43, 226), getWidth(), getHeight(), new Color(0, 191, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 30, 0));
        statsPanel.setOpaque(false);


        JPanel eroeContainer = new JPanel(new BorderLayout(15, 0));
        eroeContainer.setBackground(new Color(45, 45, 45));
        eroeContainer.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        eroeIconLabel = creaRiquadroIcona();
        eroeBar = creaBarraSalute();
        eroeContainer.add(eroeIconLabel, BorderLayout.WEST);
        eroeContainer.add(eroeBar, BorderLayout.CENTER);


        JPanel nemicoContainer = new JPanel(new BorderLayout(15, 0));
        nemicoContainer.setBackground(new Color(45, 45, 45));
        nemicoContainer.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        nemicoIconLabel = creaRiquadroIcona();
        nemicoBar = creaBarraSalute();
        nemicoContainer.add(nemicoIconLabel, BorderLayout.WEST);
        nemicoContainer.add(nemicoBar, BorderLayout.CENTER);

        statsPanel.add(eroeContainer);
        statsPanel.add(nemicoContainer);


        logPanel.setOpaque(true);
        logPanel.setBackground(new Color(20, 20, 20));

        JPanel logWrapper = new JPanel(new BorderLayout());
        logWrapper.setOpaque(false);
        logWrapper.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                " Cronologia Battaglia ",
                0, 0,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE
        ));
        logWrapper.add(logPanel, BorderLayout.CENTER);


        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controlPanel.setOpaque(false);

        attackButton = creaPulsanteStilizzato("⚔️ Attacca", new Color(230, 57, 70));
        specialButton = creaPulsanteStilizzato("🔥 Speciale", new Color(241, 135, 1));
        healButton = creaPulsanteStilizzato("➕ Cura", new Color(46, 176, 134));
        saveButton = creaPulsanteStilizzato("💾 Salva", new Color(69, 123, 157));

        attackButton.addActionListener(e -> controller.gestisciAttacco());
        specialButton.addActionListener(e -> controller.gestisciAttaccoSpeciale());
        healButton.addActionListener(e -> controller.gestisciCura());
        saveButton.addActionListener(e -> controller.gestisciSalvataggio());

        controlPanel.add(attackButton);
        controlPanel.add(specialButton);
        controlPanel.add(healButton);
        controlPanel.add(saveButton);

        mainPanel.add(statsPanel, BorderLayout.NORTH);
        mainPanel.add(logWrapper, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public void aggiornaGraficaDinamica(String nomeEroe, int hpEroe, int maxHpEroe,
                                        String nomeNemico, int hpNemico, int maxHpNemico,
                                        String testoCura, String testoSpecial, boolean specialDisponibile) {


        if (eroeBar == null || nemicoBar == null || eroeIconLabel == null || nemicoIconLabel == null) {
            return;
        }

        eroeBar.setMaximum(maxHpEroe);
        eroeBar.setValue(hpEroe);
        eroeBar.setString(nomeEroe + " (" + hpEroe + "/" + maxHpEroe + " HP)");
        eroeIconLabel.setText(ottieniEmojiPersonaggio(nomeEroe));

        nemicoBar.setMaximum(maxHpNemico);
        nemicoBar.setValue(hpNemico);
        nemicoBar.setString(nomeNemico + " (" + hpNemico + "/" + maxHpNemico + " HP)");
        nemicoIconLabel.setText(ottieniEmojiPersonaggio(nomeNemico));

        gestisciColoreBarra(eroeBar, (double) hpEroe / maxHpEroe);
        gestisciColoreBarra(nemicoBar, (double) hpNemico / maxHpNemico);

        if (healButton != null) healButton.setText(testoCura);
        if (specialButton != null) {
            specialButton.setText(testoSpecial);
            specialButton.setEnabled(specialDisponibile);
        }
    }

    private JLabel creaRiquadroIcona() {
        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(50, 50));
        label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JProgressBar creaBarraSalute() {
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setStringPainted(true);
        bar.setFont(new Font("Arial", Font.BOLD, 13));
        bar.setForeground(new Color(46, 204, 113));
        bar.setBackground(new Color(35, 35, 35));
        bar.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        return bar;
    }

    private JButton creaPulsanteStilizzato(String testo, Color coloreBase) {
        JButton btn = new JButton(testo);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(coloreBase);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        return btn;
    }

    private void gestisciColoreBarra(JProgressBar bar, double percentuale) {
        if (percentuale > 0.5) bar.setForeground(new Color(46, 204, 113));
        else if (percentuale > 0.2) bar.setForeground(new Color(241, 196, 15));
        else bar.setForeground(new Color(231, 76, 60));
    }

    private String ottieniEmojiPersonaggio(String nomeCompleto) {
        String nome = nomeCompleto.toLowerCase();
        if (nome.contains("oscuro")) return "😈";
        if (nome.contains("arthur")) return "🧑‍🎤";
        if (nome.contains("shadow")) return "🗡️";
        if (nome.contains("malekith")) return "🧙";
        if (nome.contains("roccia") || nome.contains("golem")) return "🧌";
        return "👤";
    }

    public void disabilitaAzioni() {
        attackButton.setEnabled(false);
        specialButton.setEnabled(false);
        healButton.setEnabled(false);
    }

    public void riattivaAzioni() {
        attackButton.setEnabled(true);
        healButton.setEnabled(true);
    }

    public BattleLogPanel getLogPanel() { return logPanel; }
}