package com.codecool.roguelike.ui.console;

import com.codecool.roguelike.Util;
import com.codecool.roguelike.ui.GameInputReader;

import java.io.IOException;

public class ConsoleGameInputReader implements GameInputReader {
    @Override
    public char getInputChar() throws IOException {
        return Util.getInputChar();
    }
}
