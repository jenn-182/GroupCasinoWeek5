package com.github.zipcodewilmington.casino.games.Craps;

import java.util.HashMap;
import java.util.Map;

import com.github.zipcodewilmington.casino.Player;

public class CrapsBetting {

    public enum BetType {
        PASS_LINE,
        DONT_PASS_LINE
    }

    private Map<Player, Bet> currentBets;

    public CrapsBetting() {
        this.currentBets = new HashMap<>();
    }

    public void placeBet(Player player, BetType type, double amount) {
        currentBets.put(player, new Bet(type, amount));
    }

    public void payOut(boolean passLineWins) {
        for (Map.Entry<Player, Bet> entry : currentBets.entrySet()) {
            Player player = entry.getKey();
            Bet bet = entry.getValue();

            boolean won = (passLineWins && bet.getType() == BetType.PASS_LINE)
                       || (!passLineWins && bet.getType() == BetType.DONT_PASS_LINE);

            if (won) {
                System.out.println(player.getUsername() + " wins $" + bet.getAmount() + "!");
                player.getAccount().deposit(bet.getAmount());
            } else {
                System.out.println(player.getUsername() + " loses $" + bet.getAmount() + ".");
                player.getAccount().withdraw(bet.getAmount());
            }
        }
    }

    private static class Bet {
        private final BetType type;
        private final double amount;

        public Bet(BetType type, double amount) {
            this.type = type;
            this.amount = amount;
        }

        public BetType getType() {
            return type;
        }

        public double getAmount() {
            return amount;
        }
    }
}