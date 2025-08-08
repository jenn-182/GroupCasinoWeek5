package com.github.zipcodewilmington.casino.games.Roulette;

public class RouletteBet {
    private int numberBet;
    private double amountBet;
    private String betType;
    private int[] numbers; 

    // Existing constructors...
    public RouletteBet(int numberBet, double amountBet) {
        this.numberBet = numberBet;
        this.amountBet = amountBet;
        this.betType = "STRAIGHT_UP";
        this.numbers = new int[] { numberBet }; 
    }

    public RouletteBet(String betType, double amountBet) {
        this.numberBet = -999;
        this.amountBet = amountBet;
        this.betType = betType;
    }

    
    public RouletteBet(String betType, int[] numbers, double amountBet) {
        this.betType = betType;
        this.numbers = numbers;
        this.amountBet = amountBet;
        this.numberBet = numbers[0]; 
    }

    public int[] getNumbers() {
        return numbers;
    }

    public String getBetType() {
        return betType;
    }

    public double getAmount() {
        return amountBet;
    }

    public int getNumberBet() {
        return numberBet;
    }

    
    public boolean checkWin(RouletteNumber winningNumber) {
        int num = winningNumber.getNumber();

        if (betType.equals("STRAIGHT_UP")) {
            return num == this.numberBet;
        } else if (betType.equals("SPLIT")) {
            return containsNumber(num);
        } else if (betType.equals("CORNER")) {
            return containsNumber(num);
        } else if (betType.equals("STREET")) {
            return containsNumber(num);
        } else if (betType.equals("DOUBLE_STREET")) {
            return containsNumber(num);
        } else if (betType.equals("TOP_LINE")) {
            return containsNumber(num);
        } else if (betType.equals("RED")) {
            return winningNumber.getColor().equals("Red");
        } else if (betType.equals("BLACK")) {
            return winningNumber.getColor().equals("Black");
        } else if (betType.equals("ODD")) {
            return num != 0 && num != 37 && num % 2 == 1;
        } else if (betType.equals("EVEN")) {
            return num != 0 && num != 37 && num % 2 == 0;
        } else if (betType.equals("HIGH")) {
            return num >= 19 && num <= 36;
        } else if (betType.equals("LOW")) {
            return num >= 1 && num <= 18;
        } else if (betType.equals("1ST12")) {
            return num >= 1 && num <= 12;
        } else if (betType.equals("2ND12")) {
            return num >= 13 && num <= 24;
        } else if (betType.equals("3RD12")) {
            return num >= 25 && num <= 36;
        } else if (betType.equals("COLUMN1")) {
            return num % 3 == 1 && num >= 1 && num <= 34; // 1,4,7,10...34
        } else if (betType.equals("COLUMN2")) {
            return num % 3 == 2 && num >= 2 && num <= 35; // 2,5,8,11...35
        } else if (betType.equals("COLUMN3")) {
            return num % 3 == 0 && num >= 3 && num <= 36; // 3,6,9,12...36
        }

        return false;
    }

    
    private boolean containsNumber(int num) {
        if (numbers == null)
            return false;
        for (int n : numbers) {
            if (n == num)
                return true;
        }
        return false;
    }

    
    public double calculatePayout() {
        if (betType.equals("STRAIGHT_UP")) {
            return amountBet * 35; // 35:1
        } else if (betType.equals("SPLIT")) {
            return amountBet * 17; // 17:1
        } else if (betType.equals("CORNER")) {
            return amountBet * 8; // 8:1
        } else if (betType.equals("STREET")) {
            return amountBet * 11; // 11:1
        } else if (betType.equals("DOUBLE_STREET")) {
            return amountBet * 5; // 5:1
        } else if (betType.equals("TOP_LINE")) {
            return amountBet * 6; // 6:1
        }
        // ... existing outside bet payouts ...
        else if (betType.equals("RED") || betType.equals("BLACK") ||
                betType.equals("ODD") || betType.equals("EVEN") ||
                betType.equals("HIGH") || betType.equals("LOW")) {
            return amountBet * 1; // 1:1
        } else if (betType.equals("1ST12") || betType.equals("2ND12") || betType.equals("3RD12") ||
                betType.equals("COLUMN1") || betType.equals("COLUMN2") || betType.equals("COLUMN3")) {
            return amountBet * 2; // 2:1
        }
        return 0.0;
    }

    
    public boolean validateBet() {
        // Universal $10 minimum for all bets
        if (amountBet < 10.0) {
            return false;
        }

        if (isInsideBet()) {
            // All inside bets have $200 maximum
            if (amountBet > 200.0) {
                return false;
            }
        } else {
            // Outside bet maximums
            if (isEvenMoneyBet() && amountBet > 5000.0) {
                return false;
            }
            if (isTwoToOneBet() && amountBet > 2500.0) {
                return false;
            }
        }
        return true;
    }

    
    private boolean isInsideBet() {
        return betType.equals("STRAIGHT_UP") || betType.equals("SPLIT") ||
                betType.equals("CORNER") || betType.equals("STREET") ||
                betType.equals("DOUBLE_STREET") || betType.equals("TOP_LINE");
    }

    private boolean isOutsideBet() {
        return !isInsideBet();
    }

    // Add this method to your RouletteBet class

    public boolean isTwoToOneBet() {
        // Dozen bets (pay 2:1)
        if (betType.equals("1ST12") || betType.equals("2ND12") || betType.equals("3RD12")) {
            return true;
        }

        // Column bets (pay 2:1)
        if (betType.equals("COLUMN1") || betType.equals("COLUMN2") || betType.equals("COLUMN3")) {
            return true;
        }

        return false;
    }

    public boolean isEvenMoneyBet() {
        // Even money bets (pay 1:1)
        return betType.equals("RED") || betType.equals("BLACK") ||
                betType.equals("ODD") || betType.equals("EVEN") ||
                betType.equals("HIGH") || betType.equals("LOW");
    }
}
