package com.github.zipcodewilmington.casino.games.Roulette;

import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.Player;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class RouletteGame implements GameInterface {
    Roulette wheel;
    double playerCurrentMoneyAmount = 1000.0;
    List<RouletteBet> currentBets;
    List<RouletteBet> previousBets;  
    Scanner scanner = new Scanner(System.in);
    private Player currentPlayer;  

    public RouletteGame() {
        try {
            this.wheel = new Roulette();
            this.currentBets = new ArrayList<>();
            this.previousBets = new ArrayList<>();
            this.scanner = new Scanner(System.in);
            this.wheel.createWheel();
        } catch (Exception e) {
            System.out.println("ERROR in constructor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Keep your existing constructor too if needed
    public RouletteGame(Roulette wheel, double PlayerCurrentMoneyAmount, List<RouletteBet> currentBets) {
        this.wheel = wheel;
        this.playerCurrentMoneyAmount = PlayerCurrentMoneyAmount;
        this.currentBets = currentBets;
        this.previousBets = new ArrayList<>();  
        this.scanner = new Scanner(System.in);  
    }

    public void playGame() {
        System.out.println("Welcome to Roulette!");

        while (playerCurrentMoneyAmount >= 10.0) {
            System.out.println("Your Money: $" + playerCurrentMoneyAmount);
            System.out.println(" ");
            playRound();

            System.out.println("Try your luck again? (y/n)");
            String answer = scanner.next();

            if (answer.equals("n") || answer.equals("no")) {
                break;
            }
        }
    }

    private void playRound() {
        try {
            currentBets.clear();
            showBettingMenu();
            tableLimits();

            System.out.println("Place your bets! Choose!");
            
            while (true) {
                try {
                    System.out.println("Which bet will you choose? (or 'done' to spin)");
                    
                    if (scanner == null) {
                        System.out.println("ERROR: Scanner is null!");
                        return;
                    }
                    
                    if (!scanner.hasNext()) {
                        Thread.sleep(100);
                        continue;
                    }
                    
                    String betType = scanner.next();
                    
                    if (betType == null) {
                        continue;
                    }
                    
                    betType = betType.toUpperCase().trim();

                    if (betType.equals("DONE")) {
                        break;
                    }

                    System.out.println("How much do you want to bet?");
                    
                    if (!scanner.hasNextDouble()) {
                        System.out.println("Invalid amount! Please enter a number.");
                        scanner.next(); // consume invalid input
                        continue;
                    }
                    
                    double amountBet = scanner.nextDouble();

                    if (amountBet <= 0) {
                        System.out.println("Invalid amount! Please enter a positive number.");
                        continue;
                    }


                    if (amountBet > playerCurrentMoneyAmount) {
                        System.out.println("Not enough money! You have $" + String.format("%.2f", playerCurrentMoneyAmount) + 
                                           " but need $" + String.format("%.2f", amountBet));
                        continue;
                    }

                    // Create bet
                    RouletteBet bet;
                    try {
                        int number = Integer.parseInt(betType);
                        bet = new RouletteBet(number, amountBet);
                    } catch (NumberFormatException e) {
                        bet = new RouletteBet(betType, amountBet);
                    }

                    if (!bet.validateBet()) {
                        System.out.println("Invalid bet! Please check the options below:");
                        tableLimits();
                        continue;
                    }

                    currentBets.add(bet);
                    System.out.println("Bet placed: $" + amountBet + " on " + betType);

                } catch (Exception e) {
                    System.out.println("ERROR in betting loop: " + e.getMessage());
                    e.printStackTrace();
                    return;
                }
            }
            
            // Only proceed if we have bets
            if (currentBets.isEmpty()) {
                System.out.println("No bets placed. Returning to game menu.");
                return;
            }

            // Spin the wheel
            System.out.println("\n Spinning the wheel...");
            Thread.sleep(1000); // Dramatic pause
            
            RouletteNumber winner = wheel.spin();
            
            if (winner == null) {
                System.out.println("ERROR: Wheel spin failed!");
                return;
            }
            
            System.out.println("🎯 Winner: " + winner.getNumber() + " " + winner.getColor());
            System.out.println();
            
            // Calculate results
            double totalBets = 0;
            double totalPayouts = 0;
            
            System.out.println("=== BET RESULTS ===");
            
            for (RouletteBet bet : currentBets) {
                totalBets += bet.getAmount();
                
                if (bet.checkWin(winner)) {
                    double betAmount = bet.getAmount();
                    double payout = 0;
                    
                    // Calculate payout based on bet type
                    if (bet.getBetType().equals("STRAIGHT_UP")) {
                        payout = betAmount * 35;
                    } else if (bet.getBetType().equals("RED") || bet.getBetType().equals("BLACK") 
                               || bet.getBetType().equals("ODD") || bet.getBetType().equals("EVEN") 
                               || bet.getBetType().equals("HIGH") || bet.getBetType().equals("LOW")) {
                        payout = betAmount * 1;
                    } else if (bet.getBetType().equals("1ST12") || bet.getBetType().equals("2ND12") 
                               || bet.getBetType().equals("3RD12") || bet.getBetType().equals("COLUMN1") 
                               || bet.getBetType().equals("COLUMN2") || bet.getBetType().equals("COLUMN3")) {
                        payout = betAmount * 2;
                    }
                    
                    totalPayouts += betAmount + payout; // Original bet + winnings
                    
                    if (bet.getBetType().equals("STRAIGHT_UP")) {
                        System.out.println("✅ WIN! Your number " + bet.getNumberBet() + " bet pays $" + (betAmount + payout));
                    } else {
                        System.out.println("✅ WIN! Your " + bet.getBetType() + " bet pays $" + (betAmount + payout));
                    }
                } else {
                    if (bet.getBetType().equals("STRAIGHT_UP")) {
                        System.out.println("❌ LOSE: Your number " + bet.getNumberBet() + " bet lost $" + bet.getAmount());
                    } else {
                        System.out.println("❌ LOSE: Your " + bet.getBetType() + " bet lost $" + bet.getAmount());
                    }
                }
            }
            
            // Update money: subtract all bets, add back payouts for winners
            playerCurrentMoneyAmount = playerCurrentMoneyAmount - totalBets + totalPayouts;
            
            // Show round summary
            double netResult = totalPayouts - totalBets;
            System.out.println();
            System.out.println("=== ROUND RESULTS ===");
            System.out.println("Total Bets: $" + String.format("%.2f", totalBets));
            System.out.println("Total Payouts: $" + String.format("%.2f", totalPayouts));
            if (netResult > 0) {
                System.out.println("Net Result: +$" + String.format("%.2f", netResult) + " ");
            } else if (netResult < 0) {
                System.out.println("Net Result: -$" + String.format("%.2f", Math.abs(netResult)) + " ");
            } else {
                System.out.println("Net Result: $0.00 (Break even)");
            }
            System.out.println("New Balance: $" + String.format("%.2f", playerCurrentMoneyAmount));
            System.out.println("====================");
            
            // Save bets for next round's display
            try {
                // Save bets for next round's display
                if (previousBets != null && currentBets != null) {
                    previousBets.clear();
                    previousBets.addAll(currentBets);
                }
            } catch (Exception e) {
                System.out.println("Error saving previous bets: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println("ERROR in playRound: " + e.getMessage());
            e.printStackTrace();
        }
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
    
        // Add null checks for previous bets
        if (previousBets != null && !previousBets.isEmpty()) {
            System.out.println("=== PREVIOUS ROUND BETS ===");
            for (RouletteBet bet : previousBets) {
                if (bet != null) {
                    try {
                        if (bet.getBetType() != null && bet.getBetType().equals("STRAIGHT_UP")) {
                            System.out.println("$" + bet.getAmount() + " on number " + bet.getNumberBet());
                        } else if (bet.getBetType() != null) {
                            System.out.println("$" + bet.getAmount() + " on " + bet.getBetType());
                        } else {
                            System.out.println("$" + bet.getAmount() + " on unknown bet type");
                        }
                    } catch (Exception e) {
                        System.out.println("Error displaying previous bet: " + e.getMessage());
                    }
                }
            }
            System.out.println("============================");
        }
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

    @Override
    public boolean add(Player player) {
        this.currentPlayer = player;
        this.playerCurrentMoneyAmount = player.getAccount().getBalance();
        return true;
    }

    @Override
    public boolean remove(Player player) {
        this.currentPlayer = null;
        return true;
    }

    @Override
    public void play() {
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
        
        if (currentPlayer != null) {
            updatePlayerBalance();
        }
    }

    @Override
    public void run() {
        play();
    }

    @Override
    public boolean isGamblingGame() {
        return true;
    }

    @Override
    public String getGameName() {
        return "Roulette";
    }

    @Override
    public int getMinimumBet() {
        return 10;
    }

    @Override
    public int getMaximumBet() {
        return 5000;
    }

    @Override
    public void launch(Player player) {
        this.currentPlayer = player;
        this.playerCurrentMoneyAmount = player.getAccount().getBalance();
        
        System.out.println("Welcome to Roulette, " + player.getUsername() + "!");
        System.out.println("Starting balance: $" + String.format("%.2f", playerCurrentMoneyAmount));
        
        if (wheel == null) {
            this.wheel = new Roulette();
        }
        
        try {
            wheel.createWheel();
        } catch (Exception e) {
            System.out.println("ERROR creating wheel: " + e.getMessage());
            return;
        }
        
        play();
        updatePlayerBalance();
    }

    private void updatePlayerBalance() {
        if (currentPlayer != null) {
            double originalBalance = currentPlayer.getAccount().getBalance();
            double difference = playerCurrentMoneyAmount - originalBalance;
            
            if (difference > 0) {
                currentPlayer.getAccount().deposit(difference);
            } else if (difference < 0) {
                currentPlayer.getAccount().withdraw(Math.abs(difference));
            }
        }
    }
}
