package com.github.zipcodewilmington.casino.games.Roulette;

import java.util.Random;

public class Roulette {
    private RouletteNumber[] wheel = new RouletteNumber[38];

    public void createWheel() {
        wheel[0] = new RouletteNumber(0, "Green");
        wheel[1] = new RouletteNumber(1, "Red");
        wheel[2] = new RouletteNumber(2, "Black");
        wheel[3] = new RouletteNumber(3, "Red");
        wheel[4] = new RouletteNumber(4, "Black");
        wheel[5] = new RouletteNumber(5, "Red");
        wheel[6] = new RouletteNumber(6, "Black");
        wheel[7] = new RouletteNumber(7, "Red");
        wheel[8] = new RouletteNumber(8, "Black");
        wheel[9] = new RouletteNumber(9, "Red");
        wheel[10] = new RouletteNumber(10, "Black");
        wheel[11] = new RouletteNumber(11, "Black");
        wheel[12] = new RouletteNumber(12, "Red");
        wheel[13] = new RouletteNumber(13, "Red");
        wheel[14] = new RouletteNumber(14, "Black");
        wheel[15] = new RouletteNumber(15, "Red");
        wheel[16] = new RouletteNumber(16, "Black");
        wheel[17] = new RouletteNumber(17, "Red");
        wheel[18] = new RouletteNumber(18, "Black");
        wheel[19] = new RouletteNumber(19, "Red");
        wheel[20] = new RouletteNumber(20, "Black");
        wheel[21] = new RouletteNumber(21, "Red");
        wheel[22] = new RouletteNumber(22, "Black");
        wheel[23] = new RouletteNumber(23, "Red");
        wheel[24] = new RouletteNumber(24, "Black");
        wheel[25] = new RouletteNumber(25, "Red");
        wheel[26] = new RouletteNumber(26, "Black");
        wheel[27] = new RouletteNumber(27, "Red");
        wheel[28] = new RouletteNumber(28, "Black");
        wheel[29] = new RouletteNumber(29, "Black");
        wheel[30] = new RouletteNumber(30, "Red");
        wheel[31] = new RouletteNumber(31, "Red");
        wheel[32] = new RouletteNumber(32, "Black");
        wheel[33] = new RouletteNumber(33, "Red");
        wheel[34] = new RouletteNumber(34, "Black");
        wheel[35] = new RouletteNumber(35, "Red");
        wheel[36] = new RouletteNumber(36, "Red");
        wheel[37] = new RouletteNumber(-1, "Green");

    }

    public RouletteNumber spin() {
        Random random = new Random();
        int randomPosition = random.nextInt(38);
        return wheel[randomPosition];
    }

    

}
