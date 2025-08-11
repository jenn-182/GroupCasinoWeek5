
package com.github.zipcodewilmington.Craps;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.github.zipcodewilmington.casino.games.Craps.Dice;

public class DiceTest {

    @Test
    public void testRollWithinRange() {
        Dice dice = new Dice();
        for (int i = 0; i < 100; i++) { // roll multiple times to check randomness
            int result = dice.roll();
            assertTrue(result >= 1 && result <= 6, 
                "Roll result should be between 1 and 6, but got: " + result);
        }
    }
}
