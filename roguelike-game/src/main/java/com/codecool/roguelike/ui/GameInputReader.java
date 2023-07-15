package com.codecool.roguelike.ui;

import java.io.IOException;

public interface GameInputReader {
    char getInputChar() throws IOException;
}
