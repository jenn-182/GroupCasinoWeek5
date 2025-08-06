package com.github.zipcodewilmington.casino.games.Roulette;

public class RouletteBet {
    private int numberBet;
    private double amountBet;
    private String betType;

    public RouletteBet(int numberBet, double amountBet) {
        this.numberBet = numberBet;
        this.amountBet = amountBet;
        this.betType = "STRAIGHT_UP";
    }

    public RouletteBet(String betType, double amountBet) {
        this.numberBet = -999;
        this.amountBet = amountBet;
        this.betType = betType;
    }

    public enum BetTypes {
        StraightUp,
        Split,
        Corner,
        Street,
        DoubleStreet,
        TopLine,
        TopLineSplit,
        TopLineTrio,
        TopLineBasket,

    }

    public boolean checkWin(RouletteNumber winningNumber) {
        int num = winningNumber.getNumber();

        if (betType.equals("STRAIGHT_UP")) {
            return num == this.numberBet;
        } else if (betType.equals("RED")) {
            return winningNumber.getColor().equals("Red");
        } else if (betType.equals("BLACK")) {
            return winningNumber.getColor().equals("Black");
        } else if (betType.equals("ODD")) {
            return (num > 0 && num % 2 == 1);
        } else if (betType.equals("EVEN")) {
            return (num > 0 && num % 2 == 0);
        } else if (betType.equals("HIGH")) {
            return (num >= 19 && num <= 36);
        } else if (betType.equals("LOW")) {
            return (num >= 1 && num <= 18);
        } else if (betType.equals("1ST12")) {
            return (num >= 1 && num <= 12);
        } else if (betType.equals("2ND12")) {
            return (num >= 13 && num <= 24);
        } else if (betType.equals("3RD12")) {
            return (num >= 25 && num <= 36);
        } else if (betType.equals("COLUMN1")) {
            return (num % 3 == 0 && num > 0);
        } else if (betType.equals("COLUMN2")) {
            return ((num - 2) % 3 == 0 && num > 0);
        } else if (betType.equals("COLUMN3")) {
            return ((num - 1)% 3 == 0 && num > 0);
        } else {
            return false;
        }

    }

    public boolean validateBet() {
        if (isOutsideBet()) { //Min Bet
            if (amountBet < 10.0) {
                return false; //Below Min
            }
            //Outside Max
            if (isEvenMoneyBet() && amountBet > 5000.0) {
                return false;
            }
            if (isTwoToOneBet() && amountBet > 2500.0) {
                return false;
            }
        } else {
            if (amountBet < 1.0) {
                return false;
            }
            if (amountBet  > 200.0) {
                return false;
            }
        }
        return true; //Bet is a-okay
    }

    private boolean isOutsideBet() {
        return !betType.equals("STRAIGHT_UP");
    }
    private boolean isEvenMoneyBet() {
        return betType.equals("RED") || betType.equals("BLACK") || 
               betType.equals("ODD") || betType.equals("EVEN") || 
               betType.equals("HIGH") || betType.equals("LOW");
    }
    private boolean isTwoToOneBet() {
        return betType.equals("1ST12") || betType.equals("2ND12") || 
               betType.equals("3RD12") || betType.equals("COLUMN1") || 
               betType.equals("COLUMN2") || betType.equals("COLUMN3");
    }

    public double calculatePayout() {
        if (betType.equals("STRAIGHT_UP")) {
            return amountBet * 35; // 35 to 1
        } else if (betType.equals("RED")) {
            return amountBet * 1; // 1 to 1 for outside bets
        } else if (betType.equals("BLACK")) {
            return amountBet * 1;
        } else if (betType.equals("ODD")) {
            return amountBet * 1;
        } else if (betType.equals("EVEN")) {
            return amountBet * 1;
        } else if (betType.equals("HIGH")) {
            return amountBet * 1;
        } else if (betType.equals("LOW")) {
            return amountBet * 1;
        } else if (betType.equals("1ST12")) {
            return amountBet * 2;
        } else if (betType.equals("2ND12")) {
            return amountBet * 2;
        } else if (betType.equals("3RD12")) {
            return amountBet * 2;
        } else if (betType.equals("COLUMN1")) {
            return amountBet * 2;
        } else if (betType.equals("COLUMN2")) {
            return amountBet * 2;
        } else if (betType.equals("COLUMN3")) {
            return amountBet * 2;
        } else {
            return 0.0;
        }
    }

}
