package com.github.zipcodewilmington.casino.games.TriviaGame;

import com.github.zipcodewilmington.casino.Player;
import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.ui.IOConsole;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Trivia implements GameInterface {
    private final IOConsole console;
    private boolean running = false;

    public Trivia(IOConsole console) {
        this.console = console;
    }

    private final List<Player> players = new ArrayList<>();
    protected final List<Question> questions = new ArrayList<>();

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

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
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
            running = false;
            return;
        }

        boolean keepPlaying = true;

        while (keepPlaying && running) {
            // Collect categories fresh every round
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

            // Filter questions by selected category fresh each round
            List<Question> filteredQuestions = new ArrayList<>();
            for (Question q : questions) {
                if (q.getCategory().equalsIgnoreCase(selectedCategory)) {
                    filteredQuestions.add(q);
                }
            }

            Collections.shuffle(filteredQuestions);

            Map<Player, Integer> scores = new HashMap<>();
            for (Player p : players) scores.put(p, 0);

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

                String answer = getInputWithTimeout("Select your answer (A, B, C, or D): ", 20).trim().toUpperCase();

                if (!answer.matches("[ABCD]")) {
                    console.println("No valid answer provided in time or invalid input. Question marked incorrect.");
                    answer = "";
                }

                if (answer.equalsIgnoreCase(q.getCorrectAnswer())) {
                    console.println("Correct!");
                    scores.put(currentPlayer, scores.get(currentPlayer) + 1);
                } else if (!answer.isEmpty()) {
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

            // Announce winner(s)
            int highestScore = Collections.max(scores.values());
            List<Player> winners = new ArrayList<>();
            for (Map.Entry<Player, Integer> entry : scores.entrySet()) {
                if (entry.getValue() == highestScore) {
                    winners.add(entry.getKey());
                }
            }

            if (winners.size() == 1) {
                console.println("\n🎉 The winner is: " + winners.get(0).getUsername() + " with a score of " + highestScore + "!");
            } else {
                console.println("\n🤝 It's a tie between:");
                for (Player p : winners) {
                    console.println("- " + p.getUsername());
                }
                console.println("Each scored: " + highestScore);
            }

            // Ask to play again
            String replayAnswer = "";
            while (true) {
                replayAnswer = console.getStringInput("Play another round? (Y/N): ").trim().toUpperCase();
                if (replayAnswer.equals("Y") || replayAnswer.equals("N")) break;
                console.println("Invalid input. Please enter Y or N.");
            }

            if (replayAnswer.equals("N")) {
                keepPlaying = false;
            }
        }

        running = false;
    }

    // Helper method for timed input
    private String getInputWithTimeout(String prompt, int timeoutSeconds) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(() -> console.getStringInput(prompt));
        try {
            return future.get(timeoutSeconds, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            console.println("\nTime is up!");
            return "";
        } catch (Exception e) {
            console.println("Error reading input: " + e.getMessage());
            return "";
        } finally {
            executor.shutdownNow();
        }
    }

    @Override
    public boolean isGamblingGame() {
        return false;
    }

    @Override
    public String getGameName() {
        return "Trivia";
    }

    @Override
    public int getMinimumBet() {
        return 0;
    }

    @Override
    public int getMaximumBet() {
        return 0;
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
        players.clear();
        if (add(player)) {
            play();
        }
    }

    public void loadQuestionsFromFile(String filename) {
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

    public void saveScore(String playerName, String category, int score) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("scores.txt", true))) {
            bw.write(playerName + " | Category: " + category + " | Score: " + score);
            bw.newLine();
        } catch (IOException e) {
            console.println("Error saving score: " + e.getMessage());
        }
    }
}
