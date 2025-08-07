package com.github.zipcodewilmington.casino.games.Poker;

import com.github.zipcodewilmington.casino.Player;

public class BettingManager {

    // COMBINED VALIDATION METHOD
    public boolean isValidAction(Player player, String action, double amount, double currentBet, double minBet, double maxBet) {
        // One method to validate any betting action
        switch (action.toLowerCase()) {
            case "call":
                return canAfford(player, currentBet);
            case "bet":
            case "raise":
                return amount >= minBet && amount <= maxBet && canAfford(player, amount);
            case "fold":
                return true; // Folding is always valid
            default:
                return false;
        }
    }

    // COMBINED BETTING PROCESSING
    public void processAction(Player player, String action, double amount, GameState gameState) {
        // Process any betting action in one method
        if (player != null && isValidAction(player, action, amount, gameState.getCurrentBet(), 0, 1000)) {
            switch (action.toLowerCase()) {
                case "call":
                    double callAmount = gameState.getCurrentBet();
                    player.getAccount().withdraw(callAmount);
                    gameState.addToPot(callAmount);
                    break;
                case "bet":
                case "raise":
                    player.getAccount().withdraw(amount);
                    gameState.addToPot(amount);
                    gameState.setCurrentBet(amount);
                    break;
            }
        }
    }

    // ESSENTIAL HELPER METHODS
    public boolean canAfford(Player player, double amount) {
        return player.getAccount().getBalance() >= amount;
    }

    public double calculateCallAmount(double currentBet) {
        return currentBet;
    }
}