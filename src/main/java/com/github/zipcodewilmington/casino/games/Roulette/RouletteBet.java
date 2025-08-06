package com.github.zipcodewilmington.casino.games.Roulette;

public class RouletteBet {
    private int numberBet;
    private double amountBet;
    

    public RouletteBet(int numberBet, double amountBet) {
        this.numberBet = numberBet;
        this.amountBet = amountBet;
    }

    public boolean checkWin(RouletteNumber winningNumber) {
        return winningNumber.getNumber() == this.numberBet;

    }

    public double calculatePayout() {
        return amountBet * 35;

    }
    
}
