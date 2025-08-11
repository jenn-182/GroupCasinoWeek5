package com.github.zipcodewilmington.casino.games.TriviaGame;

import com.github.zipcodewilmington.casino.Player;
import com.github.zipcodewilmington.casino.ui.IOConsole;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class Triviatest {

    private IOConsole mockConsole;
    private Trivia trivia;

    @BeforeEach
    void setUp() {
        mockConsole = Mockito.mock(IOConsole.class);
        trivia = new Trivia(mockConsole);
    }

    @Test
    @DisplayName("Should load questions from file successfully")
    void testLoadQuestionsFromFile() throws IOException {
        // Arrange — create temporary file with 2 questions
        File tempFile = File.createTempFile("questions", ".txt");
        tempFile.deleteOnExit();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("2\n"); // total number of questions

            // Question 1
            writer.write("General\n");
            writer.write("What is 2 + 2?\n");
            writer.write("1\n");
            writer.write("4\n");
            writer.write("3\n");
            writer.write("5\n");
            writer.write("B\n");

            // Question 2
            writer.write("Science\n");
            writer.write("What planet is known as the Red Planet?\n");
            writer.write("Earth\n");
            writer.write("Venus\n");
            writer.write("Mars\n");
            writer.write("Jupiter\n");
            writer.write("C\n");
        }

        // Act
        trivia.loadQuestionsFromFile(tempFile.getAbsolutePath());

        // Assert
        List<Question> loadedQuestions = trivia.questions;
        assertEquals(2, loadedQuestions.size(), "Should load exactly 2 questions");
        assertEquals("General", loadedQuestions.get(0).getCategory());
        assertEquals("B", loadedQuestions.get(0).getCorrectAnswer());
        assertEquals("Mars", loadedQuestions.get(1).getAnswerThree());
    }

    @Test
    @DisplayName("Should add and remove players correctly")
    void testAddAndRemovePlayers() {
        Player player = new Player("Alice", null);

        assertTrue(trivia.add(player), "Player should be added successfully");
        assertFalse(trivia.add(player), "Duplicate player should not be added");
        assertEquals(1, trivia.getPlayers().size(), "Only one player should exist");

        assertTrue(trivia.remove(player), "Player should be removed successfully");
        assertEquals(0, trivia.getPlayers().size(), "No players should remain");
    }

    @Test
    @DisplayName("Should handle missing file gracefully")
    void testLoadQuestionsFromNonExistentFile() {
        trivia.loadQuestionsFromFile("nonexistent.txt");

        assertTrue(trivia.questions.isEmpty(), "No questions should be loaded");
        verify(mockConsole).println(startsWith("Error loading questions:"));
    }
}

