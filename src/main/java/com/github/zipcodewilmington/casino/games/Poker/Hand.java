package com.github.zipcodewilmington.casino.games.Poker;

import java.util.Stack;

public class Hand {

    private Stack<Card>hand;

    Hand(){
        hand = new Stack<>();
    }

    public void receiveCard(Card card){
        hand.push(card);
    }
    public Stack<Card> returnCardsToDealer(){
        return hand;
    }
    public void printHand(){
        for (Card card : hand) {
            System.out.print(card+" | ");
        }
        System.out.println("");
    }

    public Stack<Card> gethand(){
        return hand;
    }
}
