package com.github.zipcodewilmington.casino.games.Craps;

import java.util.Random;

public class Dice {
    private final int sides;
    private final Random random = new Random();

    public Dice() {
        this(6);                            // default to 6-sided
    }
    public Dice(int sides) {
        this.sides = sides;
    }
    public int roll() {
        return random.nextInt(sides) + 1;
    }
}
