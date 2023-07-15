package com.codecool.roguelike.ui.gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageTileRenderer implements TileRenderer {
    private final BufferedImage image;
    private boolean isDebug = false;
    private Color backGroundColor;

    public ImageTileRenderer(String imagePath) {
        this.image = loadImage(imagePath);
        backGroundColor = Color.LIGHT_GRAY;
    }

    public ImageTileRenderer(String imagePath, Color backGroundColor) {
        this.image = loadImage(imagePath);
        this.backGroundColor = backGroundColor;
    }

    public ImageTileRenderer(String imagePath, boolean isDebug) {
        this.image = loadImage(imagePath);
        this.isDebug = isDebug;
        backGroundColor = Color.LIGHT_GRAY;
    }

    @Override
    public void render(int x, int y, int w, int h, int i, int j, Graphics2D g2) {
        g2.setColor(backGroundColor);
        g2.fillRect(x, y, w, h);
        g2.drawImage(image, x, y, null);

        if (isDebug) {
            drawTileCoordinates(g2, x, y + h / 2, i + "," + j);
        }
    }


    private BufferedImage loadImage(String name) {
        URL resource = getClass().getResource(name);
        try {
            assert resource != null;
            return ImageIO.read(resource);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Could not load image: " + name);
            e.printStackTrace();
        }
        return null;
    }
}

