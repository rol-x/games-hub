package org.codeshop.poker.card;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Deck {
  private final LinkedList<Card> cards;
  private final LinkedList<Card> audit = new LinkedList<>();

  public Card takeTopCard() {
    var next = cards.pop();
    audit.add(next);
    return next;
  }

  public boolean isEmpty() {
    return this.cards.isEmpty();
  }

  public List<Card> getAudit() {
    return this.audit;
  }

  public static Deck shuffled() {
    LinkedList<Card> allCards = createOrderedDeck();
    var randomGenerator = new Random();
    for (int i = 0; i < 1000; i++) {
      allCards.add(randomGenerator.nextInt(52), allCards.remove(randomGenerator.nextInt(52)));
    }
    return new Deck(allCards);
  }

  private static LinkedList<Card> createOrderedDeck() {
    var allCards = new LinkedList<Card>();
    for (var suit : Suit.values()) {
      for (var rank : Rank.values()) {
        var card = new Card(suit, rank);
        allCards.add(card);
      }
    }
    return allCards;
  }

  private Deck(LinkedList<Card> cards) {
    this.cards = cards;
  }
}
