package com.github.zipcodewilmington.casino.games.TriviaGame;

import com.github.zipcodewilmington.casino.Player;
import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.ui.IOConsole;

import java.io.*;
import java.util.*;

public class Trivia implements GameInterface {
    private final IOConsole console;
    private boolean running = false;

    public Trivia(IOConsole console) {
        this.console = console;
    }

    private final List<Player> players = new ArrayList<>();
    private final List<Question> questions = new ArrayList<>();

    @Override
    public boolean add(Player player) {
        if (players.size() >= 2) {
            console.println("Cannot add more than 2 players.");
            return false;
        }
        if (players.contains(player)) {
            console.println("Player already added: " + player.getUsername());
            return false;
        }
        players.add(player);
        return true;
    }

    @Override
    public boolean remove(Player player) {
        return players.remove(player);
    }

    @Override
    public void play() {
        if (players.isEmpty()) {
            console.println("No players added to start the Trivia game.");
            return;
        }

        running = true;
        loadQuestionsFromFile("questions.txt");

        if (questions.isEmpty()) {
            console.println("No questions loaded. Cannot start game.");
            return;
        }

        Set<String> categoriesSet = new TreeSet<>();
        for (Question q : questions) categoriesSet.add(q.getCategory());

        List<String> categories = new ArrayList<>(categoriesSet);
        console.println("\nAvailable categories:");
        for (int i = 0; i < categories.size(); i++) {
            console.println((i + 1) + ". " + categories.get(i));
        }

        int catChoice = -1;
        while (catChoice < 1 || catChoice > categories.size()) {
            String input = console.getStringInput("Select category number to play: ");
            try {
                catChoice = Integer.parseInt(input);
                if (catChoice < 1 || catChoice > categories.size()) {
                    console.println("Invalid category number.");
                }
            } catch (NumberFormatException e) {
                console.println("Please enter a valid number.");
            }
        }

        String selectedCategory = categories.get(catChoice - 1);
        console.println("Selected category: " + selectedCategory);

        // Filter questions by selected category
        List<Question> filteredQuestions = new ArrayList<>();
        for (Question q : questions) {
            if (q.getCategory().equalsIgnoreCase(selectedCategory)) {
                filteredQuestions.add(q);
            }
        }

        Collections.shuffle(filteredQuestions);

        // Scores map for players
        Map<Player, Integer> scores = new HashMap<>();
        for (Player p : players) scores.put(p, 0);

        // Ask questions, alternating players
        int totalQuestions = filteredQuestions.size();
        for (int i = 0; i < totalQuestions && running; i++) {
            Player currentPlayer = players.get(i % players.size());
            Question q = filteredQuestions.get(i);

            console.println("\nQuestion for " + currentPlayer.getUsername() + ":");
            console.println(q.getQuestion());
            console.println("A) " + q.getAnswerOne());
            console.println("B) " + q.getAnswerTwo());
            console.println("C) " + q.getAnswerThree());
            console.println("D) " + q.getAnswerFour());

            String answer = "";
            while (true) {
                answer = console.getStringInput("Select your answer (A, B, C, or D): ").trim().toUpperCase();
                if (answer.matches("[ABCD]")) break;
                console.println("Invalid choice. Please enter A, B, C, or D.");
            }

            if (answer.equalsIgnoreCase(q.getCorrectAnswer())) {
                console.println("Correct!");
                scores.put(currentPlayer, scores.get(currentPlayer) + 1);
            } else {
                console.println("Incorrect! Correct answer was: " + q.getCorrectAnswer());
            }

            console.println("Current Score for " + currentPlayer.getUsername() + ": " + scores.get(currentPlayer));
        }

        console.println("\n=== Final Scores ===");
        for (Player p : players) {
            int score = scores.getOrDefault(p, 0);
            console.println(p.getUsername() + ": " + score + " / " + totalQuestions);
            saveScore(p.getUsername(), selectedCategory, score);
        }

        running = false;
    }

    @Override
    public boolean isGamblingGame() {
        return false; // Trivia is non-gambling
    }

    @Override
    public String getGameName() {
        return "Trivia";
    }

    @Override
    public int getMinimumBet() {
        return 0; // no bets for trivia
    }

    @Override
    public int getMaximumBet() {
        return 0; // no bets for trivia
    }

    @Override
    public void run() {
        play();
    }

    @Override
    public void launch(Player player) {
        if (player == null) {
            console.println("No player provided to launch the game.");
            return;
        }
        if (!add(player)) {
            console.println("Failed to add player: " + player.getUsername());
            return;
        }
        play();
    }

    private void loadQuestionsFromFile(String filename) {
        questions.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            int numQuestions = Integer.parseInt(line.trim());

            for (int i = 0; i < numQuestions; i++) {
                String category = br.readLine();
                String question = br.readLine();
                String a = br.readLine();
                String b = br.readLine();
                String c = br.readLine();
                String d = br.readLine();
                String correct = br.readLine().trim().toUpperCase();

                questions.add(new Question(category, question, a, b, c, d, correct));
            }
        } catch (IOException | NumberFormatException e) {
            console.println("Error loading questions: " + e.getMessage());
        }
    }

    private void saveScore(String playerName, String category, int score) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("scores.txt", true))) {
            bw.write(playerName + " | Category: " + category + " | Score: " + score);
            bw.newLine();
        } catch (IOException e) {
            console.println("Error saving score: " + e.getMessage());
        }
    }
}

