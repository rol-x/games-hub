package org.codeshop.poker.card;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RankedHandDto {
  private Ranking ranking;
  private List<Card> cardsInRank;
  private List<Card> otherCards;
}
