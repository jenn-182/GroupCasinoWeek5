package com.github.zipcodewilmington.casino.games.Craps;

import com.github.zipcodewilmington.casino.games.Craps.Dice;  // need to import each class to make it work

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import com.github.zipcodewilmington.casino.Player;

public class Craps {

    private final Dice dice1 = new Dice();                      // creates an instances of dice class
    private final Dice dice2 = new Dice();
    private Scanner scanner = new Scanner(System.in);           // get Input from keybaord
    private Map<Player, String> playerBets = new HashMap<>();   // hashmap to store player name and bets type

    public void play(List<Player> players) {
        System.out.println("Welcome to Craps!!!");

        Dice dice1 = new Dice();
        Dice dice2 = new Dice();

        // select the shooter and takes betstype
        Player shooter = chooseRandomShooter(players);          // selects shooter 
        playerBets.put(player, betType(shooter));               //saves shooter in hashmap
        for (Player player : players) {                         // loops through hasmap
            if (player.equals(shooter)) {                       // if player is shooter sets as pass 
                playerBets.put(player, "pass");
            } else {
                String betType = betType(player);                // the rest are asked for bet type
                playerBets.put(player, betType);
            }
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
                payOutBets(players, bets, true);            // might have to create a bet class
                break;
            case LOSE:
                System.out.println("Result: Pass line bets LOSE!");
                payOutBets(players, bets, false);
                break;
            case POINT:
                System.out.println("Point established: " + total);
                playPointPhase(players, bets, total, dice1, dice2);
                break;

        }
    }

    // point base game
    private void playPointPhase(List<Player> players, Map<Player, String> bets, int point, Dice dice1, Dice dice2) {
        while (true) {
            int roll1 = dice1.roll();
            int roll2 = dice2.roll();
            int total = roll1 + roll2;
            System.out.println("Shooter rolled: " + roll1 + " + " + roll2 + " = " + total);

            if (total == point) {
                System.out.println("Point hit! Pass line wins!");
                payOutBets(players, bets, true);
                break;
            } else if (total == 7) {
                System.out.println("Seven out! Don't pass wins!");
                payOutBets(players, bets, false);
                break;
            }
        }
    }

    // Pass line and Don't pass line swtich case
    private void payOutBets(List<Player> players, Map<Player, String> bets, boolean passWins) {
        for (Player player : players) {
            String betType = bets.get(player);
            if ((passWins && betType.equals("pass")) || (!passWins && betType.equals("dont"))) {
                System.out.println(player.getName() + " wins the bet!");
                player.addBalance(10); // payout logic here
            } else {
                System.out.println(player.getName() + " loses the bet.");
                player.subtractBalance(10); // losing logic here
            }
        }
    }
    // function to ask if the player wants to bet on PASS or DONT PASS line

    private String betType(Player player) {
        System.out.println("player.getName()" + ", choose your bet type (pass/don't pass): ");
        String betType = scanner.nextLine().trim().toLowerCase();

        while (!betType.equals("pass") && !betType.equals("don't pass")) {
            System.out.println("Invalid input. Please enter 'pass' or 'don't pass': ");
            betType = scanner.nextLine().trim().toLowerCase();
        }

        return betType;
    }

    // set a random player as the shooter for a round of game
    private Player chooseRandomShooter(List<Player> players) {

        int index = new Random().nextInt(players.size());
        Player shooter = players.get(index);
        System.out.println("shooter.getName()" + " has been selected as the shooter.");
        return shooter;

    }

    // Firstroll enmu setting
    public enum FirstRollResult {          // fixed states
        WIN, LOSE, POINT
    }

    // fisrt roll enmu switch case
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

}
