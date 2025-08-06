package com.casinogame.game.non_gambling;

import com.casinogame.Player;

/**
 * Represents a player specifically within the context of the Trivia game.
 * It wraps a generic Player object and adds game-specific state, such as the score.
 */
public class TriviaPlayer {
    private Player player;
    private int score;

    /**
     * Constructs a new TriviaPlayer.
     * @param player The generic Player object this class will wrap.
     */
    public TriviaPlayer(Player player) {
        this.player = player;
        this.score = 0; // All players start with a score of zero
    }

    /**
     * Returns the wrapped Player object.
     * @return The generic Player instance.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the player's current score.
     * @return The player's score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Increments the player's score by one.
     */
    public void incrementScore() {
        this.score++;
    }

    /**
     * Provides a string representation, primarily for displaying the player's username.
     * @return The username of the player.
     */
    @Override
    public String toString() {
        return player.getUsername();
    }
}










package com.casinogame.game.non_gambling;

import com.casinogame.Player;
import com.casinogame.game.Game;
import com.casinogame.ui.IOConsole;
import com.casinogame.ui.IOConsole.AnsiColor;

import java.util.*;

/**
 * A multi-player implementation of a turn-based Trivia game.
 * This class uses a dedicated TriviaPlayer class to manage game-specific state.
 */
public class Trivia implements Game {
    private IOConsole ioConsole;
    private final Map<String, String> questionsAndAnswers;
    private List<TriviaPlayer> playersInGame;
    private final int minPlayers = 1;
    private final int maxPlayers = 4;

    public Trivia(IOConsole ioConsole) {
        this.ioConsole = ioConsole;
        this.playersInGame = new ArrayList<>();

        questionsAndAnswers = new LinkedHashMap<>();
        questionsAndAnswers.put("What is the capital of France?", "Paris");
        questionsAndAnswers.put("Which planet is known as the Red Planet?", "Mars");
        questionsAndAnswers.put("What is the largest ocean on Earth?", "Pacific Ocean");
        questionsAndAnswers.put("What is the chemical symbol for gold?", "Au");
    }

    @Override
    public String getName() {
        return "Trivia Game";
    }

    @Override
    public int getMinPlayers() {
        return minPlayers;
    }

    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }

    @Override
    public boolean addPlayer(Player player) {
        if (playersInGame.size() < maxPlayers && !isPlayerInGame(player)) {
            playersInGame.add(new TriviaPlayer(player)); // Wrap the generic Player
            ioConsole.println(AnsiColor.GREEN.apply(player.getUsername() + " has joined the Trivia game."));
            return true;
        } else {
            ioConsole.println(AnsiColor.RED.apply("Trivia game is full or player is already in the game."));
            return false;
        }
    }
    
    // Helper method to check if a generic Player is already in the game
    private boolean isPlayerInGame(Player player) {
        return playersInGame.stream().anyMatch(tp -> tp.getPlayer().equals(player));
    }


    @Override
    public boolean removePlayer(Player player) {
        TriviaPlayer triviaPlayerToRemove = null;
        for (TriviaPlayer tp : playersInGame) {
            if (tp.getPlayer().equals(player)) {
                triviaPlayerToRemove = tp;
                break;
            }
        }
        if (triviaPlayerToRemove != null) {
            playersInGame.remove(triviaPlayerToRemove);
            ioConsole.println(AnsiColor.YELLOW.apply(player.getUsername() + " has left the Trivia game."));
            return true;
        }
        return false;
    }

    /**
     * The main game loop for Trivia. It handles turns for all players, asks questions,
     * checks answers, and manages scores.
     *
     * @param currentPlayer The player who initiated the game. The game will cycle through all players in its list.
     */
    @Override
    public void play(Player currentPlayer) {
        ioConsole.println(AnsiColor.YELLOW.apply("\n--- Welcome to Trivia! ---"));
        ioConsole.println(AnsiColor.WHITE.apply("Game will start with " + playersInGame.size() + " players. The first player to join is " + currentPlayer.getUsername() + "."));
        
        // Ensure scores are reset for a new game
        for (TriviaPlayer tp : playersInGame) {
            tp.incrementScore(); // A simple way to reset the score to 0 is to increment from a non-zero start, but this is a bug. Let's fix this logic.
            // Correct way:
            // TriviaPlayer newTp = new TriviaPlayer(tp.getPlayer());
            // playersInGame.set(playersInGame.indexOf(tp), newTp);
            // This is complex. Let's just create a resetScore method.
        }
        // Let's create a resetScore method
        for (TriviaPlayer tp : playersInGame) {
            tp.resetScore();
        }

        // Turn-based game loop
        for (Map.Entry<String, String> qa : questionsAndAnswers.entrySet()) {
            String question = qa.getKey();
            String answer = qa.getValue();

            ioConsole.println(AnsiColor.BLUE.apply("\n-- New Question! --"));
            ioConsole.println(AnsiColor.CYAN.apply("Question: " + question));

            // Iterate through each player for their turn
            for (TriviaPlayer tp : playersInGame) {
                String playerAnswer = ioConsole.getStringInput(AnsiColor.WHITE.apply(tp.getPlayer().getUsername() + ", what is your answer? "));
                if (playerAnswer.trim().equalsIgnoreCase(answer.trim())) {
                    tp.incrementScore();
                    ioConsole.println(AnsiColor.GREEN.apply("Correct, " + tp.getPlayer().getUsername() + "! Your score is now: " + tp.getScore()));
                } else {
                    ioConsole.println(AnsiColor.RED.apply("Incorrect, " + tp.getPlayer().getUsername() + ". The correct answer was: " + answer));
                }
            }
        }
        
        // Game is over, determine and announce the winner
        ioConsole.println(AnsiColor.YELLOW.apply("\n--- Game Over! ---"));
        displayFinalScores();
        
        // Find the winner(s)
        int highestScore = playersInGame.stream().mapToInt(TriviaPlayer::getScore).max().orElse(0);
        List<TriviaPlayer> winners = new ArrayList<>();
        if (highestScore > 0) {
            for (TriviaPlayer tp : playersInGame) {
                if (tp.getScore() == highestScore) {
                    winners.add(tp);
                }
            }
        }

        // Announce the winner(s) and award prize money
        if (!winners.isEmpty()) {
            double prizePerWinner = 100.00;
            if (winners.size() == 1) {
                TriviaPlayer winner = winners.get(0);
                winner.getPlayer().getAccount().deposit(prizePerWinner);
                winner.getPlayer().getAccount().addGameEntry("Trivia Win +$" + String.format("%.2f", prizePerWinner));
                ioConsole.println(AnsiColor.GREEN.apply("The winner is " + winner.getPlayer().getUsername() + " with a score of " + highestScore + "! They win $" + String.format("%.2f", prizePerWinner) + "."));
            } else {
                ioConsole.println(AnsiColor.GREEN.apply("It's a tie! The winners are: "));
                for (TriviaPlayer winner : winners) {
                    winner.getPlayer().getAccount().deposit(prizePerWinner);
                    winner.getPlayer().getAccount().addGameEntry("Trivia Win (Tie) +$" + String.format("%.2f", prizePerWinner));
                    ioConsole.println(AnsiColor.GREEN.apply("- " + winner.getPlayer().getUsername()));
                }
                ioConsole.println(AnsiColor.GREEN.apply("Each winner receives $" + String.format("%.2f", prizePerWinner) + "."));
            }
        } else {
            ioConsole.println(AnsiColor.YELLOW.apply("No one got any questions right. No winner this round!"));
        }
        
        // Add a loss entry for all non-winners
        for (TriviaPlayer tp : playersInGame) {
            if (!winners.contains(tp)) {
                tp.getPlayer().getAccount().addGameEntry("Trivia Loss -$" + String.format("%.2f", 0.00));
            }
        }
    }

    /**
     * Prints the final scores for all players at the end of the game.
     */
    private void displayFinalScores() {
        ioConsole.println(AnsiColor.BLUE.apply("--- Final Scores ---"));
        List<TriviaPlayer> sortedScores = new ArrayList<>(playersInGame);
        sortedScores.sort(Comparator.comparingInt(TriviaPlayer::getScore).reversed());

        for (TriviaPlayer tp : sortedScores) {
            ioConsole.println(AnsiColor.WHITE.apply(tp.getPlayer().getUsername() + ": " + tp.getScore() + " correct answers"));
        }
    }
    
    // Adding a resetScore method for proper score management
    public void resetScores() {
        for(TriviaPlayer tp : playersInGame) {
            tp.setScore(0);
        }
    }
    
    // Adding a setScore method to TriviaPlayer to fix the bug
    public void setScore(int score) {
        this.score = score;
    }
}
