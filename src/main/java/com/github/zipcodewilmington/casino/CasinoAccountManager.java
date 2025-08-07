package com.github.zipcodewilmington.casino;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by leon on 7/21/2020.
 * `Casino Account Manager` stores, manages, and retrieves `Casino Account`
 * objects
 * it is advised that every instruction in this class is logged
 */
public class CasinoAccountManager {

    private Map<String, CasinoAccount> accounts;

    // Constructs a new CasinoAccountManager with an empty account list
    public CasinoAccountManager() {
        this.accounts = new HashMap<>();
        createDummyAccounts(); // Initialize with some dummy accounts for testing
    }

    private void createDummyAccounts() {
        // Create test accounts with different balances
        CasinoAccount testUser1 = new CasinoAccount(100.0, "jenn", "123");
        CasinoAccount testUser2 = new CasinoAccount(250.50, "anthony", "123");
        CasinoAccount testUser3 = new CasinoAccount(500.0, "josiah", "123");
        CasinoAccount testUser4 = new CasinoAccount(500.0, "younis", "123");
        CasinoAccount testUser5 = new CasinoAccount(500.0, "danish", "123");
        CasinoAccount testUser6 = new CasinoAccount(500.0, "frank", "123");


        accounts.put("jenn", testUser1);
        accounts.put("anthony", testUser2);
        accounts.put("josiah", testUser3);
        accounts.put("younis", testUser4);
        accounts.put("danish", testUser5);
        accounts.put("frank", testUser6);

        // Add some transaction history
        testUser1.addTransactionEntry("Initial deposit of $500.00");
        testUser2.addTransactionEntry("Initial deposit of $500.00");
        testUser2.addTransactionEntry("Won $25.00 playing Poker");
        testUser3.addTransactionEntry("Initial deposit of $500.00");
        testUser4.addTransactionEntry("Initial deposit of $500.00");
        testUser5.addTransactionEntry("Initial deposit of $500.00");
        testUser6.addTransactionEntry("Initial deposit of $500.00");


        testUser1.addGameEntry("Played Poker - Won $25.00");
        testUser2.addGameEntry("Played Roulette - Lost $10.00");
        testUser3.addGameEntry("Played Blackjack - Won $50.00");
        testUser4.addGameEntry("Played Slots - Lost $30.00");
        testUser5.addGameEntry("Played Roulette - Won $100.00");

        testUser2.addTransactionEntry("Deposited $50.00");
        testUser3.addGameEntry("Played Blackjack - Lost $20.00");
        testUser4.addGameEntry("Played Slots - Won $100.00");  
        testUser5.addGameEntry("Played Roulette - Lost $30.00");
        testUser6.addGameEntry("Played Blackjack - Won $50.00");
        testUser6.addTransactionEntry("Withdrew $100.00");

    }

    // Load all Casino Account objects
    public Map<String, CasinoAccount> loadAccounts() {
        return accounts;
    }

    /**
     * @param accountName     name of account to be returned
     * @param accountPassword password of account to be returned
     * @return `CasinoAccount` with specified `accountName` and `accountPassword`
     */

    // Retrieve an account by name and password
    public CasinoAccount getAccount(String accountName, String accountPassword) {
        CasinoAccount account = this.accounts.get(accountName);
        if (account != null && account.checkPassword(accountPassword)) {
            return account;
        } else {
            return null;
        }

        // String currentMethodName = new Object() {
        // }.getClass().getEnclosingMethod().getName();
        // String currentClassName = getClass().getName();
        // String errorMessage = "Method with name [ %s ], defined in class with name [
        // %s ] has not yet been implemented";
        // throw new RuntimeException(String.format(errorMessage, currentMethodName,
        // currentClassName));
    }

    /**
     * logs & creates a new `CasinoAccount`
     *
     * @param accountName     name of account to be created
     * @param accountPassword password of account to be created
     * @return new instance of `CasinoAccount` with specified `accountName` and
     *         `accountPassword`
     */

    // Create a new account
    public CasinoAccount createAccount(String accountName, String accountPassword) {
        if (accounts.containsKey(accountName)) {
            System.out.println("Account with name " + accountName + " already exists.");
            return null;
        }

        CasinoAccount newAccount = new CasinoAccount(0.0, accountName, accountPassword);
        accounts.put(accountName, newAccount);
        System.out.println("Account created successfully: " + newAccount);
        return newAccount;
    }

    // String currentMethodName = new Object()
    // {}.getClass().getEnclosingMethod().getName();
    // String currentClassName = getClass().getName();
    // String errorMessage = "Method with name [ %s ], defined in class with name [
    // %s ] has not yet been implemented";
    // throw new RuntimeException(String.format(errorMessage, currentMethodName,
    // currentClassName));
    // }

    /**
     * logs & registers a new `CasinoAccount` to `this.getCasinoAccountList()`
     *
     * @param casinoAccount the casinoAccount to be added to
     *                      `this.getCasinoAccountList()`
     */

    // Register a new account
    public void registerAccount(CasinoAccount casinoAccount) {

        if (accounts.containsKey(casinoAccount.getUsername())) {
            System.out.println("Account with name " + casinoAccount.getUsername() + " already exists.");
            return;
        }
        accounts.put(casinoAccount.getUsername(), casinoAccount);
        System.out.println("Account registered successfully: " + casinoAccount);

        // String currentMethodName = new Object() {
        // }.getClass().getEnclosingMethod().getName();
        // String currentClassName = getClass().getName();
        // String errorMessage = "Method with name [ %s ], defined in class with name [
        // %s ] has not yet been implemented";
        // throw new RuntimeException(String.format(errorMessage, currentMethodName,
        // currentClassName));
    }

    // Update an existing account
    public void updateAccount(CasinoAccount account) {
        if (accounts.containsKey(account.getUsername())) {
            accounts.put(account.getUsername(), account);
            System.out.println("Account updated successfully: " + account);
        } else {
            System.out.println("Account with name " + account.getUsername() + " does not exist.");
        }
    }

    // Save all accounts to the manager
    public void saveAllAccounts(Collection<CasinoAccount> accountsToSave) {
        this.accounts.clear();
        for (CasinoAccount account : accountsToSave) {
            accounts.put(account.getUsername(), account);
        }
        System.out.println("All accounts saved successfully.");
    }

    // Deletes a specific account by username
    public boolean deleteAccount(String username) {
        if (this.accounts.containsKey(username)) {
            this.accounts.remove(username);
            System.out.println("Account with name " + username + " deleted successfully.");
            return true;
        } else {
            System.out.println("Account with name " + username + " does not exist.");
            return false;
        }
    }

    // Alternate getAccount for username without password verification
    public CasinoAccount getAccountByUsername(String username) {
        return this.accounts.get(username);
    }
}
