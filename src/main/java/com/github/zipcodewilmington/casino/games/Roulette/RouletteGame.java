package com.github.zipcodewilmington.casino.games.Roulette;

import com.github.zipcodewilmington.casino.games.Roulette.RouletteTable;

import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.Player;
import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class RouletteGame implements GameInterface {
    // ANSI color codes
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_PURPLE = "\u001B[35m";

    private Roulette wheel;
    private Scanner scanner;
    private Player currentPlayer;
    private List<Player> activePlayers;
    private Map<Player, List<RouletteBet>> playerBets;
    private Map<Player, List<RouletteBet>> previousPlayerBets;
    private boolean isMultiplayer;
    private Map<Player, Double> playerWins = new HashMap<>();
    private Map<Player, Double> playerLosses = new HashMap<>();

    public RouletteGame() {
        this.wheel = new Roulette();
        this.scanner = new Scanner(System.in);
        this.activePlayers = new ArrayList<>();
        this.playerBets = new HashMap<>();
        this.previousPlayerBets = new HashMap<>();
        this.isMultiplayer = false;
        try {
            this.wheel.createWheel();
        } catch (Exception e) {
            System.err.println("ERROR creating roulette wheel: " + e.getMessage());
        }
    }

    @Override
    public void launch(Player player) {
        this.currentPlayer = player;
        this.isMultiplayer = false;
        System.out.println("Welcome to Roulette, " + player.getUsername() + "!");
        System.out.println("Starting balance: $" + String.format("%.2f", player.getAccount().getBalance()));
        playGame();
    }

    // Multiplayer entry point
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
        for (int i = 0; i < players.size(); i++) {
            System.out.print(players.get(i).getUsername());
            if (i < players.size() - 1)
                System.out.print(", ");
        }
        System.out.println();
        System.out.println();
        playMultiplayerRound();
        showFinalBalances();
    }

    private void playGame() {
        flushScreen();
        showBettingMenu(previousPlayerBets.getOrDefault(currentPlayer, new ArrayList<>()));
        while (currentPlayer.getAccount().getBalance() >= 1.0) {
            System.out.println("Your Money: $" + String.format("%.2f", currentPlayer.getAccount().getBalance()));
            System.out.println(" ");
            playRound(currentPlayer);
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println();
            System.out.print("Try your luck again? (y/n): ");
            String answer = scanner.next();

            if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("no")) {
                break;
            }
        }

        if (!activePlayers.contains(currentPlayer)) {
            activePlayers.clear();
            activePlayers.add(currentPlayer);
        }
        showFinalBalances();
    }

    private void playRound(Player player) {
        try {
            flushScreen();
            showBettingMenu(previousPlayerBets.getOrDefault(player, new ArrayList<>()));

            List<RouletteBet> currentBets = new ArrayList<>();
            System.out.println("");
            collectBetsFromPlayer(player, currentBets);

            if (currentBets.isEmpty()) {
                System.out.println("No bets placed. Round over.");
                return;
            }

            flushScreen();
            flushScreen();
            typeWriter("\nSpinning the wheel......", 80);
            flushScreen();

            RouletteNumber winner = wheel.spin();
            if (winner == null) {
                System.err.println("ERROR: Wheel spin failed!");
                return;
            }

            // Display table with winner
            RouletteTable table = new RouletteTable(winner.getNumber());
            System.out.println(table.toString());

            processPlayerResults(player, winner, currentBets);

        } catch (Exception e) {
            System.err.println("ERROR in playRound: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void playMultiplayerRound() {
        try {
            for (Player player : activePlayers) {
                playerBets.get(player).clear();
            }

            System.out.println("New Round! Everyone, place your bets!");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            boolean anyBetsPlaced = false;
            for (Player player : activePlayers) {
                System.out.println();
                System.out.println(player.getUsername().toUpperCase() + "'S TURN");
                List<RouletteBet> currentPlayerBets = playerBets.get(player);
                collectBetsFromPlayer(player, currentPlayerBets);
                if (!currentPlayerBets.isEmpty()) {
                    anyBetsPlaced = true;
                }
            }

            if (!anyBetsPlaced) {
                System.out.println("No bets placed this round. Skipping spin.");
                return;
            }

            flushScreen();
            System.out.println("SPINNING THE WHEEL FOR ALL PLAYERS...");
            Thread.sleep(2000);

            RouletteNumber winner = wheel.spin();
            if (winner == null) {
                System.err.println("ERROR: Wheel spin failed!");
                return;
            }

            RouletteTable table = new RouletteTable(winner.getNumber());
            System.out.println(table.toString());

            System.out.println("RESULTS FOR ALL PLAYERS:");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            for (Player player : activePlayers) {
                processPlayerResults(player, winner, playerBets.get(player));
            }

        } catch (Exception e) {
            System.err.println("Error in multiplayer round: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void collectBetsFromPlayer(Player player, List<RouletteBet> betsList) {
        System.out.println(ANSI_YELLOW + " Balance:" + ANSI_RESET + " $"
                + String.format("%.2f", player.getAccount().getBalance()));

        double insideTotal = 0;
        boolean firstBetPlaced = false;

        while (true) {
            try {
                if (!firstBetPlaced) {
                    System.out.println();
                    System.out.println(
                            ANSI_YELLOW + "Input Examples:" + ANSI_RESET + " RED, 7, 1-2, 1-2-3, 1-2-4-5, TOPLINE");
                    System.out.println();
                    System.out.print(ANSI_GREEN + "Which bet will you choose?: "
                            + ANSI_RESET);
                } else {
                    System.out.println();
                    System.out.println(ANSI_YELLOW + "Current Bets:" + ANSI_RESET);
                    for (RouletteBet bet : betsList) {
                        String betDisplay = formatBetDisplay(bet);
                        // print current bets
                        System.out.println("  " + betDisplay);
                    }
                    System.out.println();
                    System.out.print(
                            ANSI_GREEN + "Would you like to place another bet or spin? (bet/spin): " + ANSI_RESET);
                    String choice = scanner.next().trim().toLowerCase();
                    if (choice.equals("spin") || choice.equals("done")) {
                        // Enforce aggregate minimum for inside bets
                        if (insideTotal > 0 && insideTotal < 10) {
                            System.out.println(ANSI_RED + "Aggregate minimum for inside bets is $10." + ANSI_RESET);
                            continue;
                        }
                        break;
                    } else if (!choice.equals("bet")) {
                        System.out.println(
                                ANSI_RED + "Please type 'bet' to place another bet or 'spin' to spin." + ANSI_RESET);
                        continue;
                    }
                    // Flush and show betting options before showing current bets
                    flushScreen();
                    showBettingMenu(previousPlayerBets.getOrDefault(player, new ArrayList<>()));
                    System.out.println();
                    System.out.println(ANSI_YELLOW + "Current Bets:" + ANSI_RESET);
                    for (RouletteBet bet : betsList) {
                        String betDisplay = formatBetDisplay(bet);
                        System.out.println("  " + betDisplay);
                    }
                    System.out.println();
                    System.out.println(
                            ANSI_YELLOW + "Input Examples:" + ANSI_RESET + " RED, 7, 1-2, 1-2-3, 1-2-4-5, TOPLINE");
                    System.out.println();
                    System.out.print(ANSI_GREEN + "Which bet will you choose? (enter 'done' to spin): "
                            + ANSI_RESET);
                }

                String betInput = scanner.next().toUpperCase().trim();

                if (betInput.equals("DONE")) {
                    if (insideTotal > 0 && insideTotal < 10) {
                        System.out.println(ANSI_RED + "Aggregate minimum for inside bets is $10." + ANSI_RESET);
                        continue;
                    }
                    break;
                }

                System.out.println();
                System.out.print(ANSI_GREEN + "How much do you want to bet? (use '0.00' format): " + ANSI_RESET);
                if (!scanner.hasNextDouble()) {
                    System.out.println(ANSI_RED + "Invalid amount! Please enter a number." + ANSI_RESET);
                    scanner.next();
                    continue;
                }

                double amountBet = scanner.nextDouble();

                if (amountBet < 1) {
                    System.out.println(ANSI_RED + "Minimum chip value is $1." + ANSI_RESET);
                    continue;
                }

                RouletteBet bet = createBetFromInput(betInput, amountBet);

                if (bet == null || !bet.validateBet()) {
                    System.out.println(
                            ANSI_RED + "Invalid bet!" + ANSI_RESET);
                    continue;
                }

                // Determine bet type for limits
                String type = bet.getBetType();
                boolean isInside = type.equals("STRAIGHT_UP") || type.equals("SPLIT") || type.equals("CORNER") ||
                        type.equals("STREET") || type.equals("DOUBLE_STREET") || type.equals("TOP_LINE");
                boolean isOutsideEven = type.equals("RED") || type.equals("BLACK") || type.equals("ODD") ||
                        type.equals("EVEN") || type.equals("1-18") || type.equals("19-36");
                boolean isOutside2to1 = type.equals("COLUMN1") || type.equals("COLUMN2") || type.equals("COLUMN3") ||
                        type.equals("1ST12") || type.equals("2ND12") || type.equals("3RD12");

                // Minimums
                if (isInside) {
                    insideTotal += amountBet;
                } else if (isOutsideEven && amountBet < 10) {
                    System.out.println(ANSI_RED + "Minimum bet for outside even money is $10." + ANSI_RESET);
                    continue;
                } else if (isOutside2to1 && amountBet < 10) {
                    System.out.println(ANSI_RED + "Minimum bet for outside 2 to 1 is $10." + ANSI_RESET);
                    continue;
                }

                // Maximums
                if (isInside && amountBet > 200) {
                    System.out.println(ANSI_RED + "Maximum inside bet is $200." + ANSI_RESET);
                    continue;
                } else if (isOutsideEven && amountBet > 5000) {
                    System.out.println(ANSI_RED + "Maximum even money bet is $5000." + ANSI_RESET);
                    continue;
                } else if (isOutside2to1 && amountBet > 2500) {
                    System.out.println(ANSI_RED + "Maximum 2 to 1 bet is $2500." + ANSI_RESET);
                    continue;
                }

                if (amountBet > player.getAccount().getBalance()) {
                    System.out.println(ANSI_RED + "Not enough money! You have $"
                            + String.format("%.2f", player.getAccount().getBalance()) + ANSI_RESET);
                    continue;
                }

                betsList.add(bet);
                player.getAccount().withdraw(amountBet); // Deduct bet amount immediately
                flushScreen();
                System.out.println();
                System.out.println(
                        ANSI_YELLOW + "Bet Placed!" + ANSI_RESET + ANSI_RED + " (-$" + String.format("%.1f", amountBet)
                                + ")" + ANSI_RESET);
                System.out.println(ANSI_YELLOW + "Current Balance:" + ANSI_RESET + " $"
                        + String.format("%.2f", player.getAccount().getBalance()));

                firstBetPlaced = true;

            } catch (Exception e) {
                System.out.println(ANSI_RED + " Invalid input! Please try again." + ANSI_RESET);
                scanner.nextLine();
            }
        }

        System.out.println(player.getUsername() + " placed " + betsList.size() + " bet(s). ");
        List<RouletteBet> currentPrevBets = previousPlayerBets.getOrDefault(player, new ArrayList<>());
        currentPrevBets.clear();
        currentPrevBets.addAll(betsList);
        previousPlayerBets.put(player, currentPrevBets);
    }

    private void processPlayerResults(Player player, RouletteNumber winner, List<RouletteBet> bets) {
        System.out.println(
                "\u001B[32mWinning Number: " + (winner.getNumber() == 37 ? "00" : winner.getNumber()) + "\u001B[0m");
        System.out.println();
        System.out.println(player.getUsername().toUpperCase() + ":");

        double totalPayouts = 0;

        for (RouletteBet bet : bets) {
            boolean isWin = bet.checkWin(winner);
            bet.setWin(isWin);
            if (isWin) {
                double payout = bet.calculatePayout();
                totalPayouts += bet.getAmount() + payout;

                System.out.println("\n" + ANSI_GREEN + "    WIN!: " + formatBetDisplay(bet) + " (+ $"
                        + String.format("%.2f", totalPayouts) + ")" + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "    LOSE!: " + formatBetDisplay(bet) + " (- $"
                        + String.format("%.2f", bet.getAmount()) + ")" + ANSI_RESET);
            }
        }

        double netResult = totalPayouts - bets.stream().mapToDouble(RouletteBet::getAmount).sum();

        if (netResult > 0) {
            playerWins.put(player, playerWins.getOrDefault(player, 0.0) + netResult);
        } else if (netResult < 0) {
            playerLosses.put(player, playerLosses.getOrDefault(player, 0.0) + Math.abs(netResult));
        }

        if (netResult > 0) {

            System.out.println(
                    "\n" + ANSI_GREEN + "    TOTAL WON: +$" + String.format("%.2f", netResult) + "!" + ANSI_RESET);
        } else if (netResult < 0) {
            System.out
                    .println("\n" + ANSI_RED + "    TOTAL LOST: -$" + String.format("%.2f", Math.abs(netResult))
                            + ANSI_RESET);
        } else {
            System.out.println("\n" + ANSI_YELLOW + "    Break even!" + ANSI_RESET);
        }

        System.out.println();

        if (totalPayouts > 0) {
            player.getAccount().deposit(totalPayouts);
        }

    }

    private void showFinalBalances() {
        flushScreen();
        System.out.println(" FINAL BALANCES:");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println();
        activePlayers.sort((p1, p2) -> Double.compare(p2.getAccount().getBalance(), p1.getAccount().getBalance()));

        for (int i = 0; i < activePlayers.size(); i++) {
            Player player = activePlayers.get(i);
            String medal = (i == 0) ? "1st Place" : (i == 1) ? "2nd Place" : (i == 2) ? "3rd Place" : "👤";
            double wins = playerWins.getOrDefault(player, 0.0);
            double losses = playerLosses.getOrDefault(player, 0.0);
            double balance = player.getAccount().getBalance();
            System.out.println(medal + ": " + player.getUsername() +
                    ANSI_GREEN + " Wins: $" + String.format("%.2f", wins) +
                    ANSI_RESET + " |" + ANSI_RED + " Losses: $" + String.format("%.2f", losses) +
                    ANSI_RESET + " | " + ANSI_YELLOW + "New Balance: $" + String.format("%.2f", balance) + ANSI_RESET);
        }
        System.out.println();
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println();
        System.out.println(ANSI_PURPLE+"Thanks for playing roulette!"+ANSI_RESET);
    }

    private void flushScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Helper for formatting
    public String formatLine(String left, String pays, int paysColumn) {
        return left.length() >= paysColumn
                ? left + " " + pays
                : String.format("%-" + paysColumn + "s%s", left, pays);
    }

    private void showBettingMenu(List<RouletteBet> previousBets) {
        flushScreen();
        int width = 74;
        int paysColumn = 55;
        String border = ANSI_PURPLE + String.format("%" + width + "s", "").replace(' ', '═') + ANSI_RESET;
        String minMaxHeader = "TABLE MINIMUM & MAXIMUM BETS";
        String bettingOptions = "BETTING OPTIONS";
        // SINGLE NUMBER
        System.out.println(border);
        System.out.println(String.format("%" + ((width + bettingOptions.length()) / 2) + "s", bettingOptions));
        System.out.println(border);

        // SINGLE NUMBER
        System.out.println(formatLine(
                ANSI_YELLOW + "  SINGLE NUMBER:" + ANSI_RESET + " " + "0-36 ('00' for double zero)",
                ANSI_YELLOW + "Pays 35:1" + ANSI_RESET, paysColumn));

        System.out.println();
        System.out.println(ANSI_PURPLE + "  INSIDE BETS" + ANSI_RESET);

        System.out.println(formatLine(
                ANSI_YELLOW + "    SPLIT:" + ANSI_RESET + " Two adjacent numbers (1-2)",
                ANSI_YELLOW + "Pays 17:1" + ANSI_RESET, paysColumn));
        System.out.println(formatLine(
                ANSI_YELLOW + "    STREET:" + ANSI_RESET + " Three numbers in a row (1-2-3)",
                ANSI_YELLOW + "Pays 11:1" + ANSI_RESET, paysColumn));
        System.out.println(formatLine(
                ANSI_YELLOW + "    CORNER:" + ANSI_RESET + " Four numbers (1-2-4-5)",
                ANSI_YELLOW + "Pays 8:1" + ANSI_RESET, paysColumn));
        System.out.println(formatLine(
                ANSI_YELLOW + "    DOUBLE STREET:" + ANSI_RESET + " Six numbers (1-2-3-4-5-6)",
                ANSI_YELLOW + "Pays 5:1" + ANSI_RESET, paysColumn));
        System.out.println(formatLine(
                ANSI_YELLOW + "    TOP LINE:" + ANSI_RESET + " 0-00-1-2-3",
                ANSI_YELLOW + "Pays 6:1" + ANSI_RESET, paysColumn));

        System.out.println();
        System.out.println(ANSI_PURPLE + "  OUTSIDE BETS: " + ANSI_RESET);

        System.out.println(formatLine(
                ANSI_YELLOW + "    COLORS:" + ANSI_RESET + " RED, BLACK",
                ANSI_YELLOW + "Pays 1:1" + ANSI_RESET, paysColumn));
        System.out.println(formatLine(
                ANSI_YELLOW + "    EVENS:" + ANSI_RESET + " ODD, EVEN",
                ANSI_YELLOW + "Pays 1:1" + ANSI_RESET, paysColumn));
        System.out.println(formatLine(
                ANSI_YELLOW + "    RANGES:" + ANSI_RESET + " 1-18, 19-36",
                ANSI_YELLOW + "Pays 1:1" + ANSI_RESET, paysColumn));
        System.out.println(formatLine(
                ANSI_YELLOW + "    DOZENS:" + ANSI_RESET + " 1ST12, 2ND12, 3RD12",
                ANSI_YELLOW + "Pays 2:1" + ANSI_RESET, paysColumn));
        System.out.println(formatLine(
                ANSI_YELLOW + "    COLUMNS:" + ANSI_RESET + " COLUMN1, COLUMN2, COLUMN3",
                ANSI_YELLOW + "Pays 2:1" + ANSI_RESET, paysColumn));
        System.out.println();
        System.out.println(border);

        // Table Minimum and Maximum Bets Header
        System.out.println(String.format("%" + ((width + minMaxHeader.length()) / 2) + "s", minMaxHeader));
        System.out.println(border);
        System.out.println(ANSI_YELLOW + "  MINIMUMS:" + ANSI_RESET);
        System.out.println("    Minimum bet outside $10");
        System.out.println("    Aggregate Minimum bet inside $10");
        System.out.println("    Minimum chip value $1");
        System.out.println(ANSI_YELLOW + "  MAXIMUMS:" + ANSI_RESET);
        System.out.println("    $5000 Even Money");
        System.out.println("    $2500 2 to 1");
        System.out.println("    $200 Any way inside");
        System.out.println(border);
    }

    private RouletteBet createBetFromInput(String input, double amount) {
        // If it contains a number
        if (input.matches("\\d{1,2}|00")) {
            int number = input.equals("00") ? 37 : Integer.parseInt(input);
            return new RouletteBet(number, amount);
        }

        if (input.contains("-")) {
            String[] parts = input.split("-");
            int[] numbers = new int[parts.length];
            try {
                for (int i = 0; i < parts.length; i++) {
                    numbers[i] = parts[i].equals("00") ? 37 : Integer.parseInt(parts[i]);
                }
                String betType = determineBetType(numbers);
                if (betType != null) {
                    return new RouletteBet(betType, numbers, amount);
                }
            } catch (NumberFormatException e) {
                return null;
            }
        }

        // Handle Outside bets
        return new RouletteBet(input, amount);
    }

    private String determineBetType(int[] numbers) {
        java.util.Arrays.sort(numbers);

        switch (numbers.length) {
            case 2:
                if (validateSplit(numbers))
                    return "SPLIT";
                break;
            case 3:
                if (validateStreet(numbers))
                    return "STREET";
                break;
            case 4:
                if (validateCorner(numbers))
                    return "CORNER";
                break;
            case 6:
                if (validateDoubleStreet(numbers))
                    return "DOUBLE_STREET";
                break;
            default:
                return null;
        }
        return null;
    }

    private boolean validateSplit(int[] numbers) {
        if (numbers.length != 2)
            return false;
        int a = numbers[0], b = numbers[1];
        if ((a == 0 && b == 37) || (a == 37 && b == 0))
            return true;
        if (Math.abs(a - b) == 1 && a / 3 == b / 3)
            return true;
        if (Math.abs(a - b) == 3)
            return true;
        return false;
    }

    private boolean validateStreet(int[] numbers) {
        if (numbers.length != 3)
            return false;
        int min = numbers[0];
        return (numbers[1] == min + 1) && (numbers[2] == min + 2) && (min % 3 == 1);
    }

    private boolean validateCorner(int[] numbers) {
        if (numbers.length != 4)
            return false;
        java.util.Arrays.sort(numbers);
        int a = numbers[0], b = numbers[1], c = numbers[2], d = numbers[3];
        return (b == a + 1) && (c == a + 3) && (d == a + 4) && (a % 3 != 0);
    }

    private boolean validateDoubleStreet(int[] numbers) {
        if (numbers.length != 6)
            return false;
        java.util.Arrays.sort(numbers);
        if (!validateStreet(new int[] { numbers[0], numbers[1], numbers[2] }))
            return false;
        if (!validateStreet(new int[] { numbers[3], numbers[4], numbers[5] }))
            return false;
        return numbers[3] == numbers[2] + 1;
    }

    private String formatBetDisplay(RouletteBet bet) {
        if (bet.getBetType().equals("STRAIGHT_UP")) {
            int num = bet.getNumberBet();
            return num == 37 ? "00" : String.valueOf(num);
        } else if (bet.getBetType().equals("SPLIT") || bet.getBetType().equals("CORNER") ||
                bet.getBetType().equals("STREET") || bet.getBetType().equals("DOUBLE_STREET")) {
            StringBuilder sb = new StringBuilder();
            int[] numbers = bet.getNumbers();
            for (int i = 0; i < numbers.length; i++) {
                if (i > 0)
                    sb.append("-");
                sb.append(numbers[i] == 37 ? "00" : numbers[i]);
            }
            return sb.toString();
        } else if (bet.getBetType().equals("TOP_LINE")) {
            return "TOPLINE";
        } else {
            return bet.getBetType();
        }
    }

    // GameInterface required methods
    @Override
    public boolean add(Player player) {
        return true;
    }

    @Override
    public boolean remove(Player player) {
        return true;
    }

    @Override
    public void play() {
        playGame();
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
        return 1;
    }

    @Override
    public int getMaximumBet() {
        return 5000;
    }

    private void typeWriter(String text, int delayMillis) throws InterruptedException {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            System.out.flush();
            Thread.sleep(delayMillis);
        }
        System.out.println();
    }
}