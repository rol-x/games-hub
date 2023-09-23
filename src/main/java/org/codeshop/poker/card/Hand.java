package org.codeshop.poker.card;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import org.codeshop.poker.PokerHandEvaluator;

@Getter
public class Hand {
  private final Set<Card> cards = new HashSet<>();
  private Ranking ranking;
  private List<Card> cardsInRank;
  private List<Card> otherCards;
  private boolean folded;

  public void add(Card card) {
    cards.add(card);
  }

  public void removeCards(List<Card> cards) {
    this.cards.removeAll(cards);
    rankCards();
  }

  public void rankCards() {
    var rankedHand = PokerHandEvaluator.evaluate(cards);
    ranking = rankedHand.ranking();
    cardsInRank = rankedHand.cardsInRank();
    otherCards = rankedHand.otherCards();
  }

  public void fold() {
    folded = true;
  }

  public boolean isFolded() {
    return folded;
  }

  @Override
  public String toString() {
    var result = new StringBuilder(ranking.toString());
    cardsInRank.forEach(card -> result.append(" ").append(card));
    if (otherCards.isEmpty()) return result.toString();

    result.append(" |");
    otherCards.forEach(card -> result.append(" ").append(card));
    return result.toString();
  }
}
