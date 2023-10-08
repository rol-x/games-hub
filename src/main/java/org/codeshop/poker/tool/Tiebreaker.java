package org.codeshop.poker.tool;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.codeshop.poker.card.Rank;
import org.codeshop.poker.player.Player;

public class Tiebreaker {
  public List<Player> findWinnersInTie(List<Player> contestingPlayers) {
    var remainingContenders = new ArrayList<>(contestingPlayers);
    compareCardsInRankAndEliminate(remainingContenders);
    if (remainingContenders.size() == 1) return remainingContenders;

    compareOtherCardsAndEliminate(remainingContenders);
    return remainingContenders;
  }

  private void compareCardsInRankAndEliminate(ArrayList<Player> remainingContenders) {
    for (int i = 0; i < remainingContenders.get(0).getHand().getCardsInRank().size(); i++) {
      var comparedCardPosition = i;
      var topRank =
          remainingContenders.stream()
              .map(player -> player.getHand().getCardsInRank().get(comparedCardPosition).getRank())
              .max(Comparator.comparing(Rank::getValue))
              .orElseThrow();
      remainingContenders.removeAll(
          remainingContenders.stream()
              .filter(
                  player ->
                      !player
                          .getHand()
                          .getCardsInRank()
                          .get(comparedCardPosition)
                          .getRank()
                          .equals(topRank))
              .toList());
      if (remainingContenders.size() == 1) return;
    }
  }

  private void compareOtherCardsAndEliminate(ArrayList<Player> remainingContenders) {
    for (int i = 0; i < remainingContenders.get(0).getHand().getOtherCards().size(); i++) {
      var comparedCardPosition = i;
      var topRank =
          remainingContenders.stream()
              .map(player -> player.getHand().getOtherCards().get(comparedCardPosition).getRank())
              .max(Comparator.comparing(Rank::getValue))
              .orElseThrow();
      remainingContenders.removeAll(
          remainingContenders.stream()
              .filter(
                  player ->
                      !player
                          .getHand()
                          .getOtherCards()
                          .get(comparedCardPosition)
                          .getRank()
                          .equals(topRank))
              .toList());
      if (remainingContenders.size() == 1) return;
    }
  }
}
