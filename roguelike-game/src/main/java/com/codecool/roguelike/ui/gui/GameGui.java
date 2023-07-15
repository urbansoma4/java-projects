package com.codecool.roguelike.ui.gui;


import com.codecool.roguelike.ui.GameInputReader;
import com.codecool.roguelike.ui.GameUI;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

public class GameGui implements GameUI, GameInputReader {
    Deque<KeyEvent> keyReleases = new ConcurrentLinkedDeque<>();
    private GameGuiJFrame gameGuiJFrame;

    public GameGui(int sizeX, int sizeY, Map<Character, TileRenderer> tileRendererMap) {
        gameGuiJFrame = new GameGuiJFrame(this, "Rogue Game", 33, 33, 1, sizeX, sizeY, tileRendererMap);
    }

    public GameGui(
            String title,
            int fieldWidth,
            int fieldHeight,
            int spaceBetweenFields,
            int sizeX,
            int sizeY,
            Map<Character, TileRenderer> tileRendererMap
    ) {
        gameGuiJFrame = new GameGuiJFrame(this, title, fieldWidth, fieldHeight, spaceBetweenFields, sizeX, sizeY, tileRendererMap);
    }


    @Override
    public void displayBoard(char[][] data) {
        gameGuiJFrame.render(data);
    }

    private KeyEvent nextKeyReleased() {
        // This works, because this runs in the 'main' thread,
        // the listener that adds to this ArrayDeque runs in the 'AWT-EventQueue-0' thread
        while (keyReleases.isEmpty()) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return keyReleases.removeLast();
    }

    @Override
    public char getInputChar() throws IOException {
        return nextKeyReleased().getKeyChar();
    }

}
