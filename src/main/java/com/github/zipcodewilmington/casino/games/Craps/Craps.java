package com.github.zipcodewilmington.casino.games.Craps;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.Player;

public class Craps implements GameInterface {

    private final Dice dice1 = new Dice(); // creates an instances of dice class
    private final Dice dice2 = new Dice();
    private Scanner scanner = new Scanner(System.in); // get Input from keybaord
    private final CrapsBetting betting = new CrapsBetting(); // handles all betting logic
    private List<Player> players = new ArrayList<>(); // list used to track players
    private Player currentPlayer;

    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_CYAN = "\u001B[36m";

    private void flushScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void displayCrapsBettingOptions() {
        String border = ANSI_CYAN + "═══════════════════════════════════════════════════════════════════════"
                + ANSI_RESET;
        System.out.println(border);
        System.out.println(centerText("HOW TO BET IN CRAPS", 70));
        System.out.println(border);

        System.out.println(ANSI_GREEN + "PASS LINE BET:" + ANSI_RESET);
        System.out.println("  - Win if first roll is 7 or 11.");
        System.out.println("  - Lose if first roll is 2, 3, or 12.");
        System.out.println("  - If point is set, win if point is rolled again before 7.");

        System.out.println();
        System.out.println(ANSI_RED + "DON'T PASS LINE BET:" + ANSI_RESET);
        System.out.println("  - Lose if first roll is 7 or 11.");
        System.out.println("  - Win if first roll is 2 or 3.");
        System.out.println("  - Tie if first roll is 12.");
        System.out.println("  - If point is set, win if 7 is rolled before point.");

        System.out.println(border);
    }

    // Helper for centering text
    private String centerText(String text, int width) {
        int padSize = (width - text.length()) / 2;
        String pad = " ".repeat(Math.max(0, padSize));
        return pad + text + pad;
    }

    // Method for displaying dice with ASCII art
    private void displayDice(int roll1, int roll2) {
        String[][] diceFaces = {
                { // Face 1
                        " _______ ",
                        "|       |",
                        "|   o   |",
                        "|       |",
                        "|_______|"
                },
                { // Face 2
                        " _______ ",
                        "| o     |",
                        "|       |",
                        "|     o |",
                        "|_______|"
                },
                { // Face 3
                        " _______ ",
                        "| o     |",
                        "|   o   |",
                        "|     o |",
                        "|_______|"
                },
                { // Face 4
                        " _______ ",
                        "| o   o |",
                        "|       |",
                        "| o   o |",
                        "|_______|"
                },
                { // Face 5
                        " _______ ",
                        "| o   o |",
                        "|   o   |",
                        "| o   o |",
                        "|_______|"
                },
                { // Face 6
                        " _______ ",
                        "| o   o |",
                        "| o   o |",
                        "| o   o |",
                        "|_______|"
                }
        };

        flushScreen(); // clears the screen after displaying dice

        System.out.println("The dice roll...");

        for (int i = 0; i < diceFaces[0].length; i++) {
            System.out.println(diceFaces[roll1 - 1][i] + "   " + diceFaces[roll2 - 1][i]);
        }

        System.out.println();
        System.out.println("\n" + ANSI_YELLOW + "Total: " + ANSI_RESET + (roll1 + roll2));

    }

    public void play(List<Player> players) {

        flushScreen(); // clears the screen
        displayCrapsBettingOptions(); // displays betting options

        Player shooter = chooseRandomShooter(players); // selects shooter

        for (Player player : players) { // loops through hasmap
            CrapsBetting.BetType betType;

            if (player.equals(shooter)) { // if player is shooter sets as pass
                betType = CrapsBetting.BetType.PASS_LINE;
                System.out.println();
                System.out.println(player.getUsername() + " is the shooter and automatically on PASS line.");
            } else {
                betType = askBetType(player); // the rest are asked for bet type;
            }
            double betAmount = askBetAmount(player);
            betting.placeBet(player, betType, betAmount);
            player.getAccount().withdraw(betAmount); // Deduct bet amount immediately
        }
        // first roll
        System.out.println("\n" + ANSI_CYAN + "Press Enter to roll the dice..." + ANSI_RESET);
        scanner.nextLine();
        typeWriter("Rolling the dice.......", 50); // typewriter effect for rolling dice
        flushScreen();
        int roll1 = dice1.roll();
        int roll2 = dice2.roll();
        int total = roll1 + roll2;

        System.out.println();
        displayDice(roll1, roll2); // Using the new display method
        System.out.println("\n" + ANSI_YELLOW + "Shooter rolled: " + ANSI_RESET + roll1 + " + " + roll2 + " = " + total
                + ANSI_RESET);

        FirstRollResult result = evaluateFirstRoll(total); // creates a result var to FRR type

        switch (result) {
            case WIN:
                System.out.println("\n" + ANSI_GREEN + "Result: Pass line bets WIN!" + ANSI_RESET + "\n");
                betting.payOut(true);
                break;
            case LOSE:
                System.out.println("\n" + ANSI_RED + "Result: Pass line bets LOSE!" + ANSI_RESET + "\n");
                betting.payOut(false);
                break;
            case POINT:
                System.out.println("\n" + ANSI_YELLOW + "The Point is set to: " + ANSI_RESET + total + "\n");
                playPointPhase(players, total);
                break;

        }

    }

    // other methods and functions
    // point base game
    private void playPointPhase(List<Player> players, int point) {
        int i=1;
        while (true) {
            System.out.println("Press Enter to roll the dice...");
            scanner.nextLine();
            typeWriter("Rolling the dice.......", 50);
            flushScreen();
            int roll1 = dice1.roll();
            int roll2 = dice2.roll();
            int total = roll1 + roll2;

            System.out.println();
            displayDice(roll1, roll2);
            System.out.println(
                    "\n" + ANSI_YELLOW + "Shooter rolled: " + ANSI_RESET + roll1 + " + " + roll2 + " = " + total);

            if (total == point) {
                System.out.println("\n" + ANSI_GREEN + "Point hit! Pass line wins!" + ANSI_RESET);
                betting.payOut(true);
                break;
            } else if (total == 7) {
                System.out.println("\n" + ANSI_RED + "Seven out! Don't pass wins!" + ANSI_RESET);
                betting.payOut(false);
                break;
            }
            i++;
        }
    }

    private CrapsBetting.BetType askBetType(Player player) {
        System.out.println(player.getUsername() + ", choose your bet type (pass/don't pass): ");
        String betType = scanner.nextLine().trim().toLowerCase();

        while (!betType.equals("pass") && !betType.equals("don't pass")) {
            System.out.println("Invalid input. Please enter 'pass' or 'don't pass': ");
            betType = scanner.nextLine().trim().toLowerCase();
        }

        return betType.startsWith("pass") ? CrapsBetting.BetType.PASS_LINE : CrapsBetting.BetType.DONT_PASS_LINE;
    }

    // set a random player as the shooter for a round of game
    private Player chooseRandomShooter(List<Player> players) {

        int index = new Random().nextInt(players.size());
        Player shooter = players.get(index);
        System.out.println();
        System.out.println(shooter.getUsername() + " has been selected as the shooter.");
        return shooter;

    }

    // Firstroll enmu setting
    public enum FirstRollResult { // fixed states
        WIN, LOSE, POINT
    }

    // first roll enmu switch case
    private FirstRollResult evaluateFirstRoll(int total) {
        switch (total) {
            case 7:
            case 11:
                return FirstRollResult.WIN;
            case 2:
            case 3:
            case 12:
                return FirstRollResult.LOSE;
            default:
                return FirstRollResult.POINT;
        }
    }

    // Betting Amount
    private double askBetAmount(Player player) {
        System.out.print("\n" + ANSI_GREEN + player.getUsername() + ", enter your bet amount: " + ANSI_RESET);
        while (true) {
            try {
                double amount = Double.parseDouble(scanner.nextLine());
                if (amount > 0 && amount <= player.getAccount().getBalance()) {
                    return amount;
                } else {
                    System.out.println(ANSI_RED + "Invalid amount. Enter a positive number up to your balance ("
                            + player.getAccount().getBalance() + "): " + ANSI_RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(ANSI_RED + "Invalid number. Please enter a valid bet amount: " + ANSI_RESET);
            }
        }
    }

    // get players
    public List<Player> getPlayers() {
        return players;
    }

    // interface methods
    // game name
    @Override
    public String getGameName() {
        return "Craps";
    }
    // check if gambling game

    @Override
    public boolean isGamblingGame() {
        return true;
    }
    // set minimum bet

    @Override
    public int getMinimumBet() {
        return 5;
    }
    // set maximum bet

    @Override
    public int getMaximumBet() {
        return 500;
    }
    // add player

    @Override
    public boolean add(Player player) {
        if (!players.contains(player)) {
            players.add(player);
            return true;
        }
        return false;
    }
    // return player

    @Override
    public boolean remove(Player player) {
        return players.remove(player);
    }

    // play game
    @Override
    public void play() {

        if (players.isEmpty()) {
            System.out.println("No players added. Add players before starting the game.");
            return;
        }
        play(players);
    }

    @Override
    public void run() {
        play();

    }

    @Override
    public void launch(Player player) {
        this.currentPlayer = player;
        System.out.println("Welcome to Craps, " + player.getUsername() + "!");
    }

    @Override
    public void launchMultiplayer(List<Player> players) {
        this.players = players;
        System.out.println("Welcome to multiplayer Craps!");
        play(this.players);
    }

    private void typeWriter(String text, int delayMillis) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            System.out.flush();
            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    @Override
    public void loadQuestionsFromFile(String filename) {
        // does not use questions, so this can be left empty
    }
}