package org.codeshop.poker.card;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import org.codeshop.poker.player.PokerHandEvaluator;

@Getter
public class Hand {
  private final Set<Card> cards = new HashSet<>();
  private Ranking ranking;
  private List<Card> cardsInRank;
  private List<Card> otherCards;

  public void add(Card card) {
    this.cards.add(card);
  }

  public void rankCards() {
    var rankedHand = PokerHandEvaluator.evaluate(cards);
    this.ranking = rankedHand.getRanking();
    this.cardsInRank = rankedHand.getCardsInRank();
    this.otherCards = rankedHand.getOtherCards();
  }

  @Override
  public String toString() {
    var result = new StringBuilder(ranking.toString());
    for (var card : cardsInRank) {
      result.append(" ").append(card);
    }
    result.append(" |");
    for (var card : otherCards) {
      result.append(" ").append(card);
    }
    return result.toString();
  }
}
