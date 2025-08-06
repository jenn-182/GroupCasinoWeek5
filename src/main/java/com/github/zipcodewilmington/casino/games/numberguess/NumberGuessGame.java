package com.github.zipcodewilmington.casino.games.Numberguess;

Package games

import java.util.Random;
import java.util.Scanner;




/**
 * Created by leon on 7/21/2020.
 */
public class NumberGuessGame {
    public static void Game(String[] args) throws Exception { 
    //Initialize  scanner for user input and random generator for target number 
    Scanner scanner = new Scanner (System.in);
    Random random = new Random();
    
    
    // Generate a random number between 1 and 100 that the user needs to guess
    int numberToGuess = random.nextInt (100) + 1;
    int userGuess = 0;
    int attempts = 0;


    // Introductory message for the game
    System.out.println("Welcome to Number Guessing Game!");
    System.out.println("Try to guess the number between 1 and 100.");

    // Main game loop: continues until the user guesses the correct number
    while(userGuess != numberToGuess) {
        System.out.print("Enter your guess: ");
        userGuess = scanner.nextInt(); // Reads user's guess
        attempts++;  //Increment attempt count with each guess

        // Provide feedback if the guess is lower or higher than the target
        if (userGuess < numberToGuess) {
            System.out.print("The number is higer than " + userGuess + ". Try again.");
        }  else if (userGuess > numberToGuess) {
            System.out.println("The number is lower than " + userGuess +". Try again.");
        } else {
            // Congratulatory message when the correct guess is made
            System.out.println("Congradulations! you guessed the number " + numberToGuess + " in " + attempts + " attempts.");
          
            }
        }
    }
}

