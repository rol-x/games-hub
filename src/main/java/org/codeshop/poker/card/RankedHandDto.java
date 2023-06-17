package org.codeshop.poker.card;

import java.util.Comparator;
import java.util.List;

public record RankedHandDto(Ranking ranking, List<Card> cardsInRank, List<Card> otherCards) {
  public RankedHandDto(Ranking ranking, List<Card> cardsInRank, List<Card> otherCards) {
    this.ranking = ranking;
    this.cardsInRank =
        cardsInRank.stream().sorted(Comparator.comparing(Card::getRank).reversed()).toList();
    this.otherCards =
        otherCards.stream().sorted(Comparator.comparing(Card::getRank).reversed()).toList();
  }
}
