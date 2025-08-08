package com.github.zipcodewilmington.casino.games.Roulette;

import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.Player;
import com.github.zipcodewilmington.casino.CasinoAccount;
import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class RouletteGame implements GameInterface {
    Roulette wheel;
    double playerCurrentMoneyAmount = 1000.0;
    List<RouletteBet> currentBets;
    List<RouletteBet> previousBets;  
    Scanner scanner = new Scanner(System.in);
    private Player currentPlayer;
    private List<Player> activePlayers;
    private Map<Player, List<RouletteBet>> playerBets;
    private Map<Player, List<RouletteBet>> previousPlayerBets;
    private boolean isMultiplayer;  

    public RouletteGame() {
        try {
            this.wheel = new Roulette();
            this.currentBets = new ArrayList<>();
            this.previousBets = new ArrayList<>();
            this.scanner = new Scanner(System.in);
            this.wheel.createWheel();
            this.activePlayers = new ArrayList<>();
            this.playerBets = new HashMap<>();
            this.previousPlayerBets = new HashMap<>();
            this.isMultiplayer = false;
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
        this.activePlayers = new ArrayList<>();
        this.playerBets = new HashMap<>();
        this.previousPlayerBets = new HashMap<>();
        this.isMultiplayer = false;  
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
                    // Clean, simple prompt
                    System.out.println("Which bet will you choose? (or 'done' to spin)");
                    System.out.println("Examples: RED, 7, 1-2, 1-2-3, 1-2-4-5, TOPLINE");
                    
                    if (!scanner.hasNext()) {
                        Thread.sleep(100);
                        continue;
                    }
                    
                    String betInput = scanner.next().toUpperCase().trim();
                    
                    if (betInput.equals("DONE")) {
                        break;
                    }

                    System.out.println("How much do you want to bet?");
                    
                    if (!scanner.hasNextDouble()) {
                        System.out.println("Invalid amount! Please enter a number.");
                        scanner.next();
                        continue;
                    }
                    
                    double amountBet = scanner.nextDouble();

                    // Quick validation with helpful messages
                    if (amountBet <= 0) {
                        System.out.println("Invalid amount! Please enter a positive number.");
                        continue;
                    }

                    if (amountBet > playerCurrentMoneyAmount) {
                        System.out.println("Not enough money! You have $" + String.format("%.2f", playerCurrentMoneyAmount));
                        continue;
                    }

                    // Create bet
                    RouletteBet bet = createBetFromInput(betInput, amountBet);

                    if (bet == null || !bet.validateBet()) {
                        System.out.println("Invalid bet! Type 'help' for betting options or try again.");
                        continue;
                    }

                    currentBets.add(bet);
                    
                    //  Clean success message like your example
                    System.out.println(" Bet placed: $" + String.format("%.1f", amountBet) + " on " + formatBetDisplay(bet));
                    System.out.println(); // Clean spacing

                } catch (Exception e) {
                    System.out.println(" Invalid input! Please try again.");
                    scanner.nextLine(); // Clear the scanner
                    continue;
                }
            }
            
            // Only proceed if we have bets
            if (currentBets.isEmpty()) {
                System.out.println("No bets placed. 🤷‍♂️");
                return;
            }

            // Spin the wheel
            System.out.println("\n 🎲 Spinning the wheel...");
            Thread.sleep(1000); // Dramatic pause
            
            RouletteNumber winner = wheel.spin();
            
            if (winner == null) {
                System.out.println(" ERROR: Wheel spin failed!");
                return;
            }
            
            System.out.println("🎯 Winner: " + (winner.getNumber() == 37 ? "00" : winner.getNumber()) + " " + winner.getColor());
            System.out.println();
            
            // Calculate and show results
            double totalBets = 0;
            double totalPayouts = 0;
            
            System.out.println(" RESULTS:");
            
            for (RouletteBet bet : currentBets) {
                totalBets += bet.getAmount();
                
                if (bet.checkWin(winner)) {
                    double betAmount = bet.getAmount();
                    double payout = bet.calculatePayout();
                    totalPayouts += betAmount + payout;
                    
                    System.out.println("✅ WIN! " + formatBetDisplay(bet) + " pays $" + 
                                       String.format("%.2f", betAmount + payout));
                } else {
                    System.out.println("❌ LOSE: " + formatBetDisplay(bet) + " (-$" + 
                                       String.format("%.2f", bet.getAmount()) + ")");
                }
            }
            
            // Update balance and show summary
            playerCurrentMoneyAmount = playerCurrentMoneyAmount - totalBets + totalPayouts;
            double netResult = totalPayouts - totalBets;
            
            System.out.println();
            if (netResult > 0) {
                System.out.println(" You won $" + String.format("%.2f", netResult) + "!");
            } else if (netResult < 0) {
                System.out.println(" You lost $" + String.format("%.2f", Math.abs(netResult)));
            } else {
                System.out.println(" Break even!");
            }
            
            System.out.println(" New Balance: $" + String.format("%.2f", playerCurrentMoneyAmount));
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println();
            
            // Save bets for next round
            if (previousBets != null && currentBets != null) {
                previousBets.clear();
                previousBets.addAll(currentBets);
            }
        } catch (Exception e) {
            System.out.println("ERROR in playRound: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //  NEW: Streamlined betting info (shown once)
    private void showStreamlinedBettingInfo() {
        System.out.println(" Your Balance: $" + String.format("%.2f", playerCurrentMoneyAmount));
        System.out.println(" Minimum bet: $10 | Maximum: $5000");
        System.out.println();
        
        // Show previous bets if any (compact format)
        if (previousBets != null && !previousBets.isEmpty()) {
            System.out.println(" Last round: ");
            for (RouletteBet bet : previousBets) {
                if (bet != null) {
                    System.out.println("   $" + String.format("%.0f", bet.getAmount()) + " on " + formatBetDisplay(bet));
                }
            }
            System.out.println();
        }
    }

    //  NEW: Simplified bet creation
    private RouletteBet createBetFromInput(String input, double amount) {
        // Handle help command
        if (input.equals("HELP")) {
            showDetailedHelp();
            return null;
        }
        
        // Try inside bets first
        if (isInsideBetInput(input)) {
            RouletteBet bet = parseInsideBet(input, amount);
            if (bet != null) return bet;
            
            // Try single number
            try {
                int number = Integer.parseInt(input);
                if (number == -1) number = 37;
                return new RouletteBet(number, amount);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        
        // Handle outside bets
        return new RouletteBet(input, amount);
    }

    // NEW: Show detailed help only when requested
    private void showDetailedHelp() {
        System.out.println();
        System.out.println(" ROULETTE BETTING GUIDE:");
        System.out.println();
        System.out.println(" SINGLE NUMBERS:");
        System.out.println("   7, 23, 0, -1 (for 00) → Pays 35:1");
        System.out.println();
        System.out.println(" INSIDE BETS:");
        System.out.println("   1-2 (split) → Pays 17:1");
        System.out.println("   1-2-3 (street) → Pays 11:1");  
        System.out.println("   1-2-4-5 (corner) → Pays 8:1");
        System.out.println("   1-2-3-4-5-6 (double street) → Pays 5:1");
        System.out.println("   TOPLINE → Pays 6:1");
        System.out.println();
        System.out.println(" OUTSIDE BETS:");
        System.out.println("   RED, BLACK, ODD, EVEN, HIGH, LOW → Pays 1:1");
        System.out.println("   1ST12, 2ND12, 3RD12 → Pays 2:1");
        System.out.println("   COLUMN1, COLUMN2, COLUMN3 → Pays 2:1");
        System.out.println();
    }

    private void showBettingMenu() {
        System.out.println("=== BETTING OPTIONS ===");
        System.out.println("SINGLE NUMBER: Numbers: 0-36 (or -1 for 00) - Pays 35:1");
        System.out.println();
        System.out.println("INSIDE BETS");
        System.out.println("  SPLIT: Two adjacent numbers (1-2) - Pays 17:1");
        System.out.println("  STREET: Three numbers in row (1-2-3) - Pays 11:1");
        System.out.println("  CORNER: Four numbers (1-2-4-5) - Pays 8:1");
        System.out.println("  DOUBLE STREET: Six numbers (1-2-3-4-5-6) - Pays 5:1");
        System.out.println("  TOP LINE: 0-00-1-2-3 - Pays 6:1");
        System.out.println();
        System.out.println("OUTSIDE BETS:");
        System.out.println("  Colors: RED, BLACK - Pays 1:1");
        System.out.println("  Evens: ODD, EVEN - Pays 1:1");
        System.out.println("  Ranges: HIGH, LOW - Pays 1:1");
        System.out.println("  Dozens: 1ST12, 2ND12, 3RD12 - Pays 2:1");
        System.out.println("  Columns: COLUMN1, COLUMN2, COLUMN3 - Pays 2:1");
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
                            System.out.println("$" + bet.getAmount() + " on " + formatBetDisplay(bet));
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

    public void launchMultiplayer(List<Player> players) {
        if (players.size() < 2) {
            System.out.println("Need at least 2 Players for multiplayer!");
            return;
        }

        this.activePlayers = new ArrayList<>(players);
        this.isMultiplayer = true;

        for (Player player : activePlayers) {
            playerBets.put(player, new ArrayList<>());
            previousPlayerBets.put(player, new ArrayList<>());
        }

        System.out.println("The more, the merrier!");
        System.out.print("Players: ");
        for (int i =0; i < players.size(); i++) {
            System.out.print(players.get(i).getUsername());
            if (i < players.size() - 1) System.out.print(", ");
        }
        System.out.println();
        System.out.println();

        playMultiplayerGame();
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

    private boolean isInsideBetInput(String input) {
        // Check if input contains dashes (indicating multi-number bet)
        if (input.contains("-")) {
            return true;
        }
        // if special inside bet
        if (input.equalsIgnoreCase("TOPLINE") || input.equalsIgnoreCase("TOP-LINE")) {
            return true;
        }
        // Check if single number
        try {
            int number = Integer.parseInt(input);
            return (number >= -1 && number <= 36);
        } catch (NumberFormatException e) {
            return false;
        }

    }

    private RouletteBet parseInsideBet(String input, double amount) {
        input = input.toUpperCase().trim();

        //Hanndles TOP LINE
        if (input.equals("TOPLINE") || input.equals("TOP-LINE")) {
            int[] numbers = {0, 37, 1, 2, 3};
            return new RouletteBet("TOP_LINE", numbers, amount);
        }

        //Handles dashed number(mulitple bets)
        if (input.contains("-")) {
            String[] parts = input.split("-");
            int[] numbers = new int[parts.length];

            try{
                for (int i =0; i < parts.length; i++) {
                    if (parts[i].equals("00")) {
                        numbers[i] = 37; //00 = 37
                    } else {
                        numbers[i] = Integer.parseInt(parts[i]);
                    }
                }

                // Bet type based on number count and validate
                String betType = determineBetType(numbers);
                if (betType != null && validateNumberCombination(numbers, betType)) {
                    return new RouletteBet(betType, numbers, amount);
                }
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }    

    private String determineBetType(int[] numbers) {
        switch (numbers.length) {
            case 2:
                return "SPLIT";
            case 3:
                return "STREET";
            case 4:
                return "CORNER";
            case 6:
                return "DOUBLE_STREET";
            default: 
                return null;
        }
    }

    private boolean validateNumberCombination(int[] numbers, String betType) {
        //Apparetnly you can sort for easier validation, GTK
        java.util.Arrays.sort(numbers);

        switch(betType) {
            case "SPLIT":
                return validateSplit(numbers);
            case "STREET":
                return validateStreet(numbers);
            case "CORNER":
                return validateCorner(numbers);
            case "DOUBLE_STREET":
                return validateDoubleStreet(numbers);
            default:
                return false;
        }
    }

    private boolean validateSplit(int[] numbers) {
        if (numbers.length != 2) return false;

        int a = numbers[0], b = numbers[1];

        //Handle 0 and 00 split
        if ((a == 0 && b == 37) || (a == 37 && b == 0)) return true;

        //Horizontal splits (adjacent in same row)
        if (Math.abs(a - b) == 1 && a / 3 == b / 3) return true;

        //Vertical splits (one # above/below)
        if (Math.abs(a - b) == 3) return true;

        return false;
    }

    private boolean validateStreet(int[] numbers) {
        if (numbers.length != 3) return false;

        // Street must be three consecutive numbers in same row
        int min = numbers[0];
        return (numbers[1] == min + 1) && (numbers[2] == min + 2) && (min % 3 == 1);
    }

    private boolean validateCorner(int[] numbers) {
        if (numbers.length != 4) return false;

        //Corner must form a 2x2 square on the layout
        java.util.Arrays.sort(numbers);
        int a = numbers[0], b = numbers[1], c = numbers[2], d = numbers[3];

        //check valid corner pattern
        return (b == a + 1) && (c == a +3) && (d == a + 4) && (a % 3 != 0);
    }

    private boolean validateDoubleStreet(int[] numbers) {
        if (numbers.length != 6) return false;

        java.util.Arrays.sort(numbers);
        //check if 1st three is street
        if (!validateStreet(new int[]{numbers[0], numbers[1], numbers[2]})) return false;
        //check if last three is street
        if (!validateStreet(new int[]{numbers[3], numbers[4], numbers[5]})) return false;

        return numbers[3] == numbers[2] + 1;
    }

    private void showInsideBetExamples() {
        System.out.println("Inside bet examples:");
        System.out.println(" Split: 1-2, 4-5, 0-00");
        System.out.println(" Street: 1-2-3, 7-8-9");
        System.out.println(" Corner: 1-2-4-5, 8-9-11-12");
        System.out.println(" Double Street: 1-2-3-4-5-6");
        System.out.println(" Top line: TOPLINE");
    }

    // ENHANCED: Better bet display formatting
    private String formatBetDisplay(RouletteBet bet) {
        if (bet.getBetType().equals("STRAIGHT_UP")) {
            int num = bet.getNumberBet();
            return num == 37 ? "00" : String.valueOf(num);
        } else if (bet.getBetType().equals("SPLIT") || bet.getBetType().equals("CORNER") ||
                   bet.getBetType().equals("STREET") || bet.getBetType().equals("DOUBLE_STREET")) {
            StringBuilder sb = new StringBuilder();
            int[] numbers = bet.getNumbers();
            for (int i = 0; i < numbers.length; i++) {
                if (i > 0) sb.append("-");
                sb.append(numbers[i] == 37 ? "00" : numbers[i]);
            }
            return sb.toString() + " (" + bet.getBetType() + ")";
        } else if (bet.getBetType().equals("TOP_LINE")) {
            return "TOPLINE";
        } else {
            return bet.getBetType();
        }
    }

    private void playMultiplayerGame() {
        while (true) {
            try {
                playMultiplayerRound();

                System.out.println("One more? (y/n)");
                if (!scanner.next().toLowerCase().startsWith("y")) {
                    break;
                }
                
            } catch (Exception e) {
                System.out.println("Error in multiplayer game:" + e.getMessage());
                break;
            }
        }
        
        showFinalBalances();
    }

    private void playMultiplayerRound() {
        try{
            //Clear player bets
            for (Player player : activePlayers) {
                playerBets.get(player).clear();
            }

            System.out.println("New Round! Everyone, Place your bets!");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            //Collections
            for (Player player : activePlayers) {
                collectBetsFromPlayer(player);
            }

            //Checks if any bets were placed
            boolean anyBets = false;
            for (List<RouletteBet> bets : playerBets.values()) {
                if (!bets.isEmpty()) {
                    anyBets = true;
                    break;
                }
            }
                
            if (!anyBets) {
                System.out.println("No bets placed!");
                return;
            }

            spinForAllPlayers();
            
        } catch (Exception e) {
            System.out.println("Error in multiplayer round:" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void collectBetsFromPlayer(Player player) {
        System.out.println();
        System.out.println(player.getUsername().toUpperCase() + "'S TURN");
        System.out.println(" Balance: $" + String.format("%.2f", player.getAccount().getBalance()));
        
        // Show previous bets for this player
        List<RouletteBet> prevBets = previousPlayerBets.get(player);
        if (prevBets != null && !prevBets.isEmpty()) {
            System.out.println("Last round: ");
            for (RouletteBet bet : prevBets) {
                System.out.println("   $" + String.format("%.0f", bet.getAmount()) + " on " + formatBetDisplay(bet));
            }
        }
        
        System.out.println();
        System.out.println("Place your bets!");

        List<RouletteBet> currentPlayerBets = playerBets.get(player);
        
        while (true) {
            try {
                System.out.println("Which bet will you choose? (or 'done' to spin)");
                System.out.println("Examples: RED, 7, 1-2, 1-2-3, 1-2-4-5, TOPLINE");
                
                String betInput = scanner.next().toUpperCase().trim();
                
                if (betInput.equals("DONE")) {
                    break;
                }

                System.out.println("How much do you want to bet?");
                
                if (!scanner.hasNextDouble()) {
                    System.out.println(" Invalid amount! Please enter a number.");
                    scanner.next();
                    continue;
                }
                
                double amountBet = scanner.nextDouble();

                if (amountBet <= 0) {
                    System.out.println(" Invalid amount! Please enter a positive number.");
                    continue;
                }

                if (amountBet > player.getAccount().getBalance()) {
                    System.out.println(" Not enough money! You have $" + 
                        String.format("%.2f", player.getAccount().getBalance()));
                    continue;
                }

                RouletteBet bet = createBetFromInput(betInput, amountBet);

                if (bet == null || !bet.validateBet()) {
                    System.out.println(" Invalid bet! Type 'help' for betting options or try again.");
                    continue;
                }

                currentPlayerBets.add(bet);
                
                System.out.println(" Bet placed: $" + String.format("%.1f", amountBet) + " on " + formatBetDisplay(bet));
                System.out.println();

            } catch (Exception e) {
                System.out.println(" Invalid input! Please try again.");
                scanner.nextLine();
                continue;
            }
        }
        
        if (currentPlayerBets.isEmpty()) {
            System.out.println(player.getUsername() + " placed no bets this round.");
        } else {
            System.out.println(player.getUsername() + " placed " + currentPlayerBets.size() + " bet(s). ");
        }
    }

    private void spinForAllPlayers() {
        System.out.println();
        System.out.println(" SPINNING THE WHEEL FOR ALL PLAYERS...");
        
        try { 
            Thread.sleep(2000); 
        } catch (InterruptedException e) {}
        
        RouletteNumber winner = wheel.spin();
        
        if (winner == null) {
            System.out.println(" ERROR: Wheel spin failed!");
            return;
        }
        
        System.out.println("🎯 WINNER: " + (winner.getNumber() == 37 ? "00" : winner.getNumber()) + " " + winner.getColor());
        System.out.println();
        System.out.println(" RESULTS FOR ALL PLAYERS:");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        // Process results for each player
        for (Player player : activePlayers) {
            processPlayerResults(player, winner);
        }
    }

    private void processPlayerResults(Player player, RouletteNumber winner) {
        List<RouletteBet> bets = playerBets.get(player);
        
        if (bets.isEmpty()) {
            System.out.println("👤 " + player.getUsername() + ": No bets placed");
            return;
        }
        
        System.out.println();
        System.out.println("👤 " + player.getUsername().toUpperCase() + ":");
        
        double totalBets = 0;
        double totalPayouts = 0;
        
        for (RouletteBet bet : bets) {
            totalBets += bet.getAmount();
            
            if (bet.checkWin(winner)) {
                double betAmount = bet.getAmount();
                double payout = bet.calculatePayout();
                totalPayouts += betAmount + payout;
                
                System.out.println("   ✅ WIN! " + formatBetDisplay(bet) + " pays $" + 
                                   String.format("%.2f", betAmount + payout));
            } else {
                System.out.println("   ❌ LOSE: " + formatBetDisplay(bet) + " (-$" + 
                                   String.format("%.2f", bet.getAmount()) + ")");
            }
        }
        
        // USE EXISTING METHODS (but creates console output)
        CasinoAccount account = player.getAccount();
        
        // Withdraw total bets
        if (totalBets > 0) {
            account.withdraw(totalBets);
        }
        
        // Deposit winnings if any
        if (totalPayouts > 0) {
            account.deposit(totalPayouts);
        }
        
        double netResult = totalPayouts - totalBets;
        
        if (netResult > 0) {
            System.out.println("    Won $" + String.format("%.2f", netResult) + "!");
        } else if (netResult < 0) {
            System.out.println("    Lost $" + String.format("%.2f", Math.abs(netResult)));
        } else {
            System.out.println("    Break even!");
        }
        
        System.out.println("    New Balance: $" + String.format("%.2f", account.getBalance()));
        
        // Save bets for next round
        List<RouletteBet> prevBets = previousPlayerBets.get(player);
        prevBets.clear();
        prevBets.addAll(bets);
    }

    private void showFinalBalances() {
        System.out.println();
        System.out.println(" FINAL BALANCES:");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        // Sort players by balance (highest first)
        activePlayers.sort((p1, p2) -> 
            Double.compare(p2.getAccount().getBalance(), p1.getAccount().getBalance()));
        
        for (int i = 0; i < activePlayers.size(); i++) {
            Player player = activePlayers.get(i);
            String medal = i == 0 ? "🥇" : i == 1 ? "🥈" : i == 2 ? "🥉" : "👤";
            
            System.out.println(medal + " " + player.getUsername() + ": $" + 
                String.format("%.2f", player.getAccount().getBalance()));
        }
        
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Thanks for playing multiplayer roulette!");
    }
}
