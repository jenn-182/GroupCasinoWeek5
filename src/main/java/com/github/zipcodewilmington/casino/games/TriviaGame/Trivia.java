package com.github.zipcodewilmington.casino.games.TriviaGame;

import com.github.zipcodewilmington.casino.Player;
import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.ui.IOConsole;
import com.github.zipcodewilmington.casino.ui.UIRender;

import java.io.*;
import java.util.*;

public class Trivia implements GameInterface {
    private final IOConsole console;
    private boolean running = false;
    private final UIRender uiRender;

    // ANSI color codes
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_MAGENTA = "\u001B[35m";
    private static final String ANSI_RESET = "\u001B[0m";

    public Trivia(IOConsole console) {
        this.console = console;
        this.uiRender = new UIRender();
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
        playWithPlayers(this.players);
    }

    // Multiplayer launch method
    public void launchMultiplayer(List<Player> multiplayerPlayers) {
        if (multiplayerPlayers == null || multiplayerPlayers.isEmpty()) {
            console.println("No players added to start the Trivia game.");
            return;
        }
        playWithPlayers(multiplayerPlayers);
    }

    // Single player launch method
    @Override
    public void launch(Player player) {
        if (player == null) {
            console.println("No player provided to launch the game.");
            return;
        }
        players.clear();
        if (add(player)) {
            playWithPlayers(this.players);
        }
    }

    // Core game logic for any list of players
    private void playWithPlayers(List<Player> activePlayers) {
        if (activePlayers == null || activePlayers.isEmpty()) {
            console.println(ANSI_RED + "No players added to start the Trivia game." + ANSI_RESET);
            return;
        }

        running = true;
        loadQuestionsFromFile("questions.txt");

        if (questions.isEmpty()) {
            console.println(ANSI_RED + "No questions loaded. Cannot start game." + ANSI_RESET);
            running = false;
            return;
        }

        boolean keepPlaying = true;

        while (keepPlaying && running) {
            // --- CATEGORY SELECTION ---
            // Collect categories fresh every round
            Set<String> categoriesSet = new TreeSet<>();
            for (Question q : questions)
                categoriesSet.add(q.getCategory());

            List<String> categories = new ArrayList<>(categoriesSet);
            uiRender.displayEmptyMessage("Welcome to the Trivia Game!", ANSI_MAGENTA);
            console.println("Please choose a category to begin!");
            console.println(ANSI_CYAN + "\nAvailable categories:" + ANSI_RESET);

            for (int i = 0; i < categories.size(); i++) {
                String color = (i % 2 == 0) ? ANSI_MAGENTA : ANSI_YELLOW;
                console.println(color + (i + 1) + ". " + categories.get(i) + ANSI_RESET);
            }

            int catChoice = -1;
            while (catChoice < 1 || catChoice > categories.size()) {
                System.out.println();
                console.print("Select category number to play: "); // Print prompt without newline
                String input = console.getStringInput("");
                try {
                    catChoice = Integer.parseInt(input);
                    if (catChoice < 1 || catChoice > categories.size()) {
                        console.println(ANSI_RED + "Invalid category number." + ANSI_RESET);
                    }
                } catch (NumberFormatException e) {
                    console.println(ANSI_RED + "Please enter a valid number." + ANSI_RESET);
                }
            }

            flushScreen();

            String selectedCategory = categories.get(catChoice - 1);
            System.out.println();
            console.println(ANSI_CYAN + "Selected category: " + ANSI_MAGENTA + selectedCategory + ANSI_RESET);

            // Filter questions by selected category fresh each round
            List<Question> filteredQuestions = new ArrayList<>();
            for (Question q : questions) {
                if (q.getCategory().equalsIgnoreCase(selectedCategory)) {
                    filteredQuestions.add(q);
                }
            }

            Collections.shuffle(filteredQuestions);

            Map<Player, Integer> scores = new HashMap<>();
            for (Player p : activePlayers)
                scores.put(p, 0);

            int totalQuestions = filteredQuestions.size();
            for (int qIndex = 0; qIndex < totalQuestions && running; qIndex++) {
                Question q = filteredQuestions.get(qIndex);
                for (Player currentPlayer : activePlayers) {

                    // Display question with color
                    console.println(
                            "\n╔════════════════════════════════════════════════════════════════════════════╗");
                    console.println(
                            "┃" + ANSI_YELLOW + "★ Question for " + ANSI_GREEN + currentPlayer.getUsername()
                                    + ANSI_YELLOW
                                    + " ★" + ANSI_RESET);
                    console.println("┃ " + ANSI_MAGENTA + q.getQuestion() + ANSI_RESET);
                    console.println(
                            "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
                    console.println("┃ " + ANSI_CYAN + "A) " + ANSI_RESET + q.getAnswerOne());
                    console.println("┃ " + ANSI_CYAN + "B) " + ANSI_RESET + q.getAnswerTwo());
                    console.println("┃ " + ANSI_CYAN + "C) " + ANSI_RESET + q.getAnswerThree());
                    console.println("┃ " + ANSI_CYAN + "D) " + ANSI_RESET + q.getAnswerFour());
                    console.println(
                            "╚════════════════════════════════════════════════════════════════════════════╝");

                    String answer = console
                            .getStringInput(ANSI_YELLOW + "Select your answer (A, B, C, or D): " + ANSI_RESET).trim()
                            .toUpperCase();
                    console.print("");

                    if (!answer.matches("[ABCD]")) {
                        console.println(
                                ANSI_RED + "No valid answer provided or invalid input. Question marked incorrect."
                                        + ANSI_RESET);
                        answer = "";
                    }

                    System.out.println();
                    if (answer.equalsIgnoreCase(q.getCorrectAnswer())) {
                        console.println(ANSI_GREEN + "Correct!" + ANSI_RESET);
                        scores.put(currentPlayer, scores.get(currentPlayer) + 1);
                    } else if (!answer.isEmpty()) {
                        console.println(
                                ANSI_RED + "Incorrect! Correct answer was: " + ANSI_YELLOW + q.getCorrectAnswer()
                                        + ANSI_RESET);
                    }

                    console.println(ANSI_CYAN + "Current Score for " + currentPlayer.getUsername() + ": " + ANSI_MAGENTA
                            + scores.get(currentPlayer) + ANSI_RESET);

                    System.out.println();
                    console.print(ANSI_YELLOW + "Press ENTER to view the next question..." + ANSI_RESET);
                    console.getStringInput(""); // Wait for user to press ENTER

                    flushScreen();
                }
            }

            // --- LEADERBOARD & WINNER ---
            displayLeaderboard(activePlayers, scores, totalQuestions, selectedCategory);

            int highestScore = Collections.max(scores.values());
            List<Player> winners = new ArrayList<>();
            for (Map.Entry<Player, Integer> entry : scores.entrySet()) {
                if (entry.getValue() == highestScore) {
                    winners.add(entry.getKey());
                }
            }

            if (winners.size() == 1) {
                console.println(
                        ANSI_GREEN + "\nThe winner is: " + winners.get(0).getUsername() + " with a score of "
                                + highestScore + "!" + ANSI_RESET);
            } else {
                console.println(ANSI_YELLOW + "\nIt's a tie between:" + ANSI_RESET);
                for (Player p : winners) {
                    console.println(ANSI_MAGENTA + "- " + p.getUsername() + ANSI_RESET);
                }
                console.println(ANSI_YELLOW + "Each scored: " + highestScore + ANSI_RESET);
            }

            // --- PLAY AGAIN PROMPT ---
            String replayAnswer = "";
            while (true) {
                System.out.println();
                replayAnswer = console.getStringInput(ANSI_YELLOW + "Play another round? (Y/N): " + ANSI_RESET)
                        .trim()
                        .toUpperCase();
                if (replayAnswer.equals("Y") || replayAnswer.equals("N")) {
                    flushScreen();
                    break;
                }
                console.println(ANSI_RED + "Invalid input. Please enter Y or N." + ANSI_RESET);
            }

            if (replayAnswer.equals("N")) {
                keepPlaying = false;
            }
        }

        running = false;
    }

    // Leaderboard display
    private void displayLeaderboard(List<Player> activePlayers, Map<Player, Integer> scores, int totalQuestions,
            String selectedCategory) {
        System.out.println(ANSI_YELLOW + "\n=== Leaderboard ===" + ANSI_RESET);
        System.out.println("+----------------------+--------+");
        System.out.println("| Player               | Score  |");
        System.out.println("+----------------------+--------+");
        for (Player p : activePlayers) {
            int score = scores.getOrDefault(p, 0);
            saveScore(p.getUsername(), selectedCategory, score);
            System.out.printf("| %-20s | %-6d |\n", p.getUsername(), score);
        }
        System.out.println("+----------------------+--------+");
        System.out.println(ANSI_MAGENTA + "Max possible: " + totalQuestions + ANSI_RESET);
        System.out.println();
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

    public void saveScore(String playerName, String category, int score) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("scores.txt", true))) {
            bw.write(playerName + " | Category: " + category + " | Score: " + score);
            bw.newLine();
        } catch (IOException e) {
            console.println("Error saving score: " + e.getMessage());
        }
    }

    private void flushScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
