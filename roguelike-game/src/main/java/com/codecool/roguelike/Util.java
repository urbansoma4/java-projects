package com.codecool.roguelike;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static int getRandomIntFromRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static char getInputChar() throws IOException {
        return br.readLine().charAt(0);
    }
}
