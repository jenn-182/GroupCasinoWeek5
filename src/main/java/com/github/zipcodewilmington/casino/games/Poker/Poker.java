package com.github.zipcodewilmington.casino.games.Poker;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.Player;

public class Poker implements GameInterface {

    private Player player; 
    private List<String> computers;
    private Deck dealerDeck;
    private Hand playerHand;
    private List<Hand> computerHands;
    private List<Card> communityCards;
    private int currentRound;
    private Scanner scanner;

    private GameState gameState;
    private HandAnalyzer handAnalyzer;
    private BettingManager bettingManager;

    private final double maxbet = 500.0;
    private final double minbet = 50.0;

    // Constructor is now public
    public Poker(Player player) {
        this.player = player;
        dealerDeck = new Deck();
        communityCards = new ArrayList<>();
        computers = new ArrayList<>();
        computerHands = new ArrayList<>();
        playerHand = new Hand();
        currentRound = 0;
        scanner = new Scanner(System.in);

        gameState = new GameState();
        handAnalyzer = new HandAnalyzer();
        bettingManager = new BettingManager();

        // Initialize GameState with actual player username
        gameState.updatePlayerName("Player", player.getUsername());
       
        for (int i = 1; i <= 3; i++) {
            computers.add("Bot" + i);
            computerHands.add(new Hand());
        }
    }

    @Override
    public void play() {
        dealCards();
        
        while (currentRound < 4 && gameState.isGameActive()) {
            dealCommunityCards();
            displayGameState();
            
            // Betting round
            conductBettingRound();
            
            if (gameState.getActivePlayers().size() <= 1) {
                // Only one player left, end game
                break;
            }
            
            currentRound++;
        }
        
        showdown();
    }

    @Override
    public void run() {
        play();
    }

    public void dealCards() {
        // Deal 2 cards to each player
        for (int i = 0; i < 2; i++) {
            playerHand.receiveCard(dealerDeck.dealCard());
            for (Hand computerHand : computerHands) {
                computerHand.receiveCard(dealerDeck.dealCard());
            }
        }
    }

    public void dealCommunityCards() {
        switch (currentRound) {
            case 0: break; // Pre-flop
            case 1: // Flop - 3 cards
                for (int i = 0; i < 3; i++) {
                    communityCards.add(dealerDeck.dealCard());
                }
                break;
            case 2: // Turn - 1 card
            case 3: // River - 1 card
                communityCards.add(dealerDeck.dealCard());
                break;
        }
    }

    public void conductBettingRound() {
        // Reset betting round
        bettingManager.startNewBettingRound();
        gameState.resetPlayerActions();
        
        
        
        // Continue betting until all active players have acted on current bet
        while (!allPlayersHaveActed()) {
            boolean anyPlayerActed = false;
            
            // Player action first
            if (gameState.isPlayerActive(player.getUsername()) && !gameState.hasPlayerActed(player.getUsername())) {
                // Check if player is all-in (has no money left)
                if (player.getAccount().getBalance() <= 0) {
                    System.out.println("You are all-in since your balance is now $0!");
                    gameState.setPlayerActed(player.getUsername()); // Mark as acted but stay active
                    anyPlayerActed = true;
                } else {
                    handlePlayerAction();
                    anyPlayerActed = true;
                }
            }
            
            // Computer actions - only for computers that haven't acted
            for (int i = 0; i < computers.size(); i++) {
                String computerName = computers.get(i);
                
                if (gameState.isPlayerActive(computerName) && !gameState.hasPlayerActed(computerName)) {
                    handleComputerAction(computerName, i);
                    anyPlayerActed = true;
                }
            }
            
            // Safety check to prevent infinite loop
            if (!anyPlayerActed) {
                System.out.println("DEBUG: No players acted, breaking loop");
                break;
            }
        }
    }
    
    private boolean allPlayersHaveActed() {
        for (String playerName : gameState.getActivePlayers()) {
            if (!gameState.hasPlayerActed(playerName)) {
                return false;
            }
        }
        return true;
    }

    public void handlePlayerAction() {
        System.out.println("\n" + player.getUsername() + "'s turn");
        System.out.println("Your balance: $" + player.getAccount().getBalance());
        playerHand.printHand();
        
        double currentBet = bettingManager.getCurrentBet();
        double playerBet = gameState.getPlayerBet(player.getUsername());
        double amountToCall = currentBet - playerBet;
        
        System.out.println("Current bet: $" + currentBet);
        if (amountToCall > 0) {
            System.out.println("Amount to call: $" + amountToCall);
        }
        
        System.out.println("Choose action:");
        System.out.println("1. Fold");
        if (amountToCall > 0) {
            System.out.println("2. Call ($" + amountToCall + ")");
        } else {
            System.out.println("2. Check");
        }
        System.out.println("3. Raise");
        System.out.println("4. All-In ($" + player.getAccount().getBalance() + ")");
        
        int choice = getPlayerChoice(1, 4);
        
        switch (choice) {
            case 1: // Fold
                gameState.foldPlayer(player.getUsername());
                gameState.setPlayerActed(player.getUsername());
                System.out.println("You folded.");
                break;
                
            case 2: // Call/Check
                if (amountToCall > 0) {
                    if (bettingManager.canAfford(player, amountToCall)) {
                        bettingManager.processBet(player, amountToCall, gameState);
                        gameState.setPlayerBet(player.getUsername(), currentBet);
                        gameState.setPlayerActed(player.getUsername());
                        System.out.println("You called $" + amountToCall);
                    } else {
                        System.out.println("Not enough money to call!");
                        handlePlayerAction(); // Try again
                        return;
                    }
                } else {
                    gameState.setPlayerActed(player.getUsername());
                    System.out.println("You checked.");
                }
                break;
                
            case 3: // Raise
                handleRaise();
                break;
                
            case 4: // All-In
                handleAllIn();
                break;
        }
    }

    private void handleRaise() {
        double currentBet = bettingManager.getCurrentBet();
        double playerBet = gameState.getPlayerBet(player.getUsername());
        double amountToCall = currentBet - playerBet;
        
        // Calculate minimum raise properly
        double minRaise;
        if (amountToCall > 0) {
            // Someone has bet, so minimum raise is double the current bet minus what we've already put in
            minRaise = Math.max(minbet, currentBet * 2 - playerBet);
        } else {
            // No one has bet this round (everyone checked), so minimum raise is just the min bet
            minRaise = minbet;
        }
        
        double playerBalance = player.getAccount().getBalance();
        
        // Check if player can afford the minimum raise
        if (playerBalance < minRaise) {
            System.out.println("You don't have enough money to make the minimum raise of $" + minRaise);
            System.out.println("You have $" + playerBalance + " remaining.");
            System.out.println("You can only Call, Fold, or All-In.");
            handlePlayerAction(); // Go back to main menu
            return;
        }
        
        System.out.println("Minimum raise: $" + minRaise);
        System.out.println("Enter raise amount: ");
        
        double raiseAmount = getPlayerBetAmount();
        
        if (raiseAmount < minRaise) {
            System.out.println("Raise too small! Minimum: $" + minRaise);
            handleRaise(); // Try again
            return;
        }
        
        if (!bettingManager.canAfford(player, raiseAmount)) {
            System.out.println("Not enough money!");
            handleRaise(); // Try again
            return;
        }
        
        bettingManager.processBet(player, raiseAmount, gameState);
        double newBetLevel = playerBet + raiseAmount;
        bettingManager.setCurrentBet(newBetLevel);
        gameState.setPlayerBet(player.getUsername(), newBetLevel);
        
        // KEY CHANGE: Reset all other players' actions since there's a new bet to respond to
        gameState.resetPlayerActionsExcept(player.getUsername());
        gameState.setPlayerActed(player.getUsername()); // Raiser has acted on this bet level
        
        System.out.println("You raised to $" + newBetLevel);
    }

    private void handleAllIn() {
        double playerBalance = player.getAccount().getBalance();
        double currentBet = bettingManager.getCurrentBet();
        double playerBet = gameState.getPlayerBet(player.getUsername());
        double newPlayerBet = playerBet + playerBalance;
        
        // Put all remaining money into the pot
        bettingManager.processBet(player, playerBalance, gameState);
        gameState.setPlayerBet(player.getUsername(), newPlayerBet);
        gameState.setPlayerActed(player.getUsername());
        
        System.out.println("You went all-in with $" + playerBalance + "!");
        System.out.println("Your total bet is now $" + newPlayerBet);
        
        // If the all-in amount is more than the current bet, it's effectively a raise
        if (newPlayerBet > currentBet) {
            bettingManager.setCurrentBet(newPlayerBet);
            gameState.resetPlayerActionsExcept(player.getUsername());
            gameState.setPlayerActed(player.getUsername());
            System.out.println("This counts as a raise to $" + newPlayerBet);
        }
    }

    private int getPlayerChoice(int min, int max) {
        while (true) {
            try {
                int choice = scanner.nextInt();
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.println("Invalid choice. Please enter " + min + "-" + max);
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    private double getPlayerBetAmount() {
        while (true) {
            try {
                return scanner.nextDouble();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    public void handleComputerAction(String computerName, int computerIndex) {
        System.out.println(computerName + " is thinking...");
        
        // Simple AI logic
        double currentBet = bettingManager.getCurrentBet();
        double computerBet = gameState.getPlayerBet(computerName);
        double amountToCall = currentBet - computerBet;
        
        // Random decision for now (you can make this smarter later)
        double random = Math.random();
        
        if (random < 0.2) { // 20% chance to fold
            gameState.foldPlayer(computerName);
            gameState.setPlayerActed(computerName);
            System.out.println(computerName + " folded.");
            
        } else if (random < 0.8) { // 60% chance to call
            if (amountToCall > 0) {
                // Assume computers have enough money for simplicity
                gameState.addToPot(amountToCall);
                gameState.setPlayerBet(computerName, currentBet);
                gameState.setPlayerActed(computerName);
                System.out.println(computerName + " called $" + amountToCall);
            } else {
                gameState.setPlayerActed(computerName);
                System.out.println(computerName + " checked.");
            }
            
        } else { // 20% chance to raise
            double raiseAmount = minbet + (Math.random() * 100);
            double newBetLevel = currentBet + raiseAmount;
            bettingManager.setCurrentBet(newBetLevel);
            gameState.addToPot(amountToCall + raiseAmount);
            gameState.setPlayerBet(computerName, newBetLevel);
            
            // KEY CHANGE: Reset all other players' actions since there's a new bet
            gameState.resetPlayerActionsExcept(computerName);
            gameState.setPlayerActed(computerName); // This computer has acted on this bet level
            
            System.out.println(computerName + " raised to $" + newBetLevel);
        }
    }

    public void handleComputerActions() {
        for (int i = 0; i < computers.size(); i++) {
            String computerName = computers.get(i);
            
            if (!gameState.isPlayerActive(computerName) || gameState.hasPlayerActed(computerName)) {
                continue; // Skip if folded or already acted
            }
            
            handleComputerAction(computerName, i);
        }
    }

    public void showdown() {
        System.out.println("\n=== SHOWDOWN ===");
        
        List<String> activePlayers = gameState.getActivePlayers();
        
        if (activePlayers.size() == 1) {
            System.out.println(activePlayers.get(0) + " wins by default!");
            // Award pot to winner
            if (activePlayers.get(0).equals(player.getUsername())) {
                player.getAccount().deposit(gameState.getPot());
            }
            return;
        }
        
        // Show hands of active players
        if (gameState.isPlayerActive(player.getUsername())) {
            System.out.print(player.getUsername() + ": ");
            playerHand.printHand();
        }
        
        for (int i = 0; i < computers.size(); i++) {
            String computerName = computers.get(i);
            if (gameState.isPlayerActive(computerName)) {
                System.out.print(computerName + ": ");
                computerHands.get(i).printHand();
            }
        }
        
        System.out.println("Winner determination coming soon...");
        // TODO: Use HandAnalyzer to determine winner
    }

    public void displayGameState() {
        System.out.println("\n\n=== Round " + (currentRound + 1) + " ===");
        System.out.println("Pot: $" + gameState.getPot());
        
        if (!communityCards.isEmpty()) {
            System.out.print("Community Cards: ");
            for (Card card : communityCards) {
                System.out.print(card + " ");
            }
            System.out.println();
        }
        
        System.out.println("Active players: " + gameState.getActivePlayers().size());
    }

    @Override
    public boolean add(Player player) {
        this.player = player;
        return true;
    }

    @Override
    public boolean remove(Player player) {
        this.player = null;
        return true;
    }

    @Override
    public boolean isGamblingGame() {
        return true;
    }

    @Override
    public String getGameName() {
        return "Poker";
    }

    @Override
    public int getMinimumBet() {
        return (int) minbet;
    }

    @Override
    public int getMaximumBet() {
        return (int) maxbet;
    }
}