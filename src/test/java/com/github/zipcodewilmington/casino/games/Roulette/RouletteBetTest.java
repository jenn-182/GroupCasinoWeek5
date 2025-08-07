package com.github.zipcodewilmington.casino.games.Roulette;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Comprehensive test suite for RouletteBet class
 * Tests all bet types, payouts, validation, and edge cases
 */
public class RouletteBetTest {
    
    private RouletteNumber red7;
    private RouletteNumber black8;
    private RouletteNumber green0;
    private RouletteNumber green00;
    private RouletteNumber high25;
    private RouletteNumber low5;
    private RouletteNumber num12; // Column 1
    private RouletteNumber num11; // Column 2
    private RouletteNumber num10; // Column 3
    private RouletteNumber num15; // 2nd dozen
    private RouletteNumber num30; // 3rd dozen
    
    @Before
    public void setUp() {
        // Create test RouletteNumbers for all scenarios
        red7 = new RouletteNumber(7, "Red");
        black8 = new RouletteNumber(8, "Black");
        green0 = new RouletteNumber(0, "Green");
        green00 = new RouletteNumber(-1, "Green");
        high25 = new RouletteNumber(25, "Red");
        low5 = new RouletteNumber(5, "Red");
        num12 = new RouletteNumber(12, "Red");   // Column 1 (12 % 3 = 0)
        num11 = new RouletteNumber(11, "Black"); // Column 2 ((11-2) % 3 = 0)
        num10 = new RouletteNumber(10, "Black"); // Column 3 ((10-1) % 3 = 0)
        num15 = new RouletteNumber(15, "Black"); // 2nd dozen (13-24)
        num30 = new RouletteNumber(30, "Red");   // 3rd dozen (25-36)
    }
    
    // === STRAIGHT UP BET TESTS ===
    @Test
    public void testStraightUpBetWin() {
        RouletteBet bet = new RouletteBet(7, 50.0);
        Assert.assertTrue("Straight up bet on 7 should win with 7", bet.checkWin(red7));
        Assert.assertEquals("Straight up payout should be 35 to 1", 1750.0, bet.calculatePayout(), 0.01);
    }
    
    @Test
    public void testStraightUpBetLose() {
        RouletteBet bet = new RouletteBet(7, 50.0);
        Assert.assertFalse("Straight up bet on 7 should lose with 8", bet.checkWin(black8));
    }
    
    // === RED/BLACK BET TESTS ===
    @Test
    public void testRedBetWin() {
        RouletteBet bet = new RouletteBet("RED", 100.0);
        Assert.assertTrue("Red bet should win on red 7", bet.checkWin(red7));
        Assert.assertEquals("Red bet payout should be 1 to 1", 100.0, bet.calculatePayout(), 0.01);
    }
    
    @Test
    public void testRedBetLose() {
        RouletteBet bet = new RouletteBet("RED", 100.0);
        Assert.assertFalse("Red bet should lose on black 8", bet.checkWin(black8));
    }
    
    @Test
    public void testBlackBetWin() {
        RouletteBet bet = new RouletteBet("BLACK", 100.0);
        Assert.assertTrue("Black bet should win on black 8", bet.checkWin(black8));
        Assert.assertEquals("Black bet payout should be 1 to 1", 100.0, bet.calculatePayout(), 0.01);
    }
    
    @Test
    public void testBlackBetLose() {
        RouletteBet bet = new RouletteBet("BLACK", 100.0);
        Assert.assertFalse("Black bet should lose on red 7", bet.checkWin(red7));
    }
    
    // === ODD/EVEN BET TESTS ===
    @Test
    public void testOddBetWin() {
        RouletteBet bet = new RouletteBet("ODD", 75.0);
        Assert.assertTrue("Odd bet should win on 7", bet.checkWin(red7));
        Assert.assertEquals("Odd bet payout should be 1 to 1", 75.0, bet.calculatePayout(), 0.01);
    }
    
    @Test
    public void testOddBetLose() {
        RouletteBet bet = new RouletteBet("ODD", 75.0);
        Assert.assertFalse("Odd bet should lose on 8", bet.checkWin(black8));
    }
    
    @Test
    public void testEvenBetWin() {
        RouletteBet bet = new RouletteBet("EVEN", 75.0);
        Assert.assertTrue("Even bet should win on 8", bet.checkWin(black8));
        Assert.assertEquals("Even bet payout should be 1 to 1", 75.0, bet.calculatePayout(), 0.01);
    }
    
    @Test
    public void testEvenBetLose() {
        RouletteBet bet = new RouletteBet("EVEN", 75.0);
        Assert.assertFalse("Even bet should lose on 7", bet.checkWin(red7));
    }
    
    // === HIGH/LOW BET TESTS ===
    @Test
    public void testHighBetWin() {
        RouletteBet bet = new RouletteBet("HIGH", 200.0);
        Assert.assertTrue("High bet should win on 25 (19-36)", bet.checkWin(high25));
        Assert.assertEquals("High bet payout should be 1 to 1", 200.0, bet.calculatePayout(), 0.01);
    }
    
    @Test
    public void testHighBetLose() {
        RouletteBet bet = new RouletteBet("HIGH", 200.0);
        Assert.assertFalse("High bet should lose on 5 (1-18)", bet.checkWin(low5));
    }
    
    @Test
    public void testLowBetWin() {
        RouletteBet bet = new RouletteBet("LOW", 200.0);
        Assert.assertTrue("Low bet should win on 5 (1-18)", bet.checkWin(low5));
        Assert.assertEquals("Low bet payout should be 1 to 1", 200.0, bet.calculatePayout(), 0.01);
    }
    
    @Test
    public void testLowBetLose() {
        RouletteBet bet = new RouletteBet("LOW", 200.0);
        Assert.assertFalse("Low bet should lose on 25 (19-36)", bet.checkWin(high25));
    }
    
    // === DOZEN BET TESTS ===
    @Test
    public void testFirstDozenWin() {
        RouletteBet bet = new RouletteBet("1ST12", 300.0);
        Assert.assertTrue("1st dozen should win on 7 (1-12)", bet.checkWin(red7));
        Assert.assertEquals("Dozen bet payout should be 2 to 1", 600.0, bet.calculatePayout(), 0.01);
    }
    
    @Test
    public void testSecondDozenWin() {
        RouletteBet bet = new RouletteBet("2ND12", 300.0);
        Assert.assertTrue("2nd dozen should win on 15 (13-24)", bet.checkWin(num15));
        Assert.assertEquals("Dozen bet payout should be 2 to 1", 600.0, bet.calculatePayout(), 0.01);
    }
    
    @Test
    public void testThirdDozenWin() {
        RouletteBet bet = new RouletteBet("3RD12", 300.0);
        Assert.assertTrue("3rd dozen should win on 30 (25-36)", bet.checkWin(num30));
        Assert.assertEquals("Dozen bet payout should be 2 to 1", 600.0, bet.calculatePayout(), 0.01);
    }
    
    // === COLUMN BET TESTS ===
    @Test
    public void testColumn1Win() {
        RouletteBet bet = new RouletteBet("COLUMN1", 250.0);
        Assert.assertTrue("Column 1 should win on 12", bet.checkWin(num12));
        Assert.assertEquals("Column bet payout should be 2 to 1", 500.0, bet.calculatePayout(), 0.01);
    }
    
    @Test
    public void testColumn2Win() {
        RouletteBet bet = new RouletteBet("COLUMN2", 250.0);
        Assert.assertTrue("Column 2 should win on 11", bet.checkWin(num11));
        Assert.assertEquals("Column bet payout should be 2 to 1", 500.0, bet.calculatePayout(), 0.01);
    }
    
    @Test
    public void testColumn3Win() {
        RouletteBet bet = new RouletteBet("COLUMN3", 250.0);
        Assert.assertTrue("Column 3 should win on 10", bet.checkWin(num10));
        Assert.assertEquals("Column bet payout should be 2 to 1", 500.0, bet.calculatePayout(), 0.01);
    }
    
    // === EDGE CASE TESTS (0 AND 00) ===
    @Test
    public void testOutsideBetsLoseOnZero() {
        RouletteBet redBet = new RouletteBet("RED", 100.0);
        RouletteBet oddBet = new RouletteBet("ODD", 100.0);
        RouletteBet highBet = new RouletteBet("HIGH", 100.0);
        RouletteBet dozenBet = new RouletteBet("1ST12", 100.0);
        
        Assert.assertFalse("Red bet should lose on 0", redBet.checkWin(green0));
        Assert.assertFalse("Odd bet should lose on 0", oddBet.checkWin(green0));
        Assert.assertFalse("High bet should lose on 0", highBet.checkWin(green0));
        Assert.assertFalse("Dozen bet should lose on 0", dozenBet.checkWin(green0));
    }
    
    @Test
    public void testOutsideBetsLoseOnDoubleZero() {
        RouletteBet blackBet = new RouletteBet("BLACK", 100.0);
        RouletteBet evenBet = new RouletteBet("EVEN", 100.0);
        RouletteBet lowBet = new RouletteBet("LOW", 100.0);
        RouletteBet columnBet = new RouletteBet("COLUMN1", 100.0);
        
        Assert.assertFalse("Black bet should lose on 00", blackBet.checkWin(green00));
        Assert.assertFalse("Even bet should lose on 00", evenBet.checkWin(green00));
        Assert.assertFalse("Low bet should lose on 00", lowBet.checkWin(green00));
        Assert.assertFalse("Column bet should lose on 00", columnBet.checkWin(green00));
    }
    
    @Test
    public void testStraightUpZeroBets() {
        RouletteBet zeroBet = new RouletteBet(0, 25.0);
        RouletteBet doubleZeroBet = new RouletteBet(-1, 25.0);
        
        Assert.assertTrue("Straight 0 should win on 0", zeroBet.checkWin(green0));
        Assert.assertTrue("Straight 00 should win on 00", doubleZeroBet.checkWin(green00));
        Assert.assertEquals("Zero bet payout should be 35 to 1", 875.0, zeroBet.calculatePayout(), 0.01);
    }
    
    // === TABLE LIMIT VALIDATION TESTS ===
    @Test
    public void testValidBets() {
        RouletteBet validRed = new RouletteBet("RED", 100.0);
        RouletteBet validStraight = new RouletteBet(7, 50.0);
        RouletteBet validDozen = new RouletteBet("1ST12", 500.0);
        
        Assert.assertTrue("Valid red bet should pass validation", validRed.validateBet());
        Assert.assertTrue("Valid straight bet should pass validation", validStraight.validateBet());
        Assert.assertTrue("Valid dozen bet should pass validation", validDozen.validateBet());
    }
    
    @Test
    public void testInvalidMinimumBets() {
        RouletteBet invalidRedMin = new RouletteBet("RED", 5.0);
        RouletteBet invalidStraightMin = new RouletteBet(7, 0.50);
        
        Assert.assertFalse("Red bet below $10 minimum should fail", invalidRedMin.validateBet());
        Assert.assertFalse("Straight bet below $1 minimum should fail", invalidStraightMin.validateBet());
    }
    
    @Test
    public void testInvalidMaximumBets() {
        RouletteBet invalidRedMax = new RouletteBet("RED", 6000.0);
        RouletteBet invalidStraightMax = new RouletteBet(7, 250.0);
        RouletteBet invalidDozenMax = new RouletteBet("1ST12", 3000.0);
        
        Assert.assertFalse("Red bet above $5000 should fail", invalidRedMax.validateBet());
        Assert.assertFalse("Straight bet above $200 should fail", invalidStraightMax.validateBet());
        Assert.assertFalse("Dozen bet above $2500 should fail", invalidDozenMax.validateBet());
    }
    
    @Test
    public void testBoundaryBets() {
        // Test exact minimum and maximum amounts
        RouletteBet minRed = new RouletteBet("RED", 10.0);
        RouletteBet maxRed = new RouletteBet("RED", 5000.0);
        RouletteBet minStraight = new RouletteBet(7, 1.0);
        RouletteBet maxStraight = new RouletteBet(7, 200.0);
        RouletteBet maxDozen = new RouletteBet("1ST12", 2500.0);
        
        Assert.assertTrue("Minimum red bet should pass", minRed.validateBet());
        Assert.assertTrue("Maximum red bet should pass", maxRed.validateBet());
        Assert.assertTrue("Minimum straight bet should pass", minStraight.validateBet());
        Assert.assertTrue("Maximum straight bet should pass", maxStraight.validateBet());
        Assert.assertTrue("Maximum dozen bet should pass", maxDozen.validateBet());
    }
}