package com.github.zipcodewilmington;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.github.zipcodewilmington.casino.CasinoAccount;
import com.github.zipcodewilmington.casino.CasinoAccountManager;
import com.github.zipcodewilmington.casino.Player;
import com.github.zipcodewilmington.casino.games.Craps.Craps;
import com.github.zipcodewilmington.casino.games.Poker.Poker;
import com.github.zipcodewilmington.casino.games.TriviaGame.Trivia;
import com.github.zipcodewilmington.casino.games.numberguess.NumberGuessGame;
import com.github.zipcodewilmington.casino.games.numberguess.NumberGuessPlayer;
import com.github.zipcodewilmington.casino.ui.IOConsole;

/**
 * Created by leon on 7/21/2020. Casino - Main orchestrator of the casino
 * application
 */
public class Casino implements Runnable {

    private final IOConsole console;
    private final CasinoAccountManager accountManager;
    private CasinoAccount currentAccount;
    private boolean isRunning;
    private Scanner scanner = new Scanner(System.in);

    // Constructor
    public Casino() {
        this.console = new IOConsole(this);
        this.accountManager = new CasinoAccountManager();
        this.currentAccount = null;
        this.isRunning = false;
        initializeCasino();
    }

    private void initializeCasino() {
        System.out.println("Initializing Casino...");
        System.out.println("Account management system loaded.");
        System.out.println("Game systems initialized.");
        System.out.println("Casino ready for operation!");
    }

    @Override
    public void run() {
        startup();
        console.start();
        shutdown();
    }

    private void startup() {
        isRunning = true;
        System.out.println("Casino is now open for business!");
    }

    private void shutdown() {
        isRunning = false;
        System.out.println("Closing Casino...");
        System.out.println("All accounts saved.");
        System.out.println("Thank you for visiting the Casino!");
    }

    // Getters and Setters
    public CasinoAccountManager getAccountManager() {
        return this.accountManager;
    }

    public Double getCurrentBalance() {
        if (currentAccount != null) {
            return currentAccount.getBalance();
        } else {
            String errorMessage = "No account is currently logged in.";
            throw new RuntimeException(errorMessage);
        }
    }

    public void setCurrentAccount(CasinoAccount account) {
        if (account != null) {
            this.currentAccount = account;
            System.out.println("User session started for: " + account.getUsername());
        } else {
            if (this.currentAccount != null) {
                System.out.println("User session ended for: " + this.currentAccount.getUsername());
            }
            this.currentAccount = null;
        }
    }

    public CasinoAccount getCurrentAccount() {
        return this.currentAccount;
    }

    public IOConsole getConsole() {
        return console;
    }

    public boolean isRunning() {
        return isRunning;
    }

    // Game orchestration methods
    public void launchGame(String gameName, Player player) {
        if (!isValidGameSession(player)) {
            return;
        }

        System.out.println("Launching " + gameName + " for player: " + player.getUsername());

        switch (gameName.toLowerCase()) {
            case "roulette":
                playRouletteGame(player);
                break;
            case "poker":
                playPokerGame(player);
                break;
            case "craps":
                playCrapsGame(player);
                break;
            case "trivia":
                playTriviaGame(player);
                break;
            case "numberguess":
                playNumberGuessGame(player);
                break;
            default:
                console.println("Unknown game: " + gameName);
                break;
        }
    }

    private boolean isValidGameSession(Player player) {
        if (player == null) {
            console.println("Error: No player provided for game session.");
            return false;
        }

        if (player.getAccount() == null) {
            console.println("Error: Player has no valid account.");
            return false;
        }

        if (player.getAccount().getBalance() <= 0) {
            console.println("Insufficient funds to play. Please deposit money first.");
            return false;
        }

        return true;
    }

    public void playRouletteGame(Player player) {
        try {
            console.println("Welcome to the Roulette Table, " + player.getUsername() + "!");
            console.println("Transferring your casino balance to the Roulette table...");

            // Create the roulette wheel
            com.github.zipcodewilmington.casino.games.Roulette.Roulette roulette = new com.github.zipcodewilmington.casino.games.Roulette.Roulette();
            roulette.createWheel();

            // Create an empty bet list for the game
            java.util.List<com.github.zipcodewilmington.casino.games.Roulette.RouletteBet> currentBets = new java.util.ArrayList<>();

            // Store initial balance to calculate changes
            double initialBalance = player.getAccount().getBalance();

            // Create the RouletteGame with player's current balance
            com.github.zipcodewilmington.casino.games.Roulette.RouletteGame game = new com.github.zipcodewilmington.casino.games.Roulette.RouletteGame(
                    roulette,
                    player.getAccount().getBalance(),
                    currentBets);

            // This runs the COMPLETE RouletteGame with ALL original prompts:
            game.playGame();

            // Sync the game results back to the casino account
            double finalAmount = game.getCurrentBalance();
            double difference = finalAmount - initialBalance;

            // Update the player's casino account
            if (difference > 0) {
                player.getAccount().deposit(difference);
                console.println(
                        "\nNice job! Your casino account gained $" + String.format("%.2f", difference));
            } else if (difference < 0) {
                player.getAccount().withdraw(Math.abs(difference));
                console.println(
                        "\nTough session! Your casino account lost $" + String.format("%.2f", Math.abs(difference)));
            } else {
                console.println("\nEven session - no change to your casino account!");
            }

            // Update final balance and add to game history
            console.println("Updated Casino Balance: $" + String.format("%.2f", player.getAccount().getBalance()));
            player.getAccount().addGameEntry("Roulette Session - Net: "
                    + (difference >= 0 ? "+$" + String.format("%.2f", difference)
                            : "-$" + String.format("%.2f", Math.abs(difference))));

            console.println("Thank you for playing Roulette! Returning to main casino floor...");
            console.getStringInput("Press ENTER to continue...");

        } catch (Exception e) {
            console.println("Error in Roulette game: " + e.getMessage());
        }
    }

    public void playPokerGame(Player player) {
        try {
            console.println("Starting Poker for " + player.getUsername());
            player.getAccount().addGameEntry("Played Poker - Demo session");
            console.println("Poker session completed!");
            Poker poker = new Poker(player);
            poker.run();
        } catch (Exception e) {
            console.println("Error in Poker game: " + e.getMessage());
        }
    }
// Craps Game

    public void playCrapsGame(Player loggedInPlayer) {
        Craps craps = new Craps();

        // Add the logged-in player first
        craps.add(loggedInPlayer);

        // Show all available players
        List<CasinoAccount> allAccounts = new ArrayList<>(accountManager.loadAccounts().values());

        System.out.println("Available players to join (excluding you):");
        int idx = 1;
        Map<Integer, Player> indexToPlayer = new HashMap<>();

        for (CasinoAccount account : allAccounts) {
            if (!account.getUsername().equals(loggedInPlayer.getUsername())) {
                System.out.println(idx + ": " + account.getUsername());
                indexToPlayer.put(idx, account.getPlayer());
                idx++;
            }
        }

        System.out.println("You can add up to 4 more players to join (max 5 total).");
        System.out.println("Type player number to add, or type END to finish.");

        while (craps.getPlayers().size() < 5) {
            System.out.print("Enter player number or END to stop: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("END")) {
                break;  // Stop asking for players
            }

            try {
                int selection = Integer.parseInt(input);
                Player selectedPlayer = indexToPlayer.get(selection);

                if (selectedPlayer == null) {
                    System.out.println("Invalid player number. Try again.");
                    continue;
                }

                if (craps.getPlayers().contains(selectedPlayer)) {
                    System.out.println("Player already added. Pick someone else.");
                    continue;
                }

                // Ask for password
                System.out.print("Enter password for " + selectedPlayer.getUsername() + ": ");
                String password = scanner.nextLine();

                // Verify password using CasinoAccountManager (you need access to it)
                CasinoAccount account = accountManager.getAccount(selectedPlayer.getUsername(), password);

                if (account != null) {
                    craps.add(selectedPlayer);
                    System.out.println(selectedPlayer.getUsername() + " added successfully.");
                } else {
                    System.out.println("Incorrect password. Player not added.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid player number or END.");
            }
        }
    System.out.println (

    "Starting Craps with players:");
        for (Player p : craps.getPlayers()) {
            System.out.println("- " + p.getUsername());
        }

        // Start the game
    craps.play ();
}





public void playTriviaGame(Player firstPlayer) {
    try {
        console.println("Starting Trivia for " + firstPlayer.getUsername());
        firstPlayer.getAccount().addGameEntry("Playing Trivia - Demo session");

        Trivia triviaGame = new Trivia(console); // Pass console to Trivia
        triviaGame.launch(firstPlayer);

        console.println("Trivia session completed!");
    } catch (Exception e) {
        console.println("Error in Trivia game: " + e.getMessage());
    }
}


    public void playNumberGuessGame(Player player) {
        try {
            console.println("Starting Number Guess for " + player.getUsername());
            player.getAccount().addGameEntry("Played Number Guess - Demo session");
            console.println("Number Guess session completed!");
        } catch (Exception e) {
            console.println("Error in Number Guess game: " + e.getMessage());
        }
    }

    // Transaction processing methods
    public boolean processDeposit(Player player, double amount) {
        if (player == null || amount <= 0) {
            return false;
        }

        try {
            player.getAccount().deposit(amount);
            player.getAccount().addTransactionEntry(
                    String.format("Deposit: $%.2f via Casino System", amount));
            System.out.println("Deposit processed: " + player.getUsername() + " deposited $" + amount);
            return true;
        } catch (Exception e) {
            System.out.println("Deposit failed: " + e.getMessage());
            return false;
        }
    }

    public boolean processWithdrawal(Player player, double amount) {
        if (player == null || amount <= 0) {
            return false;
        }

        try {
            boolean success = player.getAccount().withdraw(amount);
            if (success) {
                player.getAccount().addTransactionEntry(
                        String.format("Withdrawal: $%.2f via Casino System", amount));
                System.out.println("Withdrawal processed: " + player.getUsername() + " withdrew $" + amount);
            }
            return success;
        } catch (Exception e) {
            System.out.println("Withdrawal failed: " + e.getMessage());
            return false;
        }
    }
}
