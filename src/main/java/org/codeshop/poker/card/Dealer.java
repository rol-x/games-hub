package org.codeshop.poker.card;

import java.util.Collections;
import java.util.LinkedList;

public class Dealer {
  private final LinkedList<Card> deck;

  public Dealer() {
    deck = createOrderedDeck();
    Collections.shuffle(deck);
  }

  public Card dealCard() {
    if (deck.isEmpty()) {
      System.out.println("The game cannot continue because the deck has ended.");
      System.exit(0);
    }
    return deck.pop();
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
