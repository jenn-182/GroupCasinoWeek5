package com.github.zipcodewilmington.casino.ui;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import com.github.zipcodewilmington.Casino;
import com.github.zipcodewilmington.casino.CasinoAccount;
import com.github.zipcodewilmington.casino.Player;

public class IOConsole {
    private final Scanner input;
    private final PrintStream output;
    private final AnsiColor ansiColor;
    private Casino casino;
    private final UIRender uiRender;

    public IOConsole(Casino casino) {
        this(AnsiColor.AUTO);
        this.casino = casino;
    }

    public IOConsole(AnsiColor ansiColor) {
        this(ansiColor, System.in, System.out);
    }

    public IOConsole(AnsiColor ansiColor, InputStream in, PrintStream out) {
        this.ansiColor = ansiColor;
        this.input = new Scanner(in);
        this.output = out;
        this.uiRender = new UIRender();
    }

    private void flushScreen() {
        output.print("\033[2J\033[H");
        output.flush();
    }

    private void displayMessage(String message, AnsiColor color) {
        output.println(color.getColor() + message);
    }

    private void displayMessage(String message) {
        displayMessage(message, AnsiColor.AUTO);
    }

    private String getColoredStringInput(String prompt, AnsiColor color) {
        output.print(color.getColor() + prompt);
        return input.nextLine();
    }

    public void print(String val, Object... args) {
        output.format(ansiColor.getColor() + val, args);
    }

    public void println(String val, Object... vals) {
        print(val + "\n", vals);
    }

    public String getStringInput(String prompt, Object... args) {
        println(prompt, args);
        return input.nextLine();
    }

    public Double getDoubleInput(String prompt, Object... args) {
        String stringInput = getStringInput(prompt, args);
        try {
            Double doubleInput = Double.parseDouble(stringInput);
            return doubleInput;
        } catch (NumberFormatException nfe) {
            println("[ %s ] is an invalid user input!", stringInput);
            println("Try inputting a numeric value!");
            return getDoubleInput(prompt, args);
        }
    }

    public Long getLongInput(String prompt, Object... args) {
        String stringInput = getStringInput(prompt, args);
        try {
            Long longInput = Long.parseLong(stringInput);
            return longInput;
        } catch (NumberFormatException nfe) {
            println("[ %s ] is an invalid user input!", stringInput);
            println("Try inputting an integer value!");
            return getLongInput(prompt, args);
        }
    }

    public Integer getIntegerInput(String prompt, Object... args) {
        return getLongInput(prompt, args).intValue();
    }

    public void start() {
        flushScreen();
        boolean running = true;
        while (running) {
            flushScreen();
            uiRender.displayMainMenuHeader();
            String choice = getColoredStringInput("Please select an option: ", AnsiColor.YELLOW);

            switch (choice) {
                case "1":
                    flushScreen();
                    handleRegistration();
                    break;
                case "2":
                    flushScreen();
                    handleLogin();
                    break;
                case "3":
                    flushScreen();
                    displayMessage("Exiting the Casino. Goodbye!", AnsiColor.RED);
                    running = false;
                    break;
                default:
                    displayMessage("Invalid option. Please try again.", AnsiColor.RED);
                    break;
            }
        }
        input.close();
    }

    private void displayMainMenu() {
        uiRender.displayMainMenuHeader();
    }

    private void handleRegistration() {
        flushScreen();
        displayMessage("\n--- Registration ---", AnsiColor.BLUE);
        String username = getStringInput("Enter desired username: ");
        System.out.println();
        String password = getStringInput("Enter desired password: ");
        System.out.println();

        if (casino != null && casino.getAccountManager() != null) {
            CasinoAccount newAccount = casino.getAccountManager().createAccount(username, password);
            if (newAccount != null) {
                displayMessage("Registration successful! Welcome " + username
                        + "! Your account starts with a 0.0 balance. Add some money to play!", AnsiColor.GREEN);
                double initialDepositAmount = Double.parseDouble(getColoredStringInput(
                        "Would you like to make an initial deposit? Enter amount (e.g., 50.00) or 0 to skip: ",
                        AnsiColor.CYAN));
                if (initialDepositAmount > 0) {
                    newAccount.deposit(initialDepositAmount);
                    displayMessage("Your current balance is: $" + String.format("%.2f", newAccount.getBalance())
                            + " . You can now log in!", AnsiColor.YELLOW);
                    newAccount.addTransactionEntry("Initial deposit of $" + String.format("%.2f", initialDepositAmount));
                }
            } else {
                displayMessage("Registration failed. Username may already exist.", AnsiColor.RED);
            }
        } else {
            displayMessage("Casino system not available.", AnsiColor.RED);
        }
    }

    private void handleLogin() {
        flushScreen();
        displayMessage("\n--- Login ---", AnsiColor.BLUE);
        String username = getStringInput("Enter username: ");
        System.out.println();
        String password = getStringInput("Enter password: ");
        System.out.println();

        if (casino != null && casino.getAccountManager() != null) {
            CasinoAccount account = casino.getAccountManager().getAccount(username, password);
            if (account != null) {
                flushScreen();
                casino.setCurrentAccount(account);
                displayMessage("\nLogin successful! Welcome back " + username + "!", AnsiColor.GREEN);
                getStringInput("\nPress ENTER to continue to main menu...");
                flushScreen();
                handleCasinoMenu();
            } else {
                displayMessage("\nLogin failed. Invalid username or password.", AnsiColor.RED);
                getStringInput("\nPress ENTER to continue...");
            }
        } else {
            displayMessage("\nCasino system not available.", AnsiColor.RED);
            getStringInput("\nPress ENTER to continue...");
        }
    }

    private void handleCasinoMenu() {
        flushScreen();
        boolean inGame = true;
        while (inGame) {
            flushScreen();
            Player currentPlayer = casino.getCurrentAccount().getPlayer();

            uiRender.displayCasinoMenuHeader(currentPlayer.getUsername());

            String choice = getColoredStringInput("Select an option: ", AnsiColor.YELLOW);
            switch (choice) {
                case "1":
                    flushScreen();
                    displayMessage("Loading games...", AnsiColor.GREEN);
                    selectGame(currentPlayer);
                    break;
                case "2":
                    flushScreen();
                    handleBankingAndAccountMenu(currentPlayer);
                    break;
                case "3":
                    flushScreen();
                    handleGamingProfileMenu(currentPlayer);
                    break;
                case "4":
                    flushScreen();
                    displayMessage("Logging out...", AnsiColor.YELLOW);
                    casino.setCurrentAccount(null);
                    inGame = false;
                    break;
                default:
                    flushScreen();
                    displayMessage("Invalid option. Please try again.", AnsiColor.YELLOW);
                    getStringInput("\nPress ENTER to continue...");
                    break;
            }
        }
    }

    private void selectGame(Player currentPlayer) {
        flushScreen();
        uiRender.displayGameSelectionHeader();

        String choice = getColoredStringInput("Select a game: ", AnsiColor.YELLOW);

        switch (choice) {
            case "1":
                flushScreen();
                casino.launchGame("roulette", currentPlayer);
                getStringInput("\nPress ENTER to return to menu...");
                break;
            case "2":
                flushScreen();
                casino.launchGame("poker", currentPlayer);
                getStringInput("\nPress ENTER to return to menu...");
                break;
            case "3":
                flushScreen();
                casino.launchGame("craps", currentPlayer);
                getStringInput("\nPress ENTER to return to menu...");
                break;
            case "4":
                flushScreen();
                casino.launchGame("trivia", currentPlayer);
                getStringInput("\nPress ENTER to return to menu...");
                break;
            case "5":
                flushScreen();
                casino.launchGame("numberguess", currentPlayer);
                getStringInput("\nPress ENTER to return to menu...");
                break;
            case "6":
                break;
            default:
                displayMessage("Invalid option. Please try again.", AnsiColor.RED);
                getStringInput("\nPress ENTER to continue...");
                selectGame(currentPlayer);
                break;
        }
    }

    private void handleBankingAndAccountMenu(Player currentPlayer) {
        flushScreen();
        boolean inBanking = true;
        while (inBanking) {
            flushScreen();
            uiRender.displayBankingMenuHeader(currentPlayer.getUsername(), currentPlayer.getAccount().getBalance());

            String choice = getColoredStringInput("Select an option: ", AnsiColor.YELLOW);
            switch (choice) {
                case "1":
                    flushScreen();
                    handleDeposit(currentPlayer);
                    break;
                case "2":
                    flushScreen();
                    handleWithdrawal(currentPlayer);
                    break;
                case "3":
                    flushScreen();
                    displayTransactionHistory(currentPlayer);
                    break;
                case "4":
                    inBanking = false;
                    break;
                default:
                    flushScreen();
                    displayMessage("Invalid option. Please try again.", AnsiColor.RED);
                    getStringInput("\nPress ENTER to continue...");
                    break;
            }
        }
    }

    private void handleGamingProfileMenu(Player currentPlayer) {
        flushScreen();
        boolean inProfile = true;
        while (inProfile) {
            flushScreen();
            uiRender.displayGamingProfileHeader(currentPlayer);

            String choice = getColoredStringInput("Select an option: ", AnsiColor.YELLOW);
            switch (choice) {
                case "1":
                    flushScreen();
                    displayGameHistory(currentPlayer);
                    break;
                case "2":
                    flushScreen();
                    displayGamingStatistics(currentPlayer);
                    break;
                case "3":
                    flushScreen();
                    inProfile = false;
                    break;
                default:
                    flushScreen();
                    displayMessage("Invalid option. Please try again.", AnsiColor.RED);
                    getStringInput("\nPress ENTER to continue...");
                    break;
            }
        }
    }

    private void handleDeposit(Player currentPlayer) {
        displayMessage("\n--- Deposit Money ---", AnsiColor.BLUE);
        displayMessage("Current Balance: $" + String.format("%.2f", currentPlayer.getAccount().getBalance()),
                AnsiColor.YELLOW);
        
        try {
            double depositAmount = getDoubleInput("Enter amount to deposit (or 0 to cancel): ");
            
            if (depositAmount == 0) {
                displayMessage("Deposit cancelled.", AnsiColor.YELLOW);
                return;
            }
            
            if (depositAmount > 0) {
                if (casino.processDeposit(currentPlayer, depositAmount)) {
                    displayMessage("Deposit successful!", AnsiColor.GREEN);
                    displayMessage("New Balance: $" + String.format("%.2f", currentPlayer.getAccount().getBalance()),
                            AnsiColor.YELLOW);
                } else {
                    displayMessage("Deposit failed. Please try again.", AnsiColor.RED);
                }
            } else {
                displayMessage("Invalid deposit amount. Amount must be greater than 0.", AnsiColor.RED);
            }
        } catch (Exception e) {
            displayMessage("Error processing deposit: " + e.getMessage(), AnsiColor.RED);
        }
        
        getStringInput("\nPress ENTER to continue...");
    }

    private void handleWithdrawal(Player currentPlayer) {
        displayMessage("\n--- Withdraw Money ---", AnsiColor.BLUE);
        double currentBalance = currentPlayer.getAccount().getBalance();
        displayMessage("Current Balance: $" + String.format("%.2f", currentBalance), AnsiColor.YELLOW);
        
        if (currentBalance <= 0) {
            displayMessage("Cannot withdraw: Insufficient funds.", AnsiColor.RED);
            getStringInput("\nPress ENTER to continue...");
            return;
        }
        
        try {
            double withdrawalAmount = getDoubleInput("Enter amount to withdraw (or 0 to cancel): ");
            
            if (withdrawalAmount == 0) {
                displayMessage("Withdrawal cancelled.", AnsiColor.YELLOW);
                return;
            }
            
            if (withdrawalAmount > 0) {
                if (casino.processWithdrawal(currentPlayer, withdrawalAmount)) {
                    displayMessage("Withdrawal successful!", AnsiColor.GREEN);
                    displayMessage("New Balance: $" + String.format("%.2f", currentPlayer.getAccount().getBalance()),
                            AnsiColor.YELLOW);
                } else {
                    displayMessage("Withdrawal failed. Check your balance and try again.", AnsiColor.RED);
                }
            } else {
                displayMessage("Invalid withdrawal amount. Amount must be greater than 0.", AnsiColor.RED);
            }
        } catch (Exception e) {
            displayMessage("Error processing withdrawal: " + e.getMessage(), AnsiColor.RED);
        }
        
        getStringInput("\nPress ENTER to continue...");
    }

    private void displayTransactionHistory(Player currentPlayer) {
        displayMessage("\n--- Transaction History ---", AnsiColor.BLUE);
        List<String> transactionHistory = currentPlayer.getAccount().getTransactionHistory();

        if (transactionHistory.isEmpty()) {
            displayMessage("No banking transactions found.", AnsiColor.YELLOW);
        } else {
            for (String entry : transactionHistory) {
                displayMessage("- " + entry, AnsiColor.WHITE);
            }
        }

        getStringInput("\nPress ENTER to continue...");
    }

    private void displayGameHistory(Player currentPlayer) {
        displayMessage("\n--- Game History for " + currentPlayer.getUsername() + " ---", AnsiColor.BLUE);
        List<String> gameHistory = currentPlayer.getAccount().getGameHistory();
        
        if (gameHistory.isEmpty()) {
            displayMessage("No game history available.", AnsiColor.YELLOW);
            displayMessage("Start playing games to build your gaming history!", AnsiColor.CYAN);
        } else {
            displayMessage("Total games played: " + gameHistory.size(), AnsiColor.GREEN);
            displayMessage("", AnsiColor.WHITE);
            for (String entry : gameHistory) {
                displayMessage("- " + entry, AnsiColor.WHITE);
            }
        }
        
        getStringInput("\nPress ENTER to continue...");
    }

    private void displayGamingStatistics(Player currentPlayer) {
        displayMessage("\n--- Gaming Statistics ---", AnsiColor.BLUE);
        displayMessage("Player: " + currentPlayer.getUsername(), AnsiColor.GREEN);
        
        List<String> gameHistory = currentPlayer.getAccount().getGameHistory();
        displayMessage("Total Games Played: " + gameHistory.size(), AnsiColor.WHITE);
        
        int rouletteGames = 0, pokerGames = 0, crapsGames = 0, triviaGames = 0, numberGuessGames = 0;
        
        for (String entry : gameHistory) {
            String lowerEntry = entry.toLowerCase();
            if (lowerEntry.contains("roulette")) rouletteGames++;
            else if (lowerEntry.contains("poker")) pokerGames++;
            else if (lowerEntry.contains("craps")) crapsGames++;
            else if (lowerEntry.contains("trivia")) triviaGames++;
            else if (lowerEntry.contains("number")) numberGuessGames++;
        }
        
        displayMessage("\n--- Games Breakdown ---", AnsiColor.CYAN);
        displayMessage("Roulette Games: " + rouletteGames, AnsiColor.WHITE);
        displayMessage("Poker Games: " + pokerGames, AnsiColor.WHITE);
        displayMessage("Craps Games: " + crapsGames, AnsiColor.WHITE);
        displayMessage("Trivia Games: " + triviaGames, AnsiColor.WHITE);
        displayMessage("Number Guess Games: " + numberGuessGames, AnsiColor.WHITE);
        
        String favoriteGame = "None yet";
        int maxGames = Math.max(rouletteGames, Math.max(pokerGames, Math.max(crapsGames, Math.max(triviaGames, numberGuessGames))));
        if (maxGames > 0) {
            if (rouletteGames == maxGames) favoriteGame = "Roulette";
            else if (pokerGames == maxGames) favoriteGame = "Poker";
            else if (crapsGames == maxGames) favoriteGame = "Craps";
            else if (triviaGames == maxGames) favoriteGame = "Trivia";
            else if (numberGuessGames == maxGames) favoriteGame = "Number Guess";
        }
        
        displayMessage("\nFavorite Game: " + favoriteGame, AnsiColor.YELLOW);
        displayMessage("Current Balance: $" + String.format("%.2f", currentPlayer.getAccount().getBalance()),
                AnsiColor.YELLOW);
        
        getStringInput("\nPress ENTER to continue...");
    }
}