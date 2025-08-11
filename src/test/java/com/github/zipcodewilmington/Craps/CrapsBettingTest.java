package com.github.zipcodewilmington.Craps;
import com.github.zipcodewilmington.casino.games.Craps.CrapsBetting;
import com.github.zipcodewilmington.casino.games.Craps.CrapsBetting.BetType;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class CrapsBettingTest {

    // Minimal dummy Player class to compile
    private static class DummyAccount {
        private double balance = 100.0;

        public void deposit(double amount) {
            balance += amount;
        }

        public void withdraw(double amount) {
            balance -= amount;
        }

        public double getBalance() {
            return balance;
        }
    }

    private static class DummyPlayer {
        private final String username;
        private final DummyAccount account = new DummyAccount();

        public DummyPlayer(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public DummyAccount getAccount() {
            return account;
        }
    }

    @Test
    public void testPlaceBetAndPayOutPassLineWin() {
        CrapsBetting betting = new CrapsBetting();
        DummyPlayer player1 = new DummyPlayer("player1");
        DummyPlayer player2 = new DummyPlayer("player2");

        // Place bets
        betting.placeBet(player1, BetType.PASS_LINE, 20);
        betting.placeBet(player2, BetType.DONT_PASS_LINE, 30);

        // Pay out with pass line winning
        betting.payOut(true);

        // player1 wins 20, so balance should increase by 20
        assertEquals(120.0, player1.getAccount().getBalance(), 0.001);

        // player2 loses 30, so balance should decrease by 30
        assertEquals(70.0, player2.getAccount().getBalance(), 0.001);
    }

    @Test
    public void testPlaceBetAndPayOutDontPassLineWin() {
        CrapsBetting betting = new CrapsBetting();
        DummyPlayer player1 = new DummyPlayer("player1");
        DummyPlayer player2 = new DummyPlayer("player2");

        // Place bets
        betting.placeBet(player1, BetType.PASS_LINE, 20);
        betting.placeBet(player2, BetType.DONT_PASS_LINE, 30);

        // Pay out with don't pass line winning
        betting.payOut(false);

        // player1 loses 20, so balance should decrease by 20
        assertEquals(80.0, player1.getAccount().getBalance(), 0.001);

        // player2 wins 30, so balance should increase by 30
        assertEquals(130.0, player2.getAccount().getBalance(), 0.001);
    }
}
