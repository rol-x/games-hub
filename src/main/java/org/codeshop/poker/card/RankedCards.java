package org.codeshop.poker.card;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RankedCards {
  private Ranking ranking;
  private List<Card> cards;

  @Override
  public String toString() {
    var result = new StringBuilder(ranking.toString());
    for (var card : cards) {
      result.append(" ").append(card);
    }
    return result.toString();
  }
}
