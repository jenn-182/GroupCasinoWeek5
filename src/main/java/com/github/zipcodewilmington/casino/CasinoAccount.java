package com.github.zipcodewilmington.casino;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leon on 7/21/2020.
 * `ArcadeAccount` is registered for each user of the `Arcade`.
 * The `ArcadeAccount` is used to log into the system to select a `Game` to
 * play.
 */
public class CasinoAccount {
    Double balance;
    String username;
    String password;
    List<String> gameHistory;

    // Constructor to initialize the CasinoAccount with a balance, username, and password
    CasinoAccount(Double initBalance, String username, String password){
        balance = initBalance;
        this.username = username;
        this.password = password;
        this.gameHistory = new ArrayList<>();
    }

    // Returns the balance of the account
    public Double getBalance(){
        return balance;
    }

    // Returns the username of the account
    public String getUsername() {
        return username;
    }

    // Checks if the password matches the stored password
    public boolean checkPassword(String password){
        if(this.password.equals(password))
            return true;
        return false;
    }

    // Changes the password of the account
    public boolean resetPassword(String currentPassword, String newPassword) {
    // Check if the current password matches the stored password{
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
        if(amount > 0 && amount <= balance){
            balance -= amount;
            System.out.println("Withdrawal successful, new balance is: " + this.balance);
            return true;
        } else {
            System.out.println("Invalid withdrawal amount");
            return false;
        }
    }

    // Adds a game entry to the game history
    public void addGameEntry (String entry) {
        // Check if the game name is valid
        if (entry != null && !entry.isEmpty()) {
            gameHistory.add(entry);
            System.out.println("Game entry added: " + entry);
        } else {
            System.out.println("Invalid game name, cannot add to history");
        }
    }

    // Retrieves the game history
    public List<String> getGameHistory() {
        return new ArrayList<>(gameHistory);
    }

    // String representation of the CasinoAccount
    @Override
    public String toString() {
        return "CasinoAccount{" +
                "username='" + username + '\'' +
                ", balance=" + String.format("%.2f", balance) +
                ", gameHistorySize=" + gameHistory.size() +
                '}';
    }

}
