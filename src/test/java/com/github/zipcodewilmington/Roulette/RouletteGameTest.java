package com.github.zipcodewilmington.Roulette;  

import com.github.zipcodewilmington.casino.Player;
import com.github.zipcodewilmington.Roulette.RouletteGame;
import com.github.zipcodewilmington.casino.CasinoAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;

public class RouletteGameTest {
    
    private RouletteGame game;
    private Player testPlayer;
    private CasinoAccount testAccount;

    @BeforeEach
    void setUp() {
        // Create test player with account
        testAccount = new CasinoAccount(1000.0);  // ✅ SIMPLIFIED
        testPlayer = new Player("TestPlayer", testAccount);
        
        // Create game instance
        game = new RouletteGame();
    }

    @Test
    @DisplayName("Test RouletteGame Constructor")
    void testDefaultConstructor() {
        RouletteGame newGame = new RouletteGame();
        assertNotNull(newGame);
    }

    @Test
    @DisplayName("Test getCurrentBalance Method")
    void testGetCurrentBalance() {
        // This should work if the method exists
        double balance = game.getCurrentBalance();
        assertTrue(balance >= 0);
    }

    @Test
    @DisplayName("Test Game Interface Methods")
    void testGameInterfaceMethods() {
        assertTrue(game.isGamblingGame());
        assertEquals("Roulette", game.getGameName());
        assertEquals(10, game.getMinimumBet());
        assertEquals(5000, game.getMaximumBet());
    }

    @Test
    @DisplayName("Test Player Management")
    void testPlayerManagement() {
        boolean added = game.add(testPlayer);
        assertTrue(added);
        
        boolean removed = game.remove(testPlayer);
        assertTrue(removed);
    }

    @Test
    @DisplayName("Test Multiplayer Setup - Insufficient Players")
    void testMultiplayerInsufficientPlayers() {
        List<Player> players = new ArrayList<>();
        players.add(testPlayer);
        
        // Capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        game.launchMultiplayer(players);
        
        System.setOut(originalOut);
        String output = outputStream.toString();
        assertTrue(output.contains("Need at least 2"));
    }

    @Test
    @DisplayName("Test Multiplayer Setup - Valid Players")
    void testMultiplayerValidPlayers() {
        List<Player> players = new ArrayList<>();
        players.add(testPlayer);
        
        // Create second player
        CasinoAccount account2 = new CasinoAccount(500.0);
        Player player2 = new Player("Player2", account2);
        players.add(player2);
        
        // Mock input to exit immediately
        String input = "done\ndone\nn\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        // Capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        game.launchMultiplayer(players);
        
        System.setOut(originalOut);
        String output = outputStream.toString();
        assertTrue(output.contains("The more, the merrier!") || output.contains("MULTIPLAYER"));
    }

    // Simple integration test
    @Test
    @DisplayName("Integration Test - Basic Game Flow")
    void testBasicGameFlow() {
        // Test that we can add a player and get their balance
        game.add(testPlayer);
        double balance = game.getCurrentBalance();
        assertTrue(balance > 0);
        
        // Test game properties
        assertEquals("Roulette", game.getGameName());
        assertTrue(game.isGamblingGame());
    }
}