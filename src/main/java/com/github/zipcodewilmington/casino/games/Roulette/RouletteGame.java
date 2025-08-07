package com.github.zipcodewilmington.casino.games.Roulette;

import java.util.Scanner;

import java.util.List;

public class RouletteGame {
    Roulette wheel;
    double playerCurrentMoneyAmount = 1000.0;
    List<RouletteBet> currentBets;
    Scanner scanner = new Scanner(System.in);

    public RouletteGame(Roulette wheel, double PlayerCurrentMoneyAmount, List<RouletteBet> currentBets) {
        this.wheel = wheel;
        this.playerCurrentMoneyAmount = PlayerCurrentMoneyAmount;
        this.currentBets = currentBets;
    }

    public void playGame() {
        System.out.println("Welcome to Roulette!");

        while (playerCurrentMoneyAmount >= 10.0) {
            System.out.println("Your Money: $" + playerCurrentMoneyAmount);

            playRound();

            System.out.println("Try your luck again? (y/n)");
            String answer = scanner.next();

            if (answer.equals("n") || answer.equals("no")) {
                break;
            }

        }

    }

    private void playRound() {
        currentBets.clear();
        showBettingMenu();

        System.out.println("Place your bets! Choose!");

        while (true) {
            System.out.println("Which bet will you choose? (or 'done' to spin)");
            String betType = scanner.next();

            if (betType.equals("done")) {
                break;
            }

            System.out.println("How much do you want to bet?");
            double amountBet = scanner.nextDouble();

            if (amountBet <= 0) {
                System.out.println("Can't play with no money. Try a positive amount!");
                continue;
            }

            RouletteBet bet;

            try {
                int number = Integer.parseInt(betType);
                bet = new RouletteBet(number, amountBet);
            } catch (NumberFormatException e) {
                bet = new RouletteBet(betType, amountBet);
            }

            if (!bet.validateBet()) {
                System.out.println("Not a valid bet type. Check the table limits!");
                tableLimits();
                continue;
            }

            if (amountBet > playerCurrentMoneyAmount) {
                System.out
                        .println("Not enough money! You have $" + playerCurrentMoneyAmount + " but need $" + amountBet);
                continue;
            }

            currentBets.add(bet);
            System.out.println("Bet placed: $" + amountBet + " on " + betType);

        }

        System.out.println("Here comes the spin");

        RouletteNumber winner = wheel.spin();

        System.out.println("Winner: " + winner.getNumber() + " " + winner.getColor());

        double totalWinnings = 0;
        double totalLosses = 0;

        for (RouletteBet bet : currentBets) {
            if (bet.checkWin(winner)) {
                double betAmount = bet.getAmount();
                double payout = 0;
                if (bet.getBetType().equals("STRAIGHT_UP")) {
                    payout = betAmount * 35;
                } else if (bet.getBetType().equals("RED") || bet.getBetType().equals("BLACK")) {
                    payout = betAmount * 1;
                } else if (bet.getBetType().equals("STRAIGHT_UP"))
                bet.calculatePayout()
            } else {
                totalLosses += bet.getAmount();
            }
        }

        // Update player money
        playerCurrentMoneyAmount = playerCurrentMoneyAmount - totalLosses + totalWinnings;

        

    }

    private void showBettingMenu() {
        System.out.println("=== BETTING OPTIONS ===");
        System.out.println("Numbers: 0-36 (or -1 for 00) - Pays 35:1");
        System.out.println("Colors: RED, BLACK - Pays 1:1");
        System.out.println("Evens: ODD, EVEN - Pays 1:1");
        System.out.println("Ranges: HIGH, LOW - Pays 1:1");
        System.out.println("Dozens: 1ST12, 2ND12, 3RD12 - Pays 2:1");
        System.out.println("Columns: COLUMN1, COLUMN2, COLUMN3 - Pays 2:1");
        System.out.println("========================");
    }

    private void tableLimits() {
        System.out.println("===Table Minimum and Maximum bets===");
        System.out.println("Minimum bet outside $10");
        System.out.println("Aggregate Minimun bet inside $10");
        System.out.println("Minimum chip value $1");
        System.out.println("-Maximums");
        System.out.println("$5000 Even Money");
        System.out.println("$2500 2 to 1");
        System.out.println("$200 Any way inside");
        System.out.println("====================================");
    }

        public double getCurrentBalance() {
        return playerCurrentMoneyAmount;
    }
}
