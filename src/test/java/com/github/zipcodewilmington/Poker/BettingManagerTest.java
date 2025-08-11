package com.github.zipcodewilmington.Poker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.github.zipcodewilmington.casino.Player;
import com.github.zipcodewilmington.casino.games.Poker.BettingManager;
import com.github.zipcodewilmington.casino.games.Poker.GameState;
import com.github.zipcodewilmington.casino.CasinoAccount;

import java.util.Arrays;
import java.util.List;

/**
 * JUnit test class for BettingManager
 */
@DisplayName("BettingManager Tests")
public class BettingManagerTest {
    
    private BettingManager bettingManager;
    
    @Mock
    private Player mockPlayer;
    
    @Mock
    private CasinoAccount mockAccount;
    
    private GameState gameState;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bettingManager = new BettingManager();
        gameState = new GameState();
        
        when(mockPlayer.getAccount()).thenReturn(mockAccount);
    }

    @Test
    @DisplayName("Should initialize with zero current bet")
    void testInitialCurrentBet() {
        assertEquals(0.0, bettingManager.getCurrentBet());
    }

    @Test
    @DisplayName("Should set and get current bet correctly")
    void testSetAndGetCurrentBet() {
        bettingManager.setCurrentBet(100.0);
        assertEquals(100.0, bettingManager.getCurrentBet());
        
        bettingManager.setCurrentBet(250.5);
        assertEquals(250.5, bettingManager.getCurrentBet());
    }

    @Test
    @DisplayName("Should return true when player can afford the bet")
    void testCanAfford_WhenPlayerHasSufficientBalance() {
        when(mockAccount.getBalance()).thenReturn(500.0);
        
        assertTrue(bettingManager.canAfford(mockPlayer, 250.0));
        assertTrue(bettingManager.canAfford(mockPlayer, 500.0));
        assertTrue(bettingManager.canAfford(mockPlayer, 100.0));
    }

    @Test
    @DisplayName("Should return true when player has exact amount")
    void testCanAfford_WhenPlayerHasExactAmount() {
        when(mockAccount.getBalance()).thenReturn(100.0);
        assertTrue(bettingManager.canAfford(mockPlayer, 100.0));
    }

    @Test
    @DisplayName("Should return false when player cannot afford the bet")
    void testCanAfford_WhenPlayerHasInsufficientBalance() {
        when(mockAccount.getBalance()).thenReturn(100.0);
        
        assertFalse(bettingManager.canAfford(mockPlayer, 250.0));
        assertFalse(bettingManager.canAfford(mockPlayer, 100.01));
    }

    @Test
    @DisplayName("Should return false when player has zero balance")
    void testCanAfford_WhenPlayerHasZeroBalance() {
        when(mockAccount.getBalance()).thenReturn(0.0);
        assertFalse(bettingManager.canAfford(mockPlayer, 50.0));
    }

    @Test
    @DisplayName("Should process bet when player can afford it")
    void testProcessBet_WhenPlayerCanAfford() {
        when(mockAccount.getBalance()).thenReturn(500.0);
        double initialPot = gameState.getPot();
        
        bettingManager.processBet(mockPlayer, 100.0, gameState);
        
        verify(mockAccount).withdraw(100.0);
        assertEquals(initialPot + 100.0, gameState.getPot());
    }

    @Test
    @DisplayName("Should not process bet when player cannot afford it")
    void testProcessBet_WhenPlayerCannotAfford() {
        when(mockAccount.getBalance()).thenReturn(50.0);
        double initialPot = gameState.getPot();
        
        bettingManager.processBet(mockPlayer, 100.0, gameState);
        
        verify(mockAccount, never()).withdraw(anyDouble());
        assertEquals(initialPot, gameState.getPot());
    }

    @Test
    @DisplayName("Should handle multiple bets from same player")
    void testProcessMultipleBets() {
        when(mockAccount.getBalance()).thenReturn(1000.0);
        double initialPot = gameState.getPot();
        
        bettingManager.processBet(mockPlayer, 100.0, gameState);
        bettingManager.processBet(mockPlayer, 50.0, gameState);
        
        verify(mockAccount).withdraw(100.0);
        verify(mockAccount).withdraw(50.0);
        assertEquals(initialPot + 150.0, gameState.getPot());
    }

    @Test
    @DisplayName("Should handle all-in correctly")
    void testHandleAllIn() {
        when(mockAccount.getBalance()).thenReturn(300.0);
        double initialPot = gameState.getPot();
        
        bettingManager.handleAllIn(mockPlayer, gameState);
        
        verify(mockAccount).withdraw(300.0);
        assertEquals(initialPot + 300.0, gameState.getPot());
    }

    @Test
    @DisplayName("Should handle all-in with zero balance")
    void testHandleAllIn_WithZeroBalance() {
        when(mockAccount.getBalance()).thenReturn(0.0);
        double initialPot = gameState.getPot();
        
        bettingManager.handleAllIn(mockPlayer, gameState);
        
        verify(mockAccount).withdraw(0.0);
        assertEquals(initialPot, gameState.getPot());
    }

    @Test
    @DisplayName("Should calculate minimum raise correctly")
    void testGetMinimumRaise() {
        bettingManager.setCurrentBet(100.0);
        double minRaise = bettingManager.getMinimumRaise(50.0);
        
        // Expected: max(50.0, 100.0 - 50.0 + 100.0) = max(50.0, 150.0) = 150.0
        assertEquals(150.0, minRaise);
    }

    @Test
    @DisplayName("Should calculate minimum raise with zero current bet")
    void testGetMinimumRaise_WithZeroCurrentBet() {
        bettingManager.setCurrentBet(0.0);
        double minRaise = bettingManager.getMinimumRaise(0.0);
        
        assertEquals(50.0, minRaise); // Should default to minimum 50.0
    }

    @Test
    @DisplayName("Should validate raise correctly - valid raises")
    void testIsValidRaise_ValidRaises() {
        bettingManager.setCurrentBet(100.0);
        
        assertTrue(bettingManager.isValidRaise(100.0, 50.0, 50.0)); // Total: 150 >= 150
        assertTrue(bettingManager.isValidRaise(150.0, 0.0, 50.0));   // Total: 150 >= 150
        assertTrue(bettingManager.isValidRaise(200.0, 25.0, 50.0));  // Total: 225 >= 150
    }

    @Test
    @DisplayName("Should validate raise correctly - invalid raises")
    void testIsValidRaise_InvalidRaises() {
        bettingManager.setCurrentBet(100.0);
        
        assertFalse(bettingManager.isValidRaise(25.0, 50.0, 50.0)); // Total: 75 < 150
        assertFalse(bettingManager.isValidRaise(40.0, 50.0, 50.0)); // Total: 90 < 150
        assertFalse(bettingManager.isValidRaise(0.0, 50.0, 50.0));  // Total: 50 < 150
    }

    @Test
    @DisplayName("Should reset current bet for new hand")
    void testResetForNewHand() {
        bettingManager.setCurrentBet(200.0);
        bettingManager.resetForNewHand();
        assertEquals(0.0, bettingManager.getCurrentBet());
    }

    @Test
    @DisplayName("Should complete betting round")
    void testIsBettingComplete() {
        List<String> activePlayers = Arrays.asList("Player1", "Player2", "Player3");
        assertTrue(bettingManager.isBettingComplete(activePlayers));
        
        List<String> singlePlayer = Arrays.asList("Player1");
        assertTrue(bettingManager.isBettingComplete(singlePlayer));
        
        List<String> noPlayers = Arrays.asList();
        assertTrue(bettingManager.isBettingComplete(noPlayers));
    }

    @Test
    @DisplayName("Should start new betting round without affecting current bet")
    void testStartNewBettingRound() {
        bettingManager.setCurrentBet(100.0);
        bettingManager.startNewBettingRound();
        
        // Current bet should remain the same (carries over between rounds)
        assertEquals(100.0, bettingManager.getCurrentBet());
    }

    @Test
    @DisplayName("Should handle edge case with negative amounts")
    void testCanAfford_WithNegativeAmount() {
        when(mockAccount.getBalance()).thenReturn(100.0);
        
        // Negative amounts should be considered affordable (edge case)
        assertTrue(bettingManager.canAfford(mockPlayer, -50.0));
    }
}