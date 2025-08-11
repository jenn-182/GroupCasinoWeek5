package com.github.zipcodewilmington.casino.games.TriviaGame;

import com.github.zipcodewilmington.casino.Player;
import com.github.zipcodewilmington.casino.ui.IOConsole;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class Triviatest {
    private IOConsole mockConsole;
    private Trivia trivia;
    private Player mockPlayer1;
    private Player mockPlayer2;

    @Before
    public void setUp() {
        mockConsole = mock(IOConsole.class);

        // Subclass Trivia to override loadQuestionsFromFile for testing
        trivia = new Trivia(mockConsole) {
            @Override
            public void loadQuestionsFromFile(String filename) {
                // Instead of loading from file, inject test questions directly
                questions.clear();
                questions.add(new Question(
                    "Science",
                    "What is the boiling point of water?",
                    "90°C",
                    "100°C",
                    "110°C",
                    "120°C",
                    "B"
                ));
                questions.add(new Question(
                    "Science",
                    "What planet is known as the Red Planet?",
                    "Earth",
                    "Mars",
                    "Jupiter",
                    "Saturn",
                    "B"
                ));
            }
        };

        mockPlayer1 = mock(Player.class);
        when(mockPlayer1.getUsername()).thenReturn("Alice");

        mockPlayer2 = mock(Player.class);
        when(mockPlayer2.getUsername()).thenReturn("Bob");
    }

    @Test
    public void testAddPlayersAndPlaySingleRound() {
        // Add players
        assertTrue(trivia.add(mockPlayer1));
        assertTrue(trivia.add(mockPlayer2));

        // Simulate inputs in order:
        // 1) Category selection: select 1 (Science)
        // 2) Answers for 2 questions (players alternate)
        // 3) Play again prompt: N (no)

        when(mockConsole.getStringInput("Select category number to play: ")).thenReturn("1");
        when(mockConsole.getStringInput("Select your answer (A, B, C, or D): "))
                .thenReturn("B")  // Alice answers 1st question correctly
                .thenReturn("A")  // Bob answers 2nd question incorrectly
        ;
        when(mockConsole.getStringInput("Play another round? (Y/N): ")).thenReturn("N");

        // Run the play method (which includes entire round)
        trivia.play();

        // Verify console outputs for final scores and winner announcement
        verify(mockConsole).println("\n=== Final Scores ===");
        verify(mockConsole).println("Alice: 1 / 2");
        verify(mockConsole).println("Bob: 0 / 2");
        verify(mockConsole).println("\n🎉 The winner is: Alice with a score of 1!");

        // Verify prompt for replay was called
        verify(mockConsole).getStringInput("Play another round? (Y/N): ");
    }

    @Test
    public void testPlayWithNoPlayers() {
        // No players added, play should print warning and return
        trivia.play();
        verify(mockConsole).println("No players added to start the Trivia game.");
    }

    @Test
    public void testAddMoreThanTwoPlayersRejected() {
        Player p3 = mock(Player.class);
        when(p3.getUsername()).thenReturn("Charlie");

        assertTrue(trivia.add(mockPlayer1));
        assertTrue(trivia.add(mockPlayer2));
        assertFalse(trivia.add(p3));

        verify(mockConsole).println("Cannot add more than 2 players.");
    }
}
