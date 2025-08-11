package com.github.zipcodewilmington.Poker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.zipcodewilmington.casino.games.Poker.GameState;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

/**
 * JUnit test class for GameState
 */
@DisplayName("GameState Tests")
public class GameStateTest {

    private GameState gameState;

    @BeforeEach
    void setUp() {
        gameState = new GameState();
    }

    @Test
    @DisplayName("Should initialize with correct default values")
    void testInitialization() {
        assertEquals(0.0, gameState.getPot());
        assertTrue(gameState.isGameActive());
        assertEquals(4, gameState.getActivePlayerCount());
        
        List<String> activePlayers = gameState.getActivePlayers();
        assertTrue(activePlayers.contains("Player"));
        assertTrue(activePlayers.contains("Bot1"));
        assertTrue(activePlayers.contains("Bot2"));
        assertTrue(activePlayers.contains("Bot3"));
    }

    @Test
    @DisplayName("Should initialize player bets to zero")
    void testInitialPlayerBets() {
        assertEquals(0.0, gameState.getPlayerBet("Player"));
        assertEquals(0.0, gameState.getPlayerBet("Bot1"));
        assertEquals(0.0, gameState.getPlayerBet("Bot2"));
        assertEquals(0.0, gameState.getPlayerBet("Bot3"));
    }

    @Test
    @DisplayName("Should initialize player actions to false")
    void testInitialPlayerActions() {
        assertFalse(gameState.hasPlayerActed("Player"));
        assertFalse(gameState.hasPlayerActed("Bot1"));
        assertFalse(gameState.hasPlayerActed("Bot2"));
        assertFalse(gameState.hasPlayerActed("Bot3"));
    }

    @Test
    @DisplayName("Should add amount to pot correctly")
    void testAddToPot() {
        gameState.addToPot(100.0);
        assertEquals(100.0, gameState.getPot());
        
        gameState.addToPot(50.0);
        assertEquals(150.0, gameState.getPot());
        
        gameState.addToPot(25.5);
        assertEquals(175.5, gameState.getPot());
    }

    @Test
    @DisplayName("Should add zero to pot correctly")
    void testAddZeroToPot() {
        gameState.addToPot(0.0);
        assertEquals(0.0, gameState.getPot());
        
        gameState.addToPot(100.0);
        gameState.addToPot(0.0);
        assertEquals(100.0, gameState.getPot());
    }

    @Test
    @DisplayName("Should set and get game active status")
    void testSetGameActive() {
        assertTrue(gameState.isGameActive());
        
        gameState.setGameActive(false);
        assertFalse(gameState.isGameActive());
        
        gameState.setGameActive(true);
        assertTrue(gameState.isGameActive());
    }

    @Test
    @DisplayName("Should fold player correctly")
    void testFoldPlayer() {
        assertTrue(gameState.isPlayerActive("Bot1"));
        assertFalse(gameState.isPlayerFolded("Bot1"));
        
        gameState.foldPlayer("Bot1");
        
        assertFalse(gameState.isPlayerActive("Bot1"));
        assertTrue(gameState.isPlayerFolded("Bot1"));
        assertEquals(3, gameState.getActivePlayerCount());
    }

    @Test
    @DisplayName("Should fold multiple players correctly")
    void testFoldMultiplePlayers() {
        gameState.foldPlayer("Bot1");
        gameState.foldPlayer("Bot2");
        
        assertFalse(gameState.isPlayerActive("Bot1"));
        assertFalse(gameState.isPlayerActive("Bot2"));
        assertTrue(gameState.isPlayerActive("Player"));
        assertTrue(gameState.isPlayerActive("Bot3"));
        
        assertTrue(gameState.isPlayerFolded("Bot1"));
        assertTrue(gameState.isPlayerFolded("Bot2"));
        assertFalse(gameState.isPlayerFolded("Player"));
        assertFalse(gameState.isPlayerFolded("Bot3"));
        
        assertEquals(2, gameState.getActivePlayerCount());
    }

    @Test
    @DisplayName("Should track player bets correctly")
    void testSetAndGetPlayerBet() {
        gameState.setPlayerBet("Player", 100.0);
        assertEquals(100.0, gameState.getPlayerBet("Player"));
        
        gameState.setPlayerBet("Bot1", 150.0);
        assertEquals(150.0, gameState.getPlayerBet("Bot1"));
        
        // Original player bet should remain unchanged
        assertEquals(100.0, gameState.getPlayerBet("Player"));
    }

    @Test
    @DisplayName("Should update player bets")
    void testUpdatePlayerBet() {
        gameState.setPlayerBet("Player", 50.0);
        assertEquals(50.0, gameState.getPlayerBet("Player"));
        
        gameState.setPlayerBet("Player", 100.0);
        assertEquals(100.0, gameState.getPlayerBet("Player"));
    }

    @Test
    @DisplayName("Should return zero for unknown player bet")
    void testGetUnknownPlayerBet() {
        assertEquals(0.0, gameState.getPlayerBet("UnknownPlayer"));
    }

    @Test
    @DisplayName("Should track player actions correctly")
    void testSetPlayerActed() {
        assertFalse(gameState.hasPlayerActed("Player"));
        
        gameState.setPlayerActed("Player");
        assertTrue(gameState.hasPlayerActed("Player"));
        
        // Other players should remain unchanged
        assertFalse(gameState.hasPlayerActed("Bot1"));
    }

    @Test
    @DisplayName("Should return false for unknown player action")
    void testGetUnknownPlayerAction() {
        assertFalse(gameState.hasPlayerActed("UnknownPlayer"));
    }

    @Test
    @DisplayName("Should reset all player actions")
    void testResetPlayerActions() {
        gameState.setPlayerActed("Player");
        gameState.setPlayerActed("Bot1");
        gameState.setPlayerActed("Bot2");
        
        gameState.resetPlayerActions();
        
        assertFalse(gameState.hasPlayerActed("Player"));
        assertFalse(gameState.hasPlayerActed("Bot1"));
        assertFalse(gameState.hasPlayerActed("Bot2"));
        assertFalse(gameState.hasPlayerActed("Bot3"));
    }

    @Test
    @DisplayName("Should reset player actions except exempt player")
    void testResetPlayerActionsExcept() {
        gameState.setPlayerActed("Player");
        gameState.setPlayerActed("Bot1");
        gameState.setPlayerActed("Bot2");
        
        gameState.resetPlayerActionsExcept("Player");
        
        assertTrue(gameState.hasPlayerActed("Player"));
        assertFalse(gameState.hasPlayerActed("Bot1"));
        assertFalse(gameState.hasPlayerActed("Bot2"));
        assertFalse(gameState.hasPlayerActed("Bot3"));
    }

    @Test
    @DisplayName("Should update player name correctly in active players")
    void testUpdatePlayerName() {
        assertTrue(gameState.isPlayerActive("Player"));
        
        gameState.updatePlayerName("Player", "NewPlayerName");
        
        assertTrue(gameState.isPlayerActive("NewPlayerName"));
        assertFalse(gameState.isPlayerActive("Player"));
        assertEquals(4, gameState.getActivePlayerCount());
    }

    @Test
    @DisplayName("Should update player name with existing bet")
    void testUpdatePlayerNameWithBet() {
        gameState.setPlayerBet("Player", 100.0);
        
        gameState.updatePlayerName("Player", "NewPlayerName");
        
        assertEquals(100.0, gameState.getPlayerBet("NewPlayerName"));
        assertEquals(0.0, gameState.getPlayerBet("Player"));
    }

    @Test
    @DisplayName("Should update player name with existing action")
    void testUpdatePlayerNameWithAction() {
        gameState.setPlayerActed("Player");
        
        gameState.updatePlayerName("Player", "NewPlayerName");
        
        assertTrue(gameState.hasPlayerActed("NewPlayerName"));
        assertFalse(gameState.hasPlayerActed("Player"));
    }

    @Test
    @DisplayName("Should determine game is active with multiple players")
    void testGameActiveWithMultiplePlayers() {
        assertTrue(gameState.isGameActive());
        
        gameState.foldPlayer("Bot1");
        assertTrue(gameState.isGameActive()); // Still 3 players
        
        gameState.foldPlayer("Bot2");
        assertTrue(gameState.isGameActive()); // Still 2 players
    }

    @Test
    @DisplayName("Should determine game is not active with only one player")
    void testGameNotActiveWithOnePlayer() {
        gameState.foldPlayer("Bot1");
        gameState.foldPlayer("Bot2");
        gameState.foldPlayer("Bot3");
        
        assertFalse(gameState.isGameActive());
        assertEquals(1, gameState.getActivePlayerCount());
    }

    @Test
    @DisplayName("Should determine game is not active when set inactive")
    void testGameNotActiveWhenSetInactive() {
        gameState.setGameActive(false);
        assertFalse(gameState.isGameActive());
    }

    @Test
    @DisplayName("Should reset for new hand correctly")
    void testResetForNewHand() {
        // Set up some state
        gameState.addToPot(200.0);
        gameState.foldPlayer("Bot1");
        gameState.setPlayerBet("Player", 100.0);
        gameState.setPlayerActed("Player");
        gameState.setGameActive(false);
        
        gameState.resetForNewHand();
        
        // Verify reset
        assertEquals(0.0, gameState.getPot());
        assertEquals(4, gameState.getActivePlayerCount());
        assertTrue(gameState.isPlayerActive("Bot1")); // Should be active again
        assertFalse(gameState.isPlayerFolded("Bot1")); // Should not be folded
        assertEquals(0.0, gameState.getPlayerBet("Player"));
        assertFalse(gameState.hasPlayerActed("Player"));
        assertTrue(gameState.isGameActive());
    }

    @Test
    @DisplayName("Should get all player bets as map")
    void testGetAllPlayerBets() {
        gameState.setPlayerBet("Player", 100.0);
        gameState.setPlayerBet("Bot1", 150.0);
        gameState.setPlayerBet("Bot2", 75.0);
        
        Map<String, Double> allBets = gameState.getAllPlayerBets();
        
        assertEquals(100.0, allBets.get("Player"));
        assertEquals(150.0, allBets.get("Bot1"));
        assertEquals(75.0, allBets.get("Bot2"));
        assertEquals(0.0, allBets.get("Bot3"));
        assertEquals(4, allBets.size());
    }

    @Test
    @DisplayName("Should return copy of active players list")
    void testGetActivePlayersReturnsCopy() {
        List<String> activePlayers1 = gameState.getActivePlayers();
        List<String> activePlayers2 = gameState.getActivePlayers();
        
        // Should be different objects
        assertNotSame(activePlayers1, activePlayers2);
        
        // But with same content
        assertEquals(activePlayers1.size(), activePlayers2.size());
        assertTrue(activePlayers1.containsAll(activePlayers2));
    }

    @Test
    @DisplayName("Should handle folding non-existent player")
    void testFoldNonExistentPlayer() {
        int initialCount = gameState.getActivePlayerCount();
        
        gameState.foldPlayer("NonExistentPlayer");
        
        // Should not affect the count
        assertEquals(initialCount, gameState.getActivePlayerCount());
        assertTrue(gameState.isPlayerFolded("NonExistentPlayer"));
    }

    @Test
    @DisplayName("Should handle updating name of non-existent player")
    void testUpdateNonExistentPlayerName() {
        int initialCount = gameState.getActivePlayerCount();
        
        gameState.updatePlayerName("NonExistentPlayer", "NewName");
        
        // Should not affect existing players
        assertEquals(initialCount, gameState.getActivePlayerCount());
        assertFalse(gameState.isPlayerActive("NewName"));
    }
}