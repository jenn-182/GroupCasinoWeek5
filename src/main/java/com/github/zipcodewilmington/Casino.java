package com.github.zipcodewilmington;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import com.github.zipcodewilmington.casino.games.Roulette.RouletteGame;
import com.github.zipcodewilmington.casino.ui.IOConsole;
import com.github.zipcodewilmington.casino.ui.UIRender;
import com.github.zipcodewilmington.casino.games.Numberguess.NumberGuessGame;

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

    // ---------LAUNCH GAME----------

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

    // -------------ROULETTE -------------------

    public void playRouletteGame(Player player) {
        try {
            UIRender uiRender = new UIRender();
            uiRender.displayRouletteWelcomeHeader();

            int choice = console.getIntegerInput("Enter your choice (1-3): ");

            switch (choice) {
                case 1:
                    playSinglePlayerRoulette(player);
                    break;
                case 2:
                    playMultiplayerRoulette(player);
                    break;
                case 3:
                    viewRouletteRules();
                    break;
                default:
                    console.println("Invalid choice! Please select 1, 2, or 3.");
                    break;
            }

        } catch (Exception e) {
            console.println("Error in Roulette game: " + e.getMessage());
        }
    }

    private void playSinglePlayerRoulette(Player player) {
        try {
            console.println("Transferring your casino balance to the Roulette table...");

            double initialBalance = player.getAccount().getBalance();

            // Use the default RouletteGame constructor
            RouletteGame game = new RouletteGame();

            // Run the game
            game.launch(player);

            // Sync the game results back to the casino account
            double finalAmount = player.getAccount().getBalance();
            double difference = finalAmount - initialBalance;

            if (difference > 0) {
                console.println("\nNice job! Your casino account gained $" + String.format("%.2f", difference));
            } else if (difference < 0) {
                console.println(
                        "\nTough session! Your casino account lost $" + String.format("%.2f", Math.abs(difference)));
            } else {
                console.println("\nEven session - no change to your casino account!");
            }

            console.println("Updated Casino Balance: $" + String.format("%.2f", player.getAccount().getBalance()));
            player.getAccount().addGameEntry("Roulette Session - Net: "
                    + (difference >= 0 ? "+$" + String.format("%.2f", difference)
                            : "-$" + String.format("%.2f", Math.abs(difference))));

            console.println("Thank you for playing Roulette! Returning to main casino floor...");
            console.getStringInput("Press ENTER to continue...");

        } catch (Exception e) {
            console.println("Error in single player Roulette: " + e.getMessage());
        }
    }

    private void playMultiplayerRoulette(Player hostPlayer) {
        try {
            // Get players using the new helper method
            List<Player> players = getMultiplayerPlayers(hostPlayer, 6);
            if (players.size() >= 2) {
                console.println("\nStarting Multiplayer Roulette with " + players.size() + " players!");
                RouletteGame game = new RouletteGame();
                game.launchMultiplayer(players);

                console.println("\nMultiplayer Roulette session completed!");
                console.println("All player balances have been updated.");

                for (Player player : players) {
                    player.getAccount().addGameEntry("Multiplayer Roulette Session");
                }
            } else {
                console.println("Not enough players for multiplayer! Need at least 2.");
            }

            console.getStringInput("Press ENTER to continue...");

        } catch (Exception e) {
            console.println("Error in multiplayer Roulette: " + e.getMessage());
        }
    }

    private void viewRouletteRules() {

        try {
            String rules = new String(Files.readAllBytes(Paths.get(
                    "/Users/jenn/Projects/GroupCasinoWeek5/src/main/java/com/github/zipcodewilmington/casino/games/Roulette/Rouletterules.md")));
            flushScreen();
            System.out.println(rules);
        } catch (Exception e) {
            System.out.println("Could not load Roulette rules. Please check that Rouletterules.md exists.");
        }

        System.out.println();
        System.out.print("Press ENTER to return to the menu...");
        scanner.nextLine();
    }

    // ---------- POKER -----------------

    public void playPokerGame(Player player) {
        try {
            UIRender uiRender = new UIRender();
            uiRender.displayPokerWelcomeHeader();

            int choice = console.getIntegerInput("Enter your choice (1-3): ");

            switch (choice) {
                case 1:
                    playSinglePlayerPoker(player);
                    break;
                case 2:
                    playMultiplayerPoker(player);
                    break;
                case 3:
                    viewPokerRules();
                    break;
                default:
                    console.println("Invalid choice! Please select 1, 2, or 3.");
                    break;
            }
        } catch (Exception e) {
            console.println("Error in Poker game menu: " + e.getMessage());
        }
    }

    private void playSinglePlayerPoker(Player player) {
        console.println("Starting single player Poker for " + player.getUsername());
        player.getAccount().addGameEntry("Played Poker - Demo session");
        Poker poker = new Poker(player);
        flushScreen();
        poker.run();
        console.println("Poker session completed!");
        console.getStringInput("Press ENTER to continue...");
    }

    private void playMultiplayerPoker(Player hostPlayer) {
        List<Player> players = getMultiplayerPlayers(hostPlayer, 6);
        if (players.size() >= 2) {
            console.println("\nStarting Multiplayer Poker with " + players.size() + " players!");
            Poker game = new Poker(hostPlayer);
            game.launchMultiplayer(players);
            console.println("\nMultiplayer Poker session completed!");
            for (Player player : players) {
                player.getAccount().addGameEntry("Multiplayer Poker Session");
            }
        } else {
            console.println("Not enough players for multiplayer! Need at least 2.");
        }
        console.getStringInput("Press ENTER to continue...");
    }

    private void viewPokerRules() {
        try {
            String rules = new String(Files.readAllBytes(Paths.get(
                    "/Users/jenn/Projects/GroupCasinoWeek5/src/main/java/com/github/zipcodewilmington/casino/games/Poker/Pokerrules.md")));
            flushScreen();
            System.out.println(rules);
        } catch (Exception e) {
            System.out.println("Could not load Poker rules. Please check that Pokerrules.md exists.");
        }

        System.out.println();
        System.out.print("Press ENTER to return to the menu...");
        scanner.nextLine();
    }

    // -----------------CRAPS---------------------

    public void playCrapsGame(Player player) {
        try {
            UIRender uiRender = new UIRender();
            uiRender.displayCrapsWelcomeHeader();

            int choice = console.getIntegerInput("Enter your choice (1-3): ");

            switch (choice) {
                case 1:
                    playSinglePlayerCraps(player);
                    break;
                case 2:
                    playMultiplayerCraps(player);
                    break;
                case 3:
                    viewCrapsRules();
                    break;
                default:
                    console.println("Invalid choice! Please select 1, 2, or 3.");
                    break;
            }
        } catch (Exception e) {
            console.println("Error in Craps game menu: " + e.getMessage());
        }
    }

    private void playSinglePlayerCraps(Player player) {
        console.println("Starting a single player game of Craps...");
        Craps craps = new Craps();
        craps.add(player);
        boolean keepPlaying = true;
        while (keepPlaying) {
            craps.play();
            console.print("\nDo you want to play another round? (yes/no): ");
            String playAgain = scanner.nextLine().trim().toLowerCase();
            if (!playAgain.equals("yes")) {
                keepPlaying = false;
                console.println("\nThanks for playing single player Craps!");
                player.getAccount().addGameEntry("Craps session completed");
            }
        }
    }

    private void playMultiplayerCraps(Player hostPlayer) {
        List<Player> players = getMultiplayerPlayers(hostPlayer, 5);
        if (players.size() >= 2) {
            console.println("\nStarting Multiplayer Craps with " + players.size() + " players!");
            Craps craps = new Craps();
            for (Player p : players) {
                craps.add(p);
            }
            craps.launchMultiplayer(players);

            boolean keepPlaying = true;
            while (keepPlaying) {
                craps.play();
                console.print("Do you want to play another round? (yes/no): ");
                String playAgain = scanner.nextLine().trim().toLowerCase();
                if (!playAgain.equals("yes")) {
                    keepPlaying = false;
                    console.println("Thanks for playing Craps!");
                    for (Player p : craps.getPlayers()) {
                        p.getAccount().addGameEntry("Craps session completed");
                    }
                }
            }
        } else {
            console.println("Not enough players for multiplayer! Need at least 2.");
        }
        console.getStringInput("Press ENTER to continue...");
    }

    private void viewCrapsRules() {
        try {
            String rules = new String(Files.readAllBytes(Paths.get(
                    "/Users/jenn/Projects/GroupCasinoWeek5/src/main/java/com/github/zipcodewilmington/casino/games/Craps/Crapsrules.md")));
            flushScreen();
            console.println(rules);
        } catch (IOException e) {
            console.println("Could not load Craps rules. Please check that Crapsrules.md exists.");
        }
        console.println("\nPress ENTER to return to the menu...");
        scanner.nextLine();
    }

    // ----------------TRIVIA----------------------

    public void playTriviaGame(Player player) {
        try {
            UIRender uiRender = new UIRender();
            uiRender.displayTriviaWelcomeHeader();

            int choice = console.getIntegerInput("Enter your choice (1-3): ");

            switch (choice) {
                case 1:
                    playSinglePlayerTrivia(player);
                    break;
                case 2:
                    playMultiplayerTrivia(player);
                    break;
                case 3:
                    viewTriviaRules();
                    break;
                default:
                    console.println("Invalid choice! Please select 1, 2, or 3.");
                    break;
            }
        } catch (Exception e) {
            console.println("Error in Trivia game menu: " + e.getMessage());
        }
    }

    private void playSinglePlayerTrivia(Player loggedInPlayer) {
        console.println("Starting Trivia for " + loggedInPlayer.getUsername());
        loggedInPlayer.getAccount().addGameEntry("Playing Trivia - Session started");
        Trivia triviaGame = new Trivia(console);
        triviaGame.add(loggedInPlayer);
        flushScreen();
        triviaGame.play(); // Run Trivia game
        loggedInPlayer.getAccount().addGameEntry("Trivia session completed");
        console.println("Trivia session completed!");
        console.getStringInput("Press ENTER to continue...");
    }

    private void playMultiplayerTrivia(Player hostPlayer) {
        List<Player> players = getMultiplayerPlayers(hostPlayer, 2);
        if (players.size() >= 2) {
            console.println("\nStarting Multiplayer Trivia with " + players.size() + " players!");
            flushScreen();
            console.println("Host: " + hostPlayer.getUsername());
            Trivia triviaGame = new Trivia(console);
            for (Player p : players) {
                triviaGame.add(p);
            }
            triviaGame.launchMultiplayer(players);

            console.println("Multiplayer Trivia session completed!");
            for (Player player : players) {
                player.getAccount().addGameEntry("Multiplayer Trivia Session");
            }
        } else {
            console.println("Not enough players for multiplayer! Need at least 2.");
        }
        console.getStringInput("Press ENTER to continue...");
    }

    private void viewTriviaRules() {
        console.println("Trivia Rules will be displayed here.");
        console.getStringInput("Press ENTER to continue...");
    }

    // ----------------NUMBER GUESS-------------------------

    public void playNumberGuessGame(Player player) {
        try {
            UIRender uiRender = new UIRender();
            uiRender.displayNumberGuessWelcomeHeader();

            int choice = console.getIntegerInput("Enter your choice (1-2): ");

            switch (choice) {
                case 1:
                    playSinglePlayerNumberGuess(player);
                    break;
                case 2:
                    playMultiplayerNumberGuess();
                    break;
                case 3:
                    viewNumberGuessRules();
                    break; // Exit the menu
                default:
                    console.println("Invalid choice! Please select 1 or 2.");
                    break;
            }
        } catch (Exception e) {
            console.println("Error in Number Guess game menu: " + e.getMessage());
        }
    }

    private void playSinglePlayerNumberGuess(Player player) {
        console.println("Starting Number Guess for " + player.getUsername());
        player.getAccount().addGameEntry("Played Number Guess");
        flushScreen();
        NumberGuessGame game = new NumberGuessGame(console);
        game.launch(player);
        game.play();
        console.println("Number Guess session completed!");
        console.getStringInput("Press ENTER to continue...");
    }

    private void playMultiplayerNumberGuess() {
        try {
            // Choose host (current player/account)
            Player hostPlayer = currentAccount != null ? currentAccount.getPlayer() : null;
            if (hostPlayer == null) {
                console.println("No host player found. Please log in first.");
                return;
            }

            int maxPlayers = 4; // Set your desired max
            List<Player> players = getMultiplayerPlayers(hostPlayer, maxPlayers);

            if (players.size() < 2) {
                console.println("Not enough players for multiplayer! Need at least 2.");
                return;
            }

            flushScreen();
            UIRender uiRender = new UIRender();
            uiRender.displayNumberGuessWelcomeHeader();

            NumberGuessGame game = new NumberGuessGame(console);
            game.playMultiplayer(players);

            console.println("Multiplayer Number Guess session completed!");
            for (Player player : players) {
                player.getAccount().addGameEntry("Multiplayer Number Guess Session");
            }
            console.getStringInput("Press ENTER to continue...");
        } catch (Exception e) {
            console.println("Error in multiplayer Number Guess: " + e.getMessage());
        }
    }

    private void viewNumberGuessRules() {
        console.println("Number Guess Rules will be displayed here.");
        console.getStringInput("Press ENTER to continue...");
    }

    // ---------------MONEY METHODS----------------------

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

    // ------------------MULTI PLAYER METHODS---------------------

    private List<Player> getMultiplayerPlayers(Player hostPlayer, int maxPlayers) {
        List<Player> players = new ArrayList<>();
        players.add(hostPlayer);

        UIRender uiRender = new UIRender();
        uiRender.displayMultiplayerHeader(hostPlayer.getUsername(), UIRender.PURPLE);
        console.println("You can add up to " + (maxPlayers - 1) + " more players (max " + maxPlayers + " total).");

        int numPlayers = console.getIntegerInput("How many total players? (2-" + maxPlayers + "): ");
        if (numPlayers < 2 || numPlayers > maxPlayers) {
            console.println(UIRender.RED + "Invalid number! Must be between 2 and " + maxPlayers + " players."
                    + UIRender.RESET);
            return players;
        }

        for (int i = 1; i < numPlayers; i++) {
            Player additionalPlayer = getAdditionalPlayer(i + 1);
            if (additionalPlayer != null) {
                players.add(additionalPlayer);
                console.println(UIRender.GREEN + "Added " + additionalPlayer.getUsername() + " (Balance: $"
                        + String.format("%.2f", additionalPlayer.getAccount().getBalance()) + ")" + UIRender.RESET);
            } else {
                console.println(UIRender.RED + "Failed to add player " + (i + 1) + UIRender.RESET);
            }
        }
        return players;
    }

    // HELPER METHOD TO GET ADDITIONAL PLAYERS (from the list of existing accounts)
    private Player getAdditionalPlayer(int playerNumber) {
        try {
            List<CasinoAccount> allAccounts = new ArrayList<>(accountManager.loadAccounts().values());

            console.println("\nAvailable players for Player " + playerNumber + ":");
            int idx = 1;
            Map<Integer, CasinoAccount> indexToAccount = new HashMap<>();

            for (CasinoAccount account : allAccounts) {
                console.println(idx + ": " + account.getUsername() + " (Balance: $"
                        + String.format("%.2f", account.getBalance()) + ")");
                indexToAccount.put(idx, account);
                idx++;
            }

            console.println((idx) + ": Create new player");

            int selection = console.getIntegerInput("Select player (or " + idx + " for new): ");

            if (selection == idx) {
                return createNewPlayer();
            } else {
                CasinoAccount selectedAccount = indexToAccount.get(selection);
                if (selectedAccount != null) {
                    String password = console
                            .getStringInput("Enter password for " + selectedAccount.getUsername() + ": ");
                    CasinoAccount verifiedAccount = accountManager.getAccount(selectedAccount.getUsername(), password);

                    if (verifiedAccount != null) {
                        return verifiedAccount.getPlayer();
                    } else {
                        console.println("Incorrect password!");
                        return null;
                    }
                }
            }
        } catch (Exception e) {
            console.println("Error getting additional player: " + e.getMessage());
        }
        return null;
    }

    // HELPER METHOD TO CREATE NEW PLAYER
    private Player createNewPlayer() {
        try {
            String username = console.getStringInput("Enter username for new player: ");
            String password = console.getStringInput("Enter password for new player: ");
            double startingBalance = console.getDoubleInput("Enter starting balance: $");

            if (startingBalance < 10) {
                console.println("Minimum starting balance is $10!");
                return null;
            }

            // Create new account
            CasinoAccount newAccount = accountManager.createAccount(username, password);
            if (newAccount != null) {
                newAccount.deposit(startingBalance);
                return newAccount.getPlayer();
            }

        } catch (Exception e) {
            console.println("Error creating new player: " + e.getMessage());
        }

        return null;
    }

    public static List<Player> getRegisteredPlayers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRegisteredPlayers'");
    }

    private void flushScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
