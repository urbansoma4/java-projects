package com.codecool.roguelike.ui.gui;

import java.awt.*;

public interface TileRenderer {
    void render(int x, int y, int w, int h, int i, int j, Graphics2D g2);

    default void drawTileCoordinates(Graphics2D g, int x, int y, String message) {
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        g.drawString(message, x, y);
    }
}
