package com.github.zipcodewilmington.casino.games.Poker;

import java.util.List;

public class HandAnalyzer {

    public int getRank(Hand hand, List<Card> communityCards) {
        if (isRoyalFlush(hand,communityCards)) {
            return 10;
        } else if (isStraightFlush(hand,communityCards)) {
            return 9;
        } else if (isFourOfAKind(hand,communityCards)) {
            return 8;
        } else if (isFullHouse(hand,communityCards)) {
            return 7;
        } else if (isFlush(hand,communityCards)) {
            return 6;
        } else if (isStraight(hand,communityCards)) {
            return 5;
        } else if (isThreeOfAKind(hand,communityCards)) {
            return 4;
        } else if (isTwoPair(hand,communityCards)) {
            return 3;
        } else if (isPair(hand,communityCards)) {
            return 2;
        } else {
            return 0; // High card
        }
    }

    public boolean isRoyalFlush(Hand hand, List<Card> communityCards) {
        // TODO: Check for royal flush (10, J, Q, K, A same suit)
        return false;
    }

    public boolean isStraightFlush(Hand hand, List<Card> communityCards) {
        // TODO: Check for straight flush
        return false;
    }

    public boolean isFourOfAKind(Hand hand, List<Card> communityCards) {
        // TODO: Check for four of a kind
        return false;
    }

    public boolean isFullHouse(Hand hand, List<Card> communityCards) {
        // TODO: Check for full house
        return false;
    }

    public boolean isFlush(Hand hand, List<Card> communityCards) {
        // TODO: Check for flush
        return false;
    }

    public boolean isStraight(Hand hand, List<Card> communityCards) {
        // TODO: Check for straight
        return false;
    }

    public boolean isThreeOfAKind(Hand hand, List<Card> communityCards) {
        // TODO: Check for three of a kind
        return false;
    }

    public boolean isTwoPair(Hand hand, List<Card> communityCards) {
        // TODO: Check for two pair
        return false;
    }

    public boolean isPair(Hand hand, List<Card> communityCards) {
        // TODO: Check for one pair
        return false;
    }

    public Hand compareHands(Hand hand1, Hand hand2) {
        // TODO: Compare two hands and return winner
        return null;
    }
}