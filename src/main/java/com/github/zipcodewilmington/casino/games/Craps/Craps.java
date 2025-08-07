package com.github.zipcodewilmington.casino.games.Craps;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.Player;
import com.github.zipcodewilmington.casino.games.Craps.CrapsBetting.BetType;

public class Craps implements GameInterface {

    private final Dice dice1 = new Dice();                      // creates an instances of dice class
    private final Dice dice2 = new Dice();
    private Scanner scanner = new Scanner(System.in);           // get Input from keybaord
    private final CrapsBetting betting = new CrapsBetting(); // handles all betting logic
    private List<Player> players = new ArrayList<>();       // list used to track players

    public void play(List<Player> players) {
        System.out.println("Welcome to Craps!!!");

        Player shooter = chooseRandomShooter(players);          // selects shooter 

        for (Player player : players) {                         // loops through hasmap
            BetType betType;

            if (player.equals(shooter)) {                       // if player is shooter sets as pass 
                betType = CrapsBetting.BetType.PASS_LINE;
                System.out.println(player.getUsername() + " is the shooter and automatically on PASS line.");
            } else {
                betType = askBetType(player);                // the rest are asked for bet type;
            }
            double betAmount = askBetAmount(player);
            betting.placeBet(player, betType, betAmount);

        }
        // first roll 
        int roll1 = dice1.roll();
        int roll2 = dice2.roll();
        int total = roll1 + roll2;
        System.out.println("Shooter rolled: " + roll1 + " + " + roll2 + " = " + total);

        FirstRollResult result = evaluateFirstRoll(total);             //creates a result var to FRR type

        switch (result) {
            case WIN:
                System.out.println("Result: Pass line bets WIN!");
                betting.payOut(true);            // might have to create a bet class
                break;
            case LOSE:
                System.out.println("Result: Pass line bets LOSE!");
                betting.payOut(false);
                break;
            case POINT:
                System.out.println("The Point is set to: " + total);
                playPointPhase(players, total);
                break;

        }

    }

    
// other methods and functions
    // point base game
    private void playPointPhase(List<Player> players, int point) {
        while (true) {
            int roll1 = dice1.roll();
            int roll2 = dice2.roll();
            int total = roll1 + roll2;
            System.out.println("Shooter rolled: " + roll1 + " + " + roll2 + " = " + total);

            if (total == point) {
                System.out.println("Point hit! Pass line wins!");
                betting.payOut(true);
                break;
            } else if (total == 7) {
                System.out.println("Seven out! Don't pass wins!");
                betting.payOut(false);
                break;
            }
        }
    }

    private BetType askBetType(Player player) {
        System.out.println("player.getName()" + ", choose your bet type (pass/don't pass): ");
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
        System.out.println(shooter.getUsername() + " has been selected as the shooter.");
        return shooter;

    }

    // Firstroll enmu setting
    public enum FirstRollResult {          // fixed states
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
        System.out.println(player.getUsername() + ", enter your bet amount: ");
        while (true) {
            try {
                double amount = Double.parseDouble(scanner.nextLine());
                if (amount > 0 && amount <= player.getAccount().getBalance()) {
                    return amount;
                } else {
                    System.out.println("Invalid amount. Enter a positive number up to your balance ("
                            + player.getAccount().getBalance() + "): ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid bet amount: ");
            }
        }
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

}
