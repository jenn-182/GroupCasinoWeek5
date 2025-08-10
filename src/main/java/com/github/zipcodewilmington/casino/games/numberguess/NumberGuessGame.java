package com.github.zipcodewilmington.casino.games.Numberguess;
import com.github.zipcodewilmington.casino.ui.UIRender;

import com.github.zipcodewilmington.casino.Player;
import com.github.zipcodewilmington.casino.ui.IOConsole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by leon on 7/21/2020.
 */
public class NumberGuessGame {
    private final IOConsole console;
    private Player player;
    private final UIRender uiRender;

    public NumberGuessGame(IOConsole console) {
        this.console = console;
        this.uiRender = new UIRender();
    }

    public void launch(Player player) {
        this.player = player;
    }

    private void flushScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void play() {
        Random random = new Random();
        int numberToGuess = random.nextInt(100) + 1;
        int userGuess = 0;
        int attempts = 0;
        List<Integer> guessHistory = new ArrayList<>();

        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_MAGENTA = "\u001B[35m";
        final String ANSI_RED = "\u001B[31m";

        // Fun header
        uiRender.displayEmptyMessage("Welcome to the Number Guess Game!", ANSI_MAGENTA);
        System.out.println();

        console.println(
                ANSI_MAGENTA + "Welcome, " + (player != null ? player.getUsername() : "Player") + "!" + ANSI_RESET);
        System.out.println();
        console.println(ANSI_YELLOW + "Try to guess the number between 1 and 100." + ANSI_RESET);

        while (userGuess != numberToGuess) {
            userGuess = console.getIntegerInput("\nEnter your guess: ");
            attempts++;
            guessHistory.add(userGuess);
            flushScreen();

            if (userGuess < numberToGuess) {
                console.println(
                        "\n" + ANSI_YELLOW + "The number is higher than " + userGuess + ". Try again!" + ANSI_RESET);
            } else if (userGuess > numberToGuess) {
                console.println(
                        "\n" + ANSI_YELLOW + "The number is lower than " + userGuess + ". Try again!" + ANSI_RESET);
            } else {
                console.println(ANSI_GREEN + "\nCongratulations! You guessed the number " + numberToGuess +
                        " in " + attempts + " attempts! " + ANSI_RESET);
                if (attempts == 1) {
                    console.println(ANSI_MAGENTA + "\nNice, First try! Are you psychic?" + ANSI_RESET);
                } else if (attempts <= 5) {
                    console.println(ANSI_MAGENTA + "\nImpressive, you guessed it quickly!" + ANSI_RESET);
                } else {
                    console.println(ANSI_MAGENTA + "\nPersistence pays off!" + ANSI_RESET);
                }
                System.out.println();
                if (player != null && player.getAccount() != null) {
                    player.getAccount()
                            .addGameEntry(
                                    "\nNumber Guess - Guessed " + numberToGuess + " in " + attempts + " attempts.");
                }
            }
            console.println(ANSI_CYAN + "\nYour guesses so far: " + guessHistory + ANSI_RESET);
            if (attempts == 5) {
                if (numberToGuess % 2 == 0) {
                    console.println(ANSI_YELLOW + "\nHint: The number is even." + ANSI_RESET);
                } else {
                    console.println(ANSI_YELLOW + "\nHint: The number is odd." + ANSI_RESET);
                }
            }
        }
    }

    public void playMultiplayer(List<Player> players) {
        Random random = new Random();
        int numberToGuess = random.nextInt(100) + 1;
        boolean guessed = false;
        Map<Player, Integer> attemptsMap = new HashMap<>();
        Map<Player, List<Integer>> guessHistoryMap = new HashMap<>();

        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_MAGENTA = "\u001B[35m";
        final String ANSI_RED = "\u001B[31m";

        uiRender.displayEmptyMessage("Welcome to the Multiplayer Number Guess Game!", ANSI_MAGENTA);
        System.out.println();
        console.println(ANSI_YELLOW + "Try to guess the number between 1 and 100." + ANSI_RESET);

        for (Player p : players) {
            attemptsMap.put(p, 0);
            guessHistoryMap.put(p, new ArrayList<>());
        }

        while (!guessed) {
            for (Player p : players) {
                flushScreen();
                console.println(ANSI_MAGENTA + "It's " + p.getUsername() + "'s turn!" + ANSI_RESET);
                int userGuess = console.getIntegerInput("Enter your guess: ");
                attemptsMap.put(p, attemptsMap.get(p) + 1);
                guessHistoryMap.get(p).add(userGuess);

                if (userGuess < numberToGuess) {
                    console.println(ANSI_YELLOW + "The number is higher than " + userGuess + ". Try again!" + ANSI_RESET);
                } else if (userGuess > numberToGuess) {
                    console.println(ANSI_YELLOW + "The number is lower than " + userGuess + ". Try again!" + ANSI_RESET);
                } else {
                    console.println(ANSI_GREEN + "\nCongratulations " + p.getUsername() + "! You guessed the number " +
                            numberToGuess + " in " + attemptsMap.get(p) + " attempts!" + ANSI_RESET);
                    guessed = true;
                    if (p.getAccount() != null) {
                        p.getAccount().addGameEntry(
                            "Number Guess - Guessed " + numberToGuess + " in " + attemptsMap.get(p) + " attempts.");
                    }
                    break;
                }
                console.println(ANSI_CYAN + "Your guesses so far: " + guessHistoryMap.get(p) + ANSI_RESET);
            }
        }

        // Show all players' scores
        console.println(ANSI_YELLOW + "\n--- Final Scores ---" + ANSI_RESET);
        for (Player p : players) {
            console.println(ANSI_MAGENTA + p.getUsername() + ": " + attemptsMap.get(p) + " attempts. Guesses: " + guessHistoryMap.get(p) + ANSI_RESET);
        }
    }
}
