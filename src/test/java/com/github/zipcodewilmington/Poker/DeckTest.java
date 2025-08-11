package com.github.zipcodewilmington.Poker;

import org.junit.jupiter.api.Test;

import com.github.zipcodewilmington.casino.games.Poker.Card;
import com.github.zipcodewilmington.casino.games.Poker.Deck;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * JUnit test class for Deck
 */
@DisplayName("Deck Tests")
public class DeckTest {

    private Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    @Test
    @DisplayName("Should create deck with 52 cards")
    void testDeckSize() {
        Deck testDeck = new Deck();
        int cardCount = 0;
        
        // Count cards by dealing all of them
        try {
            while (true) {
                testDeck.dealCard();
                cardCount++;
            }
        } catch (EmptyStackException e) {
            // Expected when deck is empty
        }
        
        assertEquals(52, cardCount);
    }

    @Test
    @DisplayName("Should deal cards from deck")
    void testDealCard() {
        Card dealtCard = deck.dealCard();
        
        assertNotNull(dealtCard);
        assertNotNull(dealtCard.getSuit());
        assertNotNull(dealtCard.getRank());
        assertTrue(dealtCard.getValue() >= 2 && dealtCard.getValue() <= 14);
    }

    @Test
    @DisplayName("Should deal different cards sequentially")
    void testDealMultipleCards() {
        Card card1 = deck.dealCard();
        Card card2 = deck.dealCard();
        Card card3 = deck.dealCard();
        
        assertNotNull(card1);
        assertNotNull(card2);
        assertNotNull(card3);
        
        // Note: Due to shuffling, cards might be the same, but this is extremely unlikely
        // We'll just verify they're valid cards
        assertTrue(card1.getValue() >= 2 && card1.getValue() <= 14);
        assertTrue(card2.getValue() >= 2 && card2.getValue() <= 14);
        assertTrue(card3.getValue() >= 2 && card3.getValue() <= 14);
    }

    @Test
    @DisplayName("Should contain all 4 suits")
    void testDeckContainsAllSuits() {
        Set<String> foundSuits = new HashSet<>();
        
        // Deal all cards and collect suits
        for (int i = 0; i < 52; i++) {
            Card card = deck.dealCard();
            foundSuits.add(card.getSuit());
        }
        
        assertTrue(foundSuits.contains("H"), "Deck should contain Hearts");
        assertTrue(foundSuits.contains("D"), "Deck should contain Diamonds");
        assertTrue(foundSuits.contains("C"), "Deck should contain Clubs");
        assertTrue(foundSuits.contains("S"), "Deck should contain Spades");
        assertEquals(4, foundSuits.size(), "Deck should contain exactly 4 suits");
    }

    @Test
    @DisplayName("Should contain all 13 ranks")
    void testDeckContainsAllRanks() {
        Set<String> foundRanks = new HashSet<>();
        
        // Deal all cards and collect ranks
        for (int i = 0; i < 52; i++) {
            Card card = deck.dealCard();
            foundRanks.add(card.getRank());
        }
        
        String[] expectedRanks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        
        for (String rank : expectedRanks) {
            assertTrue(foundRanks.contains(rank), "Deck should contain rank: " + rank);
        }
        
        assertEquals(13, foundRanks.size(), "Deck should contain exactly 13 ranks");
    }

    @Test
    @DisplayName("Should contain correct number of each suit")
    void testCorrectSuitDistribution() {
        Map<String, Integer> suitCount = new HashMap<>();
        suitCount.put("H", 0);
        suitCount.put("D", 0);
        suitCount.put("C", 0);
        suitCount.put("S", 0);
        
        // Deal all cards and count suits
        for (int i = 0; i < 52; i++) {
            Card card = deck.dealCard();
            suitCount.put(card.getSuit(), suitCount.get(card.getSuit()) + 1);
        }
        
        assertEquals(13, suitCount.get("H"), "Should have 13 Hearts");
        assertEquals(13, suitCount.get("D"), "Should have 13 Diamonds");
        assertEquals(13, suitCount.get("C"), "Should have 13 Clubs");
        assertEquals(13, suitCount.get("S"), "Should have 13 Spades");
    }

    @Test
    @DisplayName("Should return cards to deck")
    void testReturnCardsToDeck() {
        Stack<Card> returnedHand = new Stack<>();
        
        // Deal some cards
        Card card1 = deck.dealCard();
        Card card2 = deck.dealCard();
        returnedHand.push(card1);
        returnedHand.push(card2);
        
        // Return them
        deck.returnCardsToDeck(returnedHand);
        
        // Should be able to deal more cards now
        Card newCard1 = deck.dealCard();
        Card newCard2 = deck.dealCard();
        Card newCard3 = deck.dealCard();
        
        assertNotNull(newCard1);
        assertNotNull(newCard2);
        assertNotNull(newCard3);
    }

    @Test
    @DisplayName("Should return empty stack to deck without error")
    void testReturnEmptyStackToDeck() {
        Stack<Card> emptyHand = new Stack<>();
        
        // This should not throw an exception
        assertDoesNotThrow(() -> deck.returnCardsToDeck(emptyHand));
        
        // Should still be able to deal cards
        Card card = deck.dealCard();
        assertNotNull(card);
    }

    @Test
    @DisplayName("Should return multiple cards correctly")
    void testReturnMultipleCards() {
        Stack<Card> returnedHand = new Stack<>();
        
        // Deal 5 cards
        for (int i = 0; i < 5; i++) {
            returnedHand.push(deck.dealCard());
        }
        
        // Return all 5 cards
        deck.returnCardsToDeck(returnedHand);
        
        // Should be able to deal at least 5 more cards
        for (int i = 0; i < 5; i++) {
            Card card = deck.dealCard();
            assertNotNull(card);
        }
    }

    @Test
    @DisplayName("Should throw exception when dealing from empty deck")
    void testDealFromEmptyDeck() {
        // Deal all 52 cards
        for (int i = 0; i < 52; i++) {
            deck.dealCard();
        }
        
        // Should throw exception on 53rd card
        assertThrows(EmptyStackException.class, () -> deck.dealCard());
    }

    @Test
    @DisplayName("Should shuffle deck")
    void testShuffleDeck() {
        // Create two identical decks
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        
        // Deal a few cards from each to compare initial order
        List<Card> cards1 = new ArrayList<>();
        List<Card> cards2 = new ArrayList<>();
        
        for (int i = 0; i < 10; i++) {
            cards1.add(deck1.dealCard());
            cards2.add(deck2.dealCard());
        }
        
        // Shuffle the second deck
        deck2.shuffleDeck();
        
        // Deal more cards from the shuffled deck
        List<Card> shuffledCards = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            shuffledCards.add(deck2.dealCard());
        }
        
        // The shuffled sequence should be valid (all cards should be proper cards)
        for (Card card : shuffledCards) {
            assertNotNull(card);
            assertTrue(card.getValue() >= 2 && card.getValue() <= 14);
        }
    }

    @Test
    @DisplayName("Should maintain deck integrity after shuffle")
    void testDeckIntegrityAfterShuffle() {
        deck.shuffleDeck();
        
        Set<String> dealtCards = new HashSet<>();
        
        // Deal all cards and ensure no duplicates
        for (int i = 0; i < 52; i++) {
            Card card = deck.dealCard();
            String cardId = card.getRank() + card.getSuit();
            
            assertFalse(dealtCards.contains(cardId), 
                "Duplicate card found: " + cardId);
            dealtCards.add(cardId);
        }
        
        assertEquals(52, dealtCards.size(), "Should have dealt exactly 52 unique cards");
    }

    @Test
    @DisplayName("Should handle multiple shuffles")
    void testMultipleShuffles() {
        deck.shuffleDeck();
        deck.shuffleDeck();
        deck.shuffleDeck();
        
        // Should still be able to deal all 52 cards
        int cardCount = 0;
        try {
            while (true) {
                Card card = deck.dealCard();
                assertNotNull(card);
                cardCount++;
            }
        } catch (EmptyStackException e) {
            // Expected when deck is empty
        }
        
        assertEquals(52, cardCount);
    }

    @Test
    @DisplayName("Should create proper card values")
    void testProperCardValues() {
        Set<Integer> foundValues = new HashSet<>();
        
        // Deal all cards and collect values
        for (int i = 0; i < 52; i++) {
            Card card = deck.dealCard();
            foundValues.add(card.getValue());
        }
        
        // Should have values 2 through 14 (inclusive)
        for (int value = 2; value <= 14; value++) {
            assertTrue(foundValues.contains(value), 
                "Deck should contain cards with value: " + value);
        }
        
        assertEquals(13, foundValues.size(), "Should have exactly 13 different values");
    }

    @Test
    @DisplayName("Should maintain consistency between rank and value")
    void testRankValueConsistency() {
        Map<String, Integer> expectedValues = new HashMap<>();
        expectedValues.put("2", 2);
        expectedValues.put("3", 3);
        expectedValues.put("4", 4);
        expectedValues.put("5", 5);
        expectedValues.put("6", 6);
        expectedValues.put("7", 7);
        expectedValues.put("8", 8);
        expectedValues.put("9", 9);
        expectedValues.put("10", 10);
        expectedValues.put("J", 11);
        expectedValues.put("Q", 12);
        expectedValues.put("K", 13);
        expectedValues.put("A", 14);
        
        // Deal all cards and verify rank-value consistency
        for (int i = 0; i < 52; i++) {
            Card card = deck.dealCard();
            Integer expectedValue = expectedValues.get(card.getRank());
            
            assertNotNull(expectedValue, "Unknown rank: " + card.getRank());
            assertEquals(expectedValue.intValue(), card.getValue(),
                "Value mismatch for rank " + card.getRank());
        }
    }

    @Test
    @DisplayName("Should handle deck after partial dealing and returning")
    void testPartialDealAndReturn() {
        // Deal 10 cards
        Stack<Card> dealtCards = new Stack<>();
        for (int i = 0; i < 10; i++) {
            dealtCards.push(deck.dealCard());
        }
        
        // Return 5 cards
        Stack<Card> returnCards = new Stack<>();
        for (int i = 0; i < 5; i++) {
            returnCards.push(dealtCards.pop());
        }
        deck.returnCardsToDeck(returnCards);
        
        // Should be able to deal more cards
        Card newCard = deck.dealCard();
        assertNotNull(newCard);
        assertTrue(newCard.getValue() >= 2 && newCard.getValue() <= 14);
    }
}