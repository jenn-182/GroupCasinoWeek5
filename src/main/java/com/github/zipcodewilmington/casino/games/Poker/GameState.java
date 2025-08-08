package com.github.zipcodewilmington.casino.games.Poker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameState {

    private double pot;
    private boolean gameActive;
    private List<String> activePlayers;
    private List<String> foldedPlayers;
    private Map<String, Double> playerBets; // Track each player's total bet this hand
    private Map<String, Boolean> playerActions; // Track if player has acted this round

    public GameState() {
        pot = 0.0;
        gameActive = true;
        activePlayers = new ArrayList<>();
        foldedPlayers = new ArrayList<>();
        playerBets = new HashMap<>();
        playerActions = new HashMap<>();
        
        // Initialize with all players active
        activePlayers.add("Player"); // Will be updated with actual username
        activePlayers.add("Bot1");
        activePlayers.add("Bot2");
        activePlayers.add("Bot3");
        
        // Initialize all player bets to 0
        for (String player : activePlayers) {
            playerBets.put(player, 0.0);
            playerActions.put(player, false);
        }
    }

    public void addToPot(double amount) {
        pot += amount;
    }

    public double getPot() {
        return pot;
    }

    public boolean isGameActive() {
        return gameActive && activePlayers.size() > 1;
    }

    public void setGameActive(boolean active) {
        gameActive = active;
    }

    public List<String> getActivePlayers() {
        return new ArrayList<>(activePlayers);
    }

    public void foldPlayer(String playerName) {
        activePlayers.remove(playerName);
        foldedPlayers.add(playerName);
    }

    public boolean isPlayerActive(String playerName) {
        return activePlayers.contains(playerName);
    }

    public boolean isPlayerFolded(String playerName) {
        return foldedPlayers.contains(playerName);
    }

    public void setPlayerBet(String playerName, double totalBet) {
        playerBets.put(playerName, totalBet);
    }

    public double getPlayerBet(String playerName) {
        return playerBets.getOrDefault(playerName, 0.0);
    }

    public void setPlayerActed(String playerName) {
        playerActions.put(playerName, true);
    }

    public boolean hasPlayerActed(String playerName) {
        return playerActions.getOrDefault(playerName, false);
    }

    public void resetPlayerActions() {
        for (String player : playerActions.keySet()) {
            playerActions.put(player, false);
        }
    }

    public void resetPlayerActionsExcept(String exemptPlayer) {
        for (String player : playerActions.keySet()) {
            if (!player.equals(exemptPlayer)) {
                playerActions.put(player, false);
            }
        }
    }

    public void updatePlayerName(String oldName, String newName) {
        // Update player name in all collections
        if (activePlayers.contains(oldName)) {
            activePlayers.remove(oldName);
            activePlayers.add(newName);
        }
        
        if (playerBets.containsKey(oldName)) {
            Double bet = playerBets.remove(oldName);
            playerBets.put(newName, bet);
        }
        
        if (playerActions.containsKey(oldName)) {
            Boolean action = playerActions.remove(oldName);
            playerActions.put(newName, action);
        }
    }

    public int getActivePlayerCount() {
        return activePlayers.size();
    }

    public void resetForNewHand() {
        // Reset for a new hand
        activePlayers.clear();
        foldedPlayers.clear();
        playerBets.clear();
        playerActions.clear();
        pot = 0.0;
        gameActive = true;
        
        // Re-initialize players
        activePlayers.add("Player");
        activePlayers.add("Bot1");
        activePlayers.add("Bot2");
        activePlayers.add("Bot3");
        
        for (String player : activePlayers) {
            playerBets.put(player, 0.0);
            playerActions.put(player, false);
        }
    }

    public Map<String, Double> getAllPlayerBets() {
        return new HashMap<>(playerBets);
    }
}