package com.codecool.roguelike.ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;

class GameGuiJFrame extends JFrame {
    private final GameGui gameGui;
    private final int fieldWidth;
    private final int fieldHeight;
    private final int spaceBetweenFields;
    private char[][] data;
    private Map<Character, TileRenderer> tileRenderers;
    private JPanel panel = new JPanel(true) {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponents(g);
            Graphics2D g2 = (Graphics2D) g;

            for (int i = 0; data != null && i < data[0].length; i++) {
                for (int j = 0; j < data.length; j++) {
                    int x = i * fieldWidth;
                    int y = j * fieldHeight;
                    int w = fieldWidth - spaceBetweenFields;
                    int h = fieldHeight - spaceBetweenFields;
                    TileRenderer tileRenderer = tileRenderers.getOrDefault(data[j][i], new SquareTileRenderer(Color.GRAY));
                    tileRenderer.render(x, y, w, h, i, j, g2);
                }
            }
        }
    };

    public GameGuiJFrame(
            GameGui gameGui,
            String title,
            int fieldWidth,
            int fieldHeight,
            int spaceBetweenFields,
            int sizeX,
            int sizeY,
            Map<Character, TileRenderer> tileRenderers
    ) {
        this.gameGui = gameGui;
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.spaceBetweenFields = spaceBetweenFields;
        this.tileRenderers = tileRenderers;

        setTitle(title);
        setSize((sizeX + 1) * fieldWidth + 10, (sizeY + 1) * fieldHeight + 30);
        setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        panel.setFocusable(true);
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) { // This runs on thread: AWT-EventQueue-0
                gameGui.keyReleases.push(e);
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void render(char[][] data) {
        this.data = data;
        this.repaint();
    }
}
