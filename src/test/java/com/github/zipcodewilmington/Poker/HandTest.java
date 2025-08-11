package com.github.zipcodewilmington.Poker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.zipcodewilmington.casino.games.Poker.Card;
import com.github.zipcodewilmington.casino.games.Poker.Hand;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Stack;

/**
 * JUnit test class for Hand
 */
@DisplayName("Hand Tests")
public class HandTest {

    private Hand hand;
    private Card testCard1;
    private Card testCard2;
    private Card testCard3;

    @BeforeEach
    void setUp() {
        hand = new Hand();
        testCard1 = new Card("H", "A", 14);
        testCard2 = new Card("S", "K", 13);
        testCard3 = new Card("D", "Q", 12);
    }

    @Test
    @DisplayName("Should initialize empty hand")
    void testHandInitialization() {
        assertTrue(hand.gethand().isEmpty());
        assertEquals(0, hand.gethand().size());
    }

    @Test
    @DisplayName("Should receive single card correctly")
    void testReceiveSingleCard() {
        hand.receiveCard(testCard1);
        
        assertEquals(1, hand.gethand().size());
        assertTrue(hand.gethand().contains(testCard1));
        assertEquals(testCard1, hand.gethand().peek());
    }

    @Test
    @DisplayName("Should receive multiple cards correctly")
    void testReceiveMultipleCards() {
        hand.receiveCard(testCard1);
        hand.receiveCard(testCard2);
        
        assertEquals(2, hand.gethand().size());
        assertTrue(hand.gethand().contains(testCard1));
        assertTrue(hand.gethand().contains(testCard2));
    }

    @Test
    @DisplayName("Should receive cards in stack order")
    void testReceiveCardsInOrder() {
        hand.receiveCard(testCard1);
        hand.receiveCard(testCard2);
        hand.receiveCard(testCard3);
        
        Stack<Card> handStack = hand.gethand();
        assertEquals(3, handStack.size());
        
        // Last card in should be on top (stack behavior)
        assertEquals(testCard3, handStack.peek());
    }

    @Test
    @DisplayName("Should return cards to dealer")
    void testReturnCardsToDealer() {
        hand.receiveCard(testCard1);
        hand.receiveCard(testCard2);
        
        Stack<Card> returnedCards = hand.returnCardsToDealer();
        
        assertEquals(2, returnedCards.size());
        assertTrue(returnedCards.contains(testCard1));
        assertTrue(returnedCards.contains(testCard2));
    }

    @Test
    @DisplayName("Should return empty stack when no cards")
    void testReturnEmptyHand() {
        Stack<Card> returnedCards = hand.returnCardsToDealer();
        
        assertNotNull(returnedCards);
        assertTrue(returnedCards.isEmpty());
        assertEquals(0, returnedCards.size());
    }

    @Test
    @DisplayName("Should return same reference to hand stack")
    void testReturnCardsReturnsActualStack() {
        hand.receiveCard(testCard1);
        hand.receiveCard(testCard2);
        
        Stack<Card> returnedCards = hand.returnCardsToDealer();
        Stack<Card> handStack = hand.gethand();
        
        // Should be the same object reference
        assertSame(returnedCards, handStack);
    }

    @Test
    @DisplayName("Should print hand without throwing exception")
    void testPrintHand() {
        hand.receiveCard(testCard1);
        hand.receiveCard(testCard2);
        
        // This test just ensures printHand doesn't throw an exception
        assertDoesNotThrow(() -> hand.printHand());
    }

    @Test
    @DisplayName("Should print empty hand without throwing exception")
    void testPrintEmptyHand() {
        assertDoesNotThrow(() -> hand.printHand());
    }

    @Test
    @DisplayName("Should get hand stack correctly")
    void testGetHand() {
        hand.receiveCard(testCard1);
        Stack<Card> handStack = hand.gethand();
        
        assertEquals(1, handStack.size());
        assertEquals(testCard1, handStack.peek());
        assertSame(hand.gethand(), handStack);
    }

    @Test
    @DisplayName("Should handle multiple cards in correct stack order")
    void testCardStackOrder() {
        hand.receiveCard(testCard1); // First card in
        hand.receiveCard(testCard2); // Second card in
        hand.receiveCard(testCard3); // Third card in
        
        Stack<Card> handStack = hand.gethand();
        
        // Stack is LIFO (Last In, First Out)
        assertEquals(testCard3, handStack.pop()); // Last card in should be first out
        assertEquals(testCard2, handStack.pop()); // Second card in should be second out
        assertEquals(testCard1, handStack.pop()); // First card in should be last out
        assertTrue(handStack.isEmpty());
    }

    @Test
    @DisplayName("Should maintain card references correctly")
    void testCardReferences() {
        hand.receiveCard(testCard1);
        
        Stack<Card> handStack = hand.gethand();
        Card retrievedCard = handStack.peek();
        
        // Should be the same object reference
        assertSame(testCard1, retrievedCard);
        assertEquals(testCard1.getSuit(), retrievedCard.getSuit());
        assertEquals(testCard1.getRank(), retrievedCard.getRank());
        assertEquals(testCard1.getValue(), retrievedCard.getValue());
    }

    @Test
    @DisplayName("Should handle receiving same card multiple times")
    void testReceiveSameCardMultipleTimes() {
        hand.receiveCard(testCard1);
        hand.receiveCard(testCard1); // Same card again
        
        assertEquals(2, hand.gethand().size());
        
        Stack<Card> handStack = hand.gethand();
        assertEquals(testCard1, handStack.pop());
        assertEquals(testCard1, handStack.pop());
    }

    @Test
    @DisplayName("Should handle large number of cards")
    void testReceiveManyCards() {
        // Simulate receiving a full deck
        for (int i = 0; i < 52; i++) {
            Card card = new Card("H", "A", 14); // Same card for simplicity
            hand.receiveCard(card);
        }
        
        assertEquals(52, hand.gethand().size());
        
        Stack<Card> returnedCards = hand.returnCardsToDealer();
        assertEquals(52, returnedCards.size());
    }

    @Test
    @DisplayName("Should handle cards with different properties")
    void testReceiveDifferentCards() {
        Card aceHearts = new Card("H", "A", 14);
        Card twoSpades = new Card("S", "2", 2);
        Card jackDiamonds = new Card("D", "J", 11);
        Card kingClubs = new Card("C", "K", 13);
        
        hand.receiveCard(aceHearts);
        hand.receiveCard(twoSpades);
        hand.receiveCard(jackDiamonds);
        hand.receiveCard(kingClubs);
        
        assertEquals(4, hand.gethand().size());
        
        Stack<Card> handStack = hand.gethand();
        assertTrue(handStack.contains(aceHearts));
        assertTrue(handStack.contains(twoSpades));
        assertTrue(handStack.contains(jackDiamonds));
        assertTrue(handStack.contains(kingClubs));
    }

    @Test
    @DisplayName("Should not be null after initialization")
    void testHandNotNull() {
        assertNotNull(hand);
        assertNotNull(hand.gethand());
    }

    @Test
    @DisplayName("Should handle card retrieval without modification")
    void testGetHandDoesNotModify() {
        hand.receiveCard(testCard1);
        hand.receiveCard(testCard2);
        
        Stack<Card> handStack1 = hand.gethand();
        Stack<Card> handStack2 = hand.gethand();
        
        // Should be same reference
        assertSame(handStack1, handStack2);
        assertEquals(2, handStack1.size());
        assertEquals(2, handStack2.size());
    }

    @Test
    @DisplayName("Should allow modification through returned stack reference")
    void testHandStackModification() {
        hand.receiveCard(testCard1);
        hand.receiveCard(testCard2);
        
        Stack<Card> handStack = hand.gethand();
        Card poppedCard = handStack.pop();
        
        assertEquals(testCard2, poppedCard);
        assertEquals(1, hand.gethand().size());
        assertEquals(testCard1, hand.gethand().peek());
    }

    @Test
    @DisplayName("Should handle receiving null card gracefully")
    void testReceiveNullCard() {
        // This test ensures the method doesn't crash with null input
        // The behavior might vary depending on implementation
        assertDoesNotThrow(() -> hand.receiveCard(null));
    }

    @Test
    @DisplayName("Should maintain proper stack behavior throughout operations")
    void testStackBehaviorConsistency() {
        // Add cards
        hand.receiveCard(testCard1);
        assertEquals(1, hand.gethand().size());
        assertEquals(testCard1, hand.gethand().peek());
        
        hand.receiveCard(testCard2);
        assertEquals(2, hand.gethand().size());
        assertEquals(testCard2, hand.gethand().peek()); // Most recent on top
        
        hand.receiveCard(testCard3);
        assertEquals(3, hand.gethand().size());
        assertEquals(testCard3, hand.gethand().peek()); // Most recent on top
        
        // Return cards should give us the same stack
        Stack<Card> returnedStack = hand.returnCardsToDealer();
        assertEquals(3, returnedStack.size());
        assertEquals(testCard3, returnedStack.peek()); // Still most recent on top
    }
}