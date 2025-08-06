package com.github.zipcodewilmington.casino.games.Poker;

public class GameState {

    private double pot;
    private double currentBet;
    private boolean gameActive;
    private int round;

    public GameState() {
        resetGame();
    }

    // COMBINED RESET METHOD
    public void resetGame() {
        pot = 0.0;
        currentBet = 0.0;
        gameActive = true;
        round = 0;
    }

    // COMBINED POT/BET MANAGEMENT
    public void addToPot(double amount) {
        pot += amount;
    }

    public void setCurrentBet(double bet) {
        currentBet = bet;
    }

    public void nextRound() {
        round++;
        currentBet = 0.0; // Reset bet for new round
    }

    // ESSENTIAL GETTERS
    public double getPot() { return pot; }
    public double getCurrentBet() { return currentBet; }
    public boolean isGameActive() { return gameActive; }
    public int getRound() { return round; }

    // ESSENTIAL SETTERS
    public void setGameActive(boolean active) { 
        gameActive = active; 
    }
}