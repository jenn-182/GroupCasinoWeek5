package com.github.zipcodewilmington.Poker;

import org.junit.jupiter.api.Test;

import com.github.zipcodewilmington.casino.games.Poker.Card;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for Card
 */
@DisplayName("Card Tests")
public class CardTest {

    @Test
    @DisplayName("Should create card with correct properties")
    void testCardCreation() {
        Card card = new Card("H", "A", 14);
        
        assertEquals("H", card.getSuit());
        assertEquals("A", card.getRank());
        assertEquals(14, card.getValue());
    }

    @Test
    @DisplayName("Should create cards with different suits - Hearts")
    void testHeartsSuit() {
        Card hearts = new Card("H", "K", 13);
        assertEquals("H", hearts.getSuit());
        assertEquals("K", hearts.getRank());
        assertEquals(13, hearts.getValue());
    }

    @Test
    @DisplayName("Should create cards with different suits - Diamonds")
    void testDiamondsSuit() {
        Card diamonds = new Card("D", "Q", 12);
        assertEquals("D", diamonds.getSuit());
        assertEquals("Q", diamonds.getRank());
        assertEquals(12, diamonds.getValue());
    }

    @Test
    @DisplayName("Should create cards with different suits - Clubs")
    void testClubsSuit() {
        Card clubs = new Card("C", "J", 11);
        assertEquals("C", clubs.getSuit());
        assertEquals("J", clubs.getRank());
        assertEquals(11, clubs.getValue());
    }

    @Test
    @DisplayName("Should create cards with different suits - Spades")
    void testSpadesSuit() {
        Card spades = new Card("S", "10", 10);
        assertEquals("S", spades.getSuit());
        assertEquals("10", spades.getRank());
        assertEquals(10, spades.getValue());
    }

    @Test
    @DisplayName("Should create cards with number ranks")
    void testNumberRanks() {
        Card two = new Card("H", "2", 2);
        Card three = new Card("D", "3", 3);
        Card four = new Card("C", "4", 4);
        Card five = new Card("S", "5", 5);
        Card six = new Card("H", "6", 6);
        Card seven = new Card("D", "7", 7);
        Card eight = new Card("C", "8", 8);
        Card nine = new Card("S", "9", 9);
        Card ten = new Card("H", "10", 10);
        
        assertEquals("2", two.getRank());
        assertEquals("3", three.getRank());
        assertEquals("4", four.getRank());
        assertEquals("5", five.getRank());
        assertEquals("6", six.getRank());
        assertEquals("7", seven.getRank());
        assertEquals("8", eight.getRank());
        assertEquals("9", nine.getRank());
        assertEquals("10", ten.getRank());
    }

    @Test
    @DisplayName("Should create cards with face card ranks")
    void testFaceCardRanks() {
        Card jack = new Card("H", "J", 11);
        Card queen = new Card("D", "Q", 12);
        Card king = new Card("C", "K", 13);
        Card ace = new Card("S", "A", 14);
        
        assertEquals("J", jack.getRank());
        assertEquals("Q", queen.getRank());
        assertEquals("K", king.getRank());
        assertEquals("A", ace.getRank());
    }

    @Test
    @DisplayName("Should create cards with correct values - Low cards")
    void testLowCardValues() {
        Card two = new Card("H", "2", 2);
        Card three = new Card("D", "3", 3);
        Card four = new Card("C", "4", 4);
        Card five = new Card("S", "5", 5);
        
        assertEquals(2, two.getValue());
        assertEquals(3, three.getValue());
        assertEquals(4, four.getValue());
        assertEquals(5, five.getValue());
    }

    @Test
    @DisplayName("Should create cards with correct values - Middle cards")
    void testMiddleCardValues() {
        Card six = new Card("H", "6", 6);
        Card seven = new Card("D", "7", 7);
        Card eight = new Card("C", "8", 8);
        Card nine = new Card("S", "9", 9);
        Card ten = new Card("H", "10", 10);
        
        assertEquals(6, six.getValue());
        assertEquals(7, seven.getValue());
        assertEquals(8, eight.getValue());
        assertEquals(9, nine.getValue());
        assertEquals(10, ten.getValue());
    }

    @Test
    @DisplayName("Should create cards with correct values - Face cards")
    void testFaceCardValues() {
        Card jack = new Card("D", "J", 11);
        Card queen = new Card("C", "Q", 12);
        Card king = new Card("S", "K", 13);
        Card ace = new Card("H", "A", 14);
        
        assertEquals(11, jack.getValue());
        assertEquals(12, queen.getValue());
        assertEquals(13, king.getValue());
        assertEquals(14, ace.getValue());
    }

    @Test
    @DisplayName("Should have proper string representation")
    void testToString() {
        Card aceOfHearts = new Card("H", "A", 14);
        Card kingOfSpades = new Card("S", "K", 13);
        Card twoOfDiamonds = new Card("D", "2", 2);
        
        String aceString = aceOfHearts.toString();
        String kingString = kingOfSpades.toString();
        String twoString = twoOfDiamonds.toString();
        
        assertTrue(aceString.contains("A"));
        assertTrue(aceString.contains("H"));
        
        assertTrue(kingString.contains("K"));
        assertTrue(kingString.contains("S"));
        
        assertTrue(twoString.contains("2"));
        assertTrue(twoString.contains("D"));
    }

    @Test
    @DisplayName("Should create identical cards with same properties")
    void testIdenticalCards() {
        Card card1 = new Card("H", "A", 14);
        Card card2 = new Card("H", "A", 14);
        
        assertEquals(card1.getSuit(), card2.getSuit());
        assertEquals(card1.getRank(), card2.getRank());
        assertEquals(card1.getValue(), card2.getValue());
    }

    @Test
    @DisplayName("Should handle different card combinations")
    void testDifferentCardCombinations() {
        Card aceOfHearts = new Card("H", "A", 14);
        Card aceOfSpades = new Card("S", "A", 14);
        Card kingOfHearts = new Card("H", "K", 13);
        
        // Same rank, different suits
        assertEquals(aceOfHearts.getRank(), aceOfSpades.getRank());
        assertEquals(aceOfHearts.getValue(), aceOfSpades.getValue());
        assertNotEquals(aceOfHearts.getSuit(), aceOfSpades.getSuit());
        
        // Same suit, different ranks
        assertEquals(aceOfHearts.getSuit(), kingOfHearts.getSuit());
        assertNotEquals(aceOfHearts.getRank(), kingOfHearts.getRank());
        assertNotEquals(aceOfHearts.getValue(), kingOfHearts.getValue());
    }

    @Test
    @DisplayName("Should maintain suit consistency")
    void testSuitConsistency() {
        String[] suits = {"H", "D", "C", "S"};
        String[] ranks = {"2", "J", "Q", "K", "A"};
        int[] values = {2, 11, 12, 13, 14};
        
        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < ranks.length; j++) {
                Card card = new Card(suits[i], ranks[j], values[j]);
                assertEquals(suits[i], card.getSuit());
                assertEquals(ranks[j], card.getRank());
                assertEquals(values[j], card.getValue());
            }
        }
    }

    @Test
    @DisplayName("Should handle edge case values")
    void testEdgeCaseValues() {
        Card lowCard = new Card("H", "2", 2);
        Card highCard = new Card("S", "A", 14);
        
        assertEquals(2, lowCard.getValue());
        assertEquals(14, highCard.getValue());
        
        assertTrue(lowCard.getValue() < highCard.getValue());
    }

    @Test
    @DisplayName("Should not be null after creation")
    void testCardNotNull() {
        Card card = new Card("H", "A", 14);
        
        assertNotNull(card);
        assertNotNull(card.getSuit());
        assertNotNull(card.getRank());
    }

    @Test
    @DisplayName("Should create cards with string representation containing both rank and suit")
    void testStringRepresentationContainsBothElements() {
        Card[] testCards = {
            new Card("H", "A", 14),
            new Card("D", "K", 13),
            new Card("C", "Q", 12),
            new Card("S", "J", 11),
            new Card("H", "10", 10),
            new Card("D", "2", 2)
        };
        
        for (Card card : testCards) {
            String cardString = card.toString();
            assertTrue(cardString.contains(card.getRank()), 
                "Card string should contain rank: " + card.getRank());
            assertTrue(cardString.contains(card.getSuit()), 
                "Card string should contain suit: " + card.getSuit());
        }
    }
}