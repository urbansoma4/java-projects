package com.codecool.roguelike.ui.gui;

import java.awt.*;

public class SquareTileRenderer implements TileRenderer {

    private final Color color;
    boolean isDebug = false;
    private boolean shouldDisplayInfoInDebugMode = false;

    public SquareTileRenderer(Color color, boolean isDebug, boolean shouldDisplayInfoInDebugMode) {
        this(color);
        this.isDebug = isDebug;
        this.shouldDisplayInfoInDebugMode = shouldDisplayInfoInDebugMode;
    }

    public SquareTileRenderer(Color color) {
        this.color = color;
    }

    @Override
    public void render(int x, int y, int w, int h, int i, int j, Graphics2D g2) {
        g2.setColor(color);
        g2.fillRect(x, y, w, h);

        if (isDebug && shouldDisplayInfoInDebugMode) {
            drawTileCoordinates(g2, x, y + h / 2, i + "," + j);
        }
    }

}
