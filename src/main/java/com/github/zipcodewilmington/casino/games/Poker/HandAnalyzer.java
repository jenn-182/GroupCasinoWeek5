package com.github.zipcodewilmington.casino.games.Poker;

import java.util.List;

public class HandAnalyzer {

    public int getRank(Hand hand, List<Card> communityCards) {
        if (isRoyalFlush(hand)) {
            return 10;
        } else if (isStraightFlush(hand)) {
            return 9;
        } else if (isFourOfAKind(hand)) {
            return 8;
        } else if (isFullHouse(hand)) {
            return 7;
        } else if (isFlush(hand)) {
            return 6;
        } else if (isStraight(hand)) {
            return 5;
        } else if (isThreeOfAKind(hand)) {
            return 4;
        } else if (isTwoPair(hand)) {
            return 3;
        } else if (isPair(hand)) {
            return 2;
        } else {
            return 0; // High card
        }
    }

    public boolean isRoyalFlush(Hand hand) {
        // TODO: Check for royal flush (10, J, Q, K, A same suit)
        return false;
    }

    public boolean isStraightFlush(Hand hand) {
        // TODO: Check for straight flush
        return false;
    }

    public boolean isFourOfAKind(Hand hand) {
        // TODO: Check for four of a kind
        return false;
    }

    public boolean isFullHouse(Hand hand) {
        // TODO: Check for full house
        return false;
    }

    public boolean isFlush(Hand hand) {
        // TODO: Check for flush
        return false;
    }

    public boolean isStraight(Hand hand) {
        // TODO: Check for straight
        return false;
    }

    public boolean isThreeOfAKind(Hand hand) {
        // TODO: Check for three of a kind
        return false;
    }

    public boolean isTwoPair(Hand hand) {
        // TODO: Check for two pair
        return false;
    }

    public boolean isPair(Hand hand) {
        // TODO: Check for one pair
        return false;
    }

    public Hand compareHands(Hand hand1, Hand hand2) {
        // TODO: Compare two hands and return winner
        return null;
    }
}