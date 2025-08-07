package com.github.zipcodewilmington.casino.games.Poker;

import java.util.ArrayList;
import java.util.List;
import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.Player;

public class Poker implements GameInterface {
    

    private List<Player> players;
    private List<String> computers;
    private Deck dealerDeck;
    private List<Hand> hands;
    private List<Card> communityCards;
    private List<Boolean> activePlayers; // Track who's still in the hand
    private int curPlayerIndex;

    // Composition - using the other classes
    private GameState gameState;
    private HandAnalyzer handAnalyzer;
    private BettingManager bettingManager;

    private final double maxbet = 500.0;
    private final double minbet = 50.0;

    Poker(List<Player> players) {
        dealerDeck = new Deck();
        communityCards = new ArrayList<>();
        this.players = new ArrayList<>(players);
        computers = new ArrayList<>();
        hands = new ArrayList<>();
        activePlayers = new ArrayList<>();
        curPlayerIndex = 0;

        // Initialize helper classes
        gameState = new GameState();
        handAnalyzer = new HandAnalyzer();
        bettingManager = new BettingManager();

        addComputerPlayers();
        initializeHands();
    }

    @Override
    public void play() {
        // Main game loop
        startNewHand();
        
        while (shouldContinueGame() && gameState.getRound() < 4) {
            dealCommunityCardsForRound();
            displayGameState();
            playBettingRound();
            gameState.nextRound();
        }
        
        if (shouldContinueGame()) {
            showdown();
        }
    }

    @Override
    public void run() {
        play();
    }

    // ESSENTIAL GAME FLOW METHODS
    public void startNewHand() {
        // Reset everything for new hand
        communityCards.clear();
        resetActivePlayers();
        gameState.resetGame();
        dealCards(); // Deal hole cards
    }

    public void dealCards() {
        // Deal 2 hole cards to each player
        for(int i = 0; i < 2; i++){
            for (Hand hand : hands) {
                hand.receiveCard(dealerDeck.dealCard());
            }
        }
    }

    public void dealCommunityCardsForRound() {
        // Deal community cards based on current round
        int currentRound = gameState.getRound();
        
        switch (currentRound) {
            case 0: break; // Pre-flop - no community cards
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

    public void playBettingRound() {
        // Handle betting for all active players
        for (int i = 0; i < getTotalPlayers(); i++) {
            if (activePlayers.get(i)) {
                curPlayerIndex = i;
                handlePlayerAction();
            }
        }
    }

    public void handlePlayerAction() {
        // Handle current player's action (human or computer)
        if (isHumanPlayer()) {
            handleHumanAction();
        } else {
            handleComputerAction();
        }
    }

    public void handleHumanAction() {
        // Get human input and process action
        // TODO: Get user input (fold, call, bet, raise)
        // TODO: Process action using BettingManager
    }

    public void handleComputerAction() {
        // AI decision making
        // TODO: Simple AI logic
        // TODO: Process action using BettingManager
    }

    // ESSENTIAL PLAYER MANAGEMENT
    public void nextPlayer() {
        curPlayerIndex = (curPlayerIndex + 1) % getTotalPlayers();
    }

    public String getCurrentPlayerName() {
        if (isHumanPlayer()) {
            return players.get(curPlayerIndex).getUsername();
        } else {
            int computerIndex = curPlayerIndex - players.size();
            return computers.get(computerIndex);
        }
    }

    public boolean isHumanPlayer() {
        return curPlayerIndex < players.size();
    }

    public int getTotalPlayers() {
        return players.size() + computers.size();
    }

    public int getActivePlayers() {
        int count = 0;
        for (boolean active : activePlayers) {
            if (active) count++;
        }
        return count;
    }

    // ESSENTIAL GAME STATE METHODS
    public boolean shouldContinueGame() {
        return getActivePlayers() > 1 && gameState.isGameActive();
    }

    public void showdown() {
        // Reveal hands and determine winner
        displayGameState();
        Player winner = evaluateHands();
        // TODO: Pay winner using BettingManager
    }

    public Player evaluateHands() {
        // Find best hand using HandAnalyzer
        // TODO: Compare all active players' hands using hole cards + community cards
        return null;
    }

    public void displayGameState() {
        // Show current game state
        System.out.println("=== POKER GAME STATE ===");
        System.out.println("Round: " + gameState.getRound() + " | Pot: $" + gameState.getPot() + " | Current Bet: $" + gameState.getCurrentBet());
        
        if (!communityCards.isEmpty()) {
            System.out.print("Community Cards: ");
            for (Card card : communityCards) {
                System.out.print(card + " ");
            }
            System.out.println();
        }
    }

    // ESSENTIAL BETTING ACTIONS (Combined)
    public void processPlayerAction(String action, double amount) {
        // Process any player action in one method
        switch (action.toLowerCase()) {
            case "fold":
                activePlayers.set(curPlayerIndex, false);
                break;
            case "call":
                // TODO: Use BettingManager to process call
                break;
            case "bet":
            case "raise":
                // TODO: Use BettingManager to process bet/raise
                break;
        }
    }

    // UTILITY METHODS
    private void resetActivePlayers() {
        activePlayers.clear();
        for (int i = 0; i < getTotalPlayers(); i++) {
            activePlayers.add(true);
        }
    }

    public void resetForNextHand() {
        // Collect cards and prepare for next hand
        collectCards();
        communityCards.clear();
        resetActivePlayers();
    }

    public void collectCards() {
        // Return all cards to deck
        for (Hand hand : hands) {
            // TODO: Return cards from hands to deck
        }
        // TODO: Return community cards to deck
    }

    // REQUIRED INTERFACE METHODS
    private void initializeHands() {
        for (Player player : players) {
            hands.add(new Hand());
        }
        for (String com : computers) {
            hands.add(new Hand());
        }
    }

    private void addComputerPlayers() {
        for (int i = 0; i < 4; i++)
            computers.add("Bot" + i);
    }

    @Override
    public boolean remove(Player player) {
        if (players.contains(player)) {
            players.remove(player);
            return true;
        }
        return false;
    }

    @Override
    public boolean add(Player player) {
        if (players.contains(player)) {
            return false;
        }
        players.add(player);
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