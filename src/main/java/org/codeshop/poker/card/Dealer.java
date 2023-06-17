package org.codeshop.poker.card;

import java.util.LinkedList;
import java.util.Random;

public class Dealer {
  private LinkedList<Card> deck;
  private final Random random = new Random();

  public Dealer() {
    deck = shuffle(createOrderedDeck());
  }

  public Card dealCard() {
    if (deck.isEmpty()) {
      System.out.println("Shuffling new deck...");
      deck = shuffle(createOrderedDeck());
    }
    return deck.pop();
  }

  private LinkedList<Card> shuffle(LinkedList<Card> cards) {
    var deckSize = cards.size();
    for (int i = 0; i < 1000; i++) {
      cards.add(random.nextInt(deckSize), cards.remove(random.nextInt(deckSize)));
    }
    return cards;
  }

  private static LinkedList<Card> createOrderedDeck() {
    var cards = new LinkedList<Card>();
    for (var suit : Suit.values()) {
      for (var rank : Rank.values()) {
        var card = new Card(suit, rank);
        cards.add(card);
      }
    }
    return cards;
  }
}
