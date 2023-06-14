package org.codeshop.poker;

import java.util.List;
import org.codeshop.poker.card.Ranking;
import org.codeshop.poker.player.Player;

public class Tiebreaker {
  public Player findTopPlayerInTie(List<Player> contestingPlayers, Ranking bestRanking) {
    Player winner;
    switch (bestRanking) {
      case HIGH_CARD -> winner = tieBreakHighCard(contestingPlayers);
      case PAIR -> winner = tieBreakPair(contestingPlayers);
      case TWO_PAIR -> winner = tieBreakTwoPairs(contestingPlayers);
      case THREE_OF_A_KIND -> winner = tieBreakThreeOfAKind(contestingPlayers);
      case STRAIGHT, STRAIGHT_FLUSH -> winner = tieBreakStraight(contestingPlayers);
      case FULL_HOUSE -> winner = tieBreakFullHouse(contestingPlayers);
      case FOUR_OF_A_KIND -> winner = tieBreakFourOfAKind(contestingPlayers);
      default -> winner = null;
    }
    return winner;
  }

  private Player tieBreakFourOfAKind(List<Player> contestingPlayers) {
    return contestingPlayers.get(0);
  }

  private Player tieBreakFullHouse(List<Player> contestingPlayers) {
    return contestingPlayers.get(0);
  }

  private Player tieBreakStraight(List<Player> contestingPlayers) {
    return contestingPlayers.get(0);
  }

  private Player tieBreakThreeOfAKind(List<Player> contestingPlayers) {
    return contestingPlayers.get(0);
  }

  private Player tieBreakTwoPairs(List<Player> contestingPlayers) {
    return contestingPlayers.get(0);
  }

  private Player tieBreakHighCard(List<Player> contestingPlayers) {
    return contestingPlayers.get(0);
  }

  private Player tieBreakPair(List<Player> contestingPlayers) {
    return contestingPlayers.get(0);
  }
}
