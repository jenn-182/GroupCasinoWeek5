package com.github.zipcodewilmington.casino;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by leon on 7/21/2020.
 * `CasinoAccount` is registered for each user of the `Casino`.
 * The `CasinoAccount` is used to log into the system to select a `Game` to play.
 */
public class CasinoAccount {
    Double balance;
    String username;
    String password;
    List<String> gameHistory;
    List<String> transactionHistory;
    private Player player;
    private int totalWins;
    private int totalLosses;
    private Map<String, Integer> gameWins; // Track wins per game type
    private Map<String, Integer> gameLosses; // Track losses per game type
    private Map<String, Integer> gameCount; // Track total plays per game type

    // Constructor to initialize the CasinoAccount with a balance, username, and password
    CasinoAccount(Double initBalance, String username, String password){
        balance = initBalance;
        this.username = username;
        this.password = password;
        this.gameHistory = new ArrayList<>();
        this.player = new Player(username, this); 
        this.transactionHistory = new ArrayList<>(); 
        this.totalWins = 0;
        this.totalLosses = 0;
        this.gameWins = new HashMap<>();
        this.gameLosses = new HashMap<>();
        this.gameCount = new HashMap<>();
    }

    // Returns the balance of the account
    public Double getBalance(){
        return balance;
    }

    // Returns the username of the account
    public String getUsername() {
        return username;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public int getTotalLosses() {
        return totalLosses;
    }

    public String getFavoriteGame() {
        if (gameCount.isEmpty()) {
            return "No games played";
        }
        // Find the game with the most plays
        return gameCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No games played");
    }

    public void recordGamePlayed(String gameName) {
        gameCount.put(gameName, gameCount.getOrDefault(gameName, 0) + 1);
    }

    public void recordWin() {
        this.totalWins++;
    }
    
    public void recordLoss() {
        this.totalLosses++;
    }

    // Checks if the password matches the stored password
    public boolean checkPassword(String password){
        if(this.password.equals(password))
            return true;
        return false;
    }

    // Returns the Player associated with this account
    public Player getPlayer() {
        return player;
    }
    
    // Changes the password of the account
    public boolean resetPassword(String currentPassword, String newPassword) {
        // Check if the current password matches the stored password
        if(checkPassword(currentPassword)){
            password = newPassword;
            System.out.println("Current password is correct, password has changed");
            return true;
        } else {
            System.out.println("Current password is incorrect, did not change password");
        }
        return false;
    }

    // Deposits an amount into the account
    public void deposit(double amount){
        // Check if the amount is valid
        if(amount > 0){
            this.balance += amount;
            addTransactionEntry("Deposit: +$" + String.format("%.2f", amount));
            System.out.println("Deposit successful, new balance is: " + this.balance);
        } else {
            System.out.println("Deposit amount must be greater than 0");
        }
    }

    // Withdraws an amount from the account
    public boolean withdraw(double amount){
        // Check if the amount is valid
        if(amount <= 0){
            System.out.println("Withdrawal amount must be greater than 0");
            return false;
        }
        
        // Check if the amount is less than or equal to the balance
        if(amount <= balance){
            balance -= amount;
            addTransactionEntry("Withdrawal: -$" + String.format("%.2f", amount));
            System.out.println("Withdrawal successful, new balance is: " + this.balance);
            return true;
        } else {
            System.out.println("Invalid withdrawal amount");
            return false;
        }
    }

    // Adds a game entry to the game history and tracks statistics
    public void addGameEntry(String entry) {
        // Check if the game entry is valid
        if (entry != null && !entry.isEmpty()) {
            // Add to game history
            if (gameHistory == null) {
                gameHistory = new ArrayList<>();
            }
            gameHistory.add(entry);
            
            // Extract and record the game name for statistics
            String gameName = extractGameName(entry);
            if (gameName != null) {
                recordGamePlayed(gameName);
            }
            
            // Track wins and losses based on entry content
            String lowerEntry = entry.toLowerCase();
            if (lowerEntry.contains("won") || lowerEntry.contains("win") || lowerEntry.contains("+$")) {
                recordWin();
            } else if (lowerEntry.contains("lost") || lowerEntry.contains("loss") || lowerEntry.contains("-$")) {
                recordLoss();
            }
            
            System.out.println("Game entry added: " + entry);
        } else {
            System.out.println("Invalid game entry, cannot add to history");
        }
    }
    
    // Helper method to extract game name from game history entry
    private String extractGameName(String entry) {
        if (entry == null) return null;
        
        String lowerEntry = entry.toLowerCase();
        if (lowerEntry.contains("roulette")) return "Roulette";
        if (lowerEntry.contains("poker")) return "Poker";
        if (lowerEntry.contains("craps")) return "Craps";
        if (lowerEntry.contains("trivia")) return "Trivia";
        if (lowerEntry.contains("number")) return "Number Guess";
        
        return null;
    }

    // Retrieves the game history
    public List<String> getGameHistory() {
        return gameHistory != null ? new ArrayList<>(gameHistory) : new ArrayList<>();
    }

    // Adds a transaction entry to the transaction history
    public void addTransactionEntry(String entry) {
        if (transactionHistory == null) {
            transactionHistory = new ArrayList<>();
        }
        this.transactionHistory.add(entry);
    }
    
    // Retrieves the transaction history
    public List<String> getTransactionHistory() {
        return transactionHistory != null ? new ArrayList<>(transactionHistory) : new ArrayList<>();
    }

    // String representation of the CasinoAccount
    @Override
    public String toString() {
        return "CasinoAccount{" +
                "username='" + username + '\'' +
                ", balance=" + String.format("%.2f", balance) +
                ", gameHistorySize=" + (gameHistory != null ? gameHistory.size() : 0) +
                ", totalWins=" + totalWins +
                ", totalLosses=" + totalLosses +
                '}';
    }
}
