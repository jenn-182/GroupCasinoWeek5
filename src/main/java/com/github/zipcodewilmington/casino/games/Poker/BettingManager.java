package com.github.zipcodewilmington.casino.games.Poker;

import com.github.zipcodewilmington.casino.Player;
import java.util.List;

public class BettingManager {

    private double currentBet;

    public BettingManager() {
        currentBet = 0.0;
    }

    public boolean canAfford(Player player, double amount) {
        return player.getAccount().getBalance() >= amount;
    }

    public void processBet(Player player, double amount, GameState gameState) {
        if (canAfford(player, amount)) {
            player.getAccount().withdraw(amount);
            gameState.addToPot(amount);
        }
    }

    public double getCurrentBet() {
        return currentBet;
    }

    public void setCurrentBet(double bet) {
        this.currentBet = bet;
    }

    public void startNewBettingRound() {
        // Reset for new betting round but keep current bet
        // (in real poker, the current bet carries over between rounds)
    }

    public boolean isBettingComplete(List<String> activePlayers) {
        // Simplified check - in a full implementation, you'd track
        // whether each player has acted and either called the current bet or folded
        // For now, assume betting is complete after one round
        return true;
    }

    public double getMinimumRaise(double currentPlayerBet) {
        // Standard poker rule: minimum raise is the size of the previous raise
        // For simplicity, we'll use the current bet as minimum
        return Math.max(50.0, currentBet - currentPlayerBet + currentBet);
    }

    public void handleAllIn(Player player, GameState gameState) {
        double allInAmount = player.getAccount().getBalance();
        processBet(player, allInAmount, gameState);
    }

    public boolean isValidRaise(double raiseAmount, double currentPlayerBet, double minBet) {
        double totalBet = currentPlayerBet + raiseAmount;
        return totalBet >= currentBet + minBet;
    }

    public void resetForNewHand() {
        currentBet = 0.0;
    }
}