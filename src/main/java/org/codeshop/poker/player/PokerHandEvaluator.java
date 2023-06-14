package org.codeshop.poker.player;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.codeshop.poker.card.Card;
import org.codeshop.poker.card.Rank;
import org.codeshop.poker.card.RankedHandDto;
import org.codeshop.poker.card.Ranking;
import org.codeshop.poker.card.Suit;

public class PokerHandEvaluator {
  public static RankedHandDto evaluate(Set<Card> hand) {
    Map<Rank, Integer> rankFrequencies = getRankFrequencies(hand);

    if (rankFrequencies.containsValue(4)) return evaluateFourOfAKind(hand);
    if (rankFrequencies.containsValue(3) && rankFrequencies.containsValue(2))
      return evaluateFullHouse(hand);
    if (rankFrequencies.containsValue(3)) return evaluateThreeOfAKind(hand);
    if (rankFrequencies.containsValue(2)) {
      var pairsCount =
          rankFrequencies.values().stream().filter(cardFrequency -> cardFrequency == 2).count();
      if (pairsCount == 2) return evaluateTwoPairs(hand);
      return evaluatePair(hand);
    }

    Map<Suit, Integer> suitFrequencies = getSuitFrequencies(hand);
    var straight = isStraight(rankFrequencies);
    var flush = suitFrequencies.containsValue(5);

    if (straight && flush) {
      if (getHighCard(hand).equals(Rank.ACE)) return evaluateRoyalFlush(hand);
      return evaluateStraightFlush(hand);
    }
    if (straight) return evaluateStraight(hand);
    if (flush) return evaluateFlush(hand);
    return evaluateHighCard(hand);
  }

  private static RankedHandDto evaluateRoyalFlush(Set<Card> hand) {
    return new RankedHandDto(
        Ranking.ROYAL_FLUSH,
        List.of(),
        hand.stream().sorted(Comparator.comparing(Card::getRank).reversed()).toList());
  }

  private static RankedHandDto evaluateStraightFlush(Set<Card> hand) {
    return new RankedHandDto(
        Ranking.STRAIGHT_FLUSH,
        List.of(),
        hand.stream().sorted(Comparator.comparing(Card::getRank).reversed()).toList());
  }

  private static RankedHandDto evaluateFourOfAKind(Set<Card> hand) {
    return new RankedHandDto(
        Ranking.FOUR_OF_A_KIND,
        List.of(),
        hand.stream().sorted(Comparator.comparing(Card::getRank).reversed()).toList());
  }

  private static RankedHandDto evaluateFullHouse(Set<Card> hand) {
    return new RankedHandDto(
        Ranking.FULL_HOUSE,
        List.of(),
        hand.stream().sorted(Comparator.comparing(Card::getRank).reversed()).toList());
  }

  private static RankedHandDto evaluateFlush(Set<Card> hand) {
    return new RankedHandDto(
        Ranking.FLUSH,
        List.of(),
        hand.stream().sorted(Comparator.comparing(Card::getRank).reversed()).toList());
  }

  private static RankedHandDto evaluateStraight(Set<Card> hand) {
    return new RankedHandDto(
        Ranking.STRAIGHT,
        List.of(),
        hand.stream().sorted(Comparator.comparing(Card::getRank).reversed()).toList());
  }

  private static RankedHandDto evaluateThreeOfAKind(Set<Card> hand) {
    return new RankedHandDto(
        Ranking.THREE_OF_A_KIND,
        List.of(),
        hand.stream().sorted(Comparator.comparing(Card::getRank).reversed()).toList());
  }

  private static RankedHandDto evaluateTwoPairs(Set<Card> hand) {
    return new RankedHandDto(
        Ranking.TWO_PAIR,
        List.of(),
        hand.stream().sorted(Comparator.comparing(Card::getRank).reversed()).toList());
  }

  private static RankedHandDto evaluatePair(Set<Card> hand) {
    return new RankedHandDto(
        Ranking.PAIR,
        List.of(),
        hand.stream().sorted(Comparator.comparing(Card::getRank).reversed()).toList());
  }

  private static RankedHandDto evaluateHighCard(Set<Card> hand) {
    var highCard = hand.stream().max(Comparator.comparing(Card::getRank)).orElseThrow();
    return new RankedHandDto(
        Ranking.HIGH_CARD,
        List.of(highCard),
        hand.stream()
            .filter(card -> !card.equals(highCard))
            .sorted(Comparator.comparing(Card::getRank).reversed())
            .toList());
  }

  private static Rank getHighCard(Set<Card> hand) {
    return hand.stream().max(Comparator.comparing(Card::getRank)).orElseThrow().getRank();
  }

  private static boolean isStraight(Map<Rank, Integer> rankFrequencies) {
    var ranks = rankFrequencies.keySet().stream().sorted().toList();
    return ranks.get(4).getValue() - ranks.get(0).getValue() == 4;
  }

  private static Map<Suit, Integer> getSuitFrequencies(Set<Card> hand) {
    Map<Suit, Integer> frequencies = new EnumMap<>(Suit.class);
    hand.forEach(
        card -> {
          var suit = card.getSuit();
          if (!frequencies.containsKey(suit)) frequencies.put(suit, 1);
          else frequencies.put(suit, frequencies.get(suit) + 1);
        });
    return frequencies;
  }

  private static Map<Rank, Integer> getRankFrequencies(Set<Card> hand) {
    Map<Rank, Integer> frequencies = new EnumMap<>(Rank.class);
    hand.forEach(
        card -> {
          var rank = card.getRank();
          if (!frequencies.containsKey(rank)) frequencies.put(rank, 1);
          else frequencies.put(rank, frequencies.get(rank) + 1);
        });
    return frequencies;
  }

  private PokerHandEvaluator() {}
}
