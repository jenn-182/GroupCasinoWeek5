package com.github.zipcodewilmington.casino.games.Poker;
import java.util.Collections;
import java.util.Stack;

public class Deck {

    private Stack<Card> deck;
    private static String[] suits = { "Hearts", "Diamonds", "Clubs", "Spades" };
    private static String[] ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };
    private static int[] values = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 };

    Deck() {
        deck = new Stack<>();

        int i = 0;
        for (String rank : ranks) {
            for (String suit : suits) {
                Card card = new Card(suit, rank, values[i]);
                deck.push(card);
            }
            i++;
        }

        shuffleDeck();
    }

    public void shuffleDeck(){
        Collections.shuffle(deck);
    }

    public Card dealCard(){
        return deck.pop();
    }

    public void returnCardsToDeck(Stack<Card> returnedHand){
        for (Card card : returnedHand) {
            deck.push(card);
        }
    }


}
