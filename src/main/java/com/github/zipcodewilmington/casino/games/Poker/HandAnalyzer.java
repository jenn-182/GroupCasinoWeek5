package com.github.zipcodewilmington.casino.games.Poker;

import java.util.*;

public class HandAnalyzer {

    public static class HandValue implements Comparable<HandValue> {
        int rank; // 10 Royal Flush ... 0 High Card
        List<Integer> kickers; // For tie-breaking (sorted descending)

        HandValue(int rank, List<Integer> kickers) {
            this.rank = rank;
            this.kickers = kickers;
        }

        @Override
        public int compareTo(HandValue other) {
            if (this.rank != other.rank) return Integer.compare(this.rank, other.rank);
            for (int i = 0; i < Math.min(this.kickers.size(), other.kickers.size()); i++) {
                if (!this.kickers.get(i).equals(other.kickers.get(i))) {
                    return Integer.compare(this.kickers.get(i), other.kickers.get(i));
                }
            }
            return 0;
        }
    }

    public HandValue evaluate(Hand hand, List<Card> communityCards) {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(hand.gethand());
        allCards.addAll(communityCards);

        List<List<Card>> allCombinations = generateCombinations(allCards, 5);
        List<Card> holeCards = hand.gethand();

        HandValue best = null;
        for (List<Card> combo : allCombinations) {
            if (!containsAtLeastOneHoleCard(combo, holeCards)) continue;
            HandValue hv = evaluateFiveCards(combo);
            if (best == null || hv.compareTo(best) > 0) {
                best = hv;
            }
        }
        return best;
    }

    private HandValue evaluateFiveCards(List<Card> cards) {
        cards.sort((a, b) -> b.getValue() - a.getValue());
        boolean flush = isFlush(cards);
        boolean straight = isStraight(cards);
        Map<Integer, Integer> valueCounts = getValueCounts(cards);

        List<Integer> valuesDesc = getSortedValuesForKickers(valueCounts);

        if (flush && straight && highestStraightValue(cards) == 14) return new HandValue(10, valuesDesc); // Royal
        if (flush && straight) return new HandValue(9, Arrays.asList(highestStraightValue(cards)));
        if (hasOfAKind(valueCounts, 4)) return new HandValue(8, getKickers(valueCounts, 4));
        if (hasFullHouse(valueCounts)) return new HandValue(7, getFullHouseKickers(valueCounts));
        if (flush) return new HandValue(6, valuesDesc);
        if (straight) return new HandValue(5, Arrays.asList(highestStraightValue(cards)));
        if (hasOfAKind(valueCounts, 3)) return new HandValue(4, getKickers(valueCounts, 3));
        if (hasTwoPair(valueCounts)) return new HandValue(3, getTwoPairKickers(valueCounts));
        if (hasOfAKind(valueCounts, 2)) return new HandValue(2, getKickers(valueCounts, 2));
        return new HandValue(0, valuesDesc); // High Card
    }

    // ----- Helper methods -----
    private boolean isFlush(List<Card> cards) {
        String suit = cards.get(0).getSuit();
        for (Card c : cards) {
            if (!c.getSuit().equals(suit)) return false;
        }
        return true;
    }

    private boolean isStraight(List<Card> cards) {
        Set<Integer> values = new HashSet<>();
        for (Card c : cards) values.add(c.getValue());

        List<Integer> sorted = new ArrayList<>(values);
        Collections.sort(sorted);

        // Ace-low straight
        if (values.contains(14) && values.contains(2) && values.contains(3) && values.contains(4) && values.contains(5)) {
            return true;
        }

        for (int i = 0; i < sorted.size() - 4; i++) {
            if (sorted.get(i + 4) - sorted.get(i) == 4) return true;
        }
        return sorted.size() == 5 && sorted.get(4) - sorted.get(0) == 4;
    }

    private int highestStraightValue(List<Card> cards) {
        Set<Integer> values = new HashSet<>();
        for (Card c : cards) values.add(c.getValue());
        if (values.contains(14) && values.contains(2) && values.contains(3) && values.contains(4) && values.contains(5)) {
            return 5;
        }
        return Collections.max(values);
    }

    private Map<Integer, Integer> getValueCounts(List<Card> cards) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (Card c : cards) counts.put(c.getValue(), counts.getOrDefault(c.getValue(), 0) + 1);
        return counts;
    }

    private boolean hasOfAKind(Map<Integer, Integer> counts, int n) {
        return counts.containsValue(n);
    }

    private boolean hasTwoPair(Map<Integer, Integer> counts) {
        int pairCount = 0;
        for (int v : counts.values()) if (v == 2) pairCount++;
        return pairCount >= 2;
    }

    private boolean hasFullHouse(Map<Integer, Integer> counts) {
        return hasOfAKind(counts, 3) && hasOfAKind(counts, 2);
    }

    private List<Integer> getSortedValuesForKickers(Map<Integer, Integer> counts) {
        List<Integer> values = new ArrayList<>(counts.keySet());
        values.sort((a, b) -> b - a);
        return values;
    }

    private List<Integer> getKickers(Map<Integer, Integer> counts, int n) {
        List<Integer> group = new ArrayList<>();
        List<Integer> others = new ArrayList<>();
        for (int v : counts.keySet()) {
            if (counts.get(v) == n) group.add(v);
            else others.add(v);
        }
        group.sort(Collections.reverseOrder());
        others.sort(Collections.reverseOrder());
        group.addAll(others);
        return group;
    }

    private List<Integer> getFullHouseKickers(Map<Integer, Integer> counts) {
        int three = 0, pair = 0;
        for (int v : counts.keySet()) {
            if (counts.get(v) == 3) three = v;
            else if (counts.get(v) == 2) pair = v;
        }
        return Arrays.asList(three, pair);
    }

    private List<Integer> getTwoPairKickers(Map<Integer, Integer> counts) {
        List<Integer> pairs = new ArrayList<>();
        int kicker = 0;
        for (int v : counts.keySet()) {
            if (counts.get(v) == 2) pairs.add(v);
            else kicker = v;
        }
        pairs.sort(Collections.reverseOrder());
        pairs.add(kicker);
        return pairs;
    }

    private boolean containsAtLeastOneHoleCard(List<Card> combo, List<Card> holeCards) {
        for (Card hc : holeCards) {
            for (Card c : combo) {
                if (hc.getValue() == c.getValue() && hc.getSuit().equals(c.getSuit())) return true;
            }
        }
        return false;
    }

    private List<List<Card>> generateCombinations(List<Card> cards, int r) {
        List<List<Card>> result = new ArrayList<>();
        combineHelper(cards, r, 0, new ArrayList<>(), result);
        return result;
    }

    private void combineHelper(List<Card> cards, int r, int start, List<Card> temp, List<List<Card>> res) {
        if (temp.size() == r) {
            res.add(new ArrayList<>(temp));
            return;
        }
        for (int i = start; i < cards.size(); i++) {
            temp.add(cards.get(i));
            combineHelper(cards, r, i + 1, temp, res);
            temp.remove(temp.size() - 1);
        }
    }

    public Hand compareHands(Hand hand1, Hand hand2, List<Card> communityCards) {
        HandValue hv1 = evaluate(hand1, communityCards);
        HandValue hv2 = evaluate(hand2, communityCards);
        int cmp = hv1.compareTo(hv2);
        if (cmp > 0) return hand1;
        if (cmp < 0) return hand2;
        return null; // tie
    }
}
