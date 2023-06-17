package org.codeshop.poker;

import java.util.Comparator;
import java.util.List;
import org.codeshop.poker.card.Dealer;
import org.codeshop.poker.card.Hand;
import org.codeshop.poker.card.Ranking;
import org.codeshop.poker.player.Player;

class PokerRound {
  private final List<Player> players;
  private final Dealer dealer;
  private final Tiebreaker tiebreaker;

  public PokerRound(List<Player> players) {
    this.players = players;
    this.dealer = new Dealer();
    this.tiebreaker = new Tiebreaker();
  }

  public void dealHandToEachPlayer() {
    for (int i = 0; i < 5; i++) {
      for (var player : players) {
        var card = dealer.dealCard();
        player.takeCard(card);
      }
    }
  }

  public void rankEachHand() {
    players.forEach(Player::rankHand);
  }

  public Ranking findWinningRanking() {
    return players.stream()
        .map(Player::getHand)
        .max(Comparator.comparing(Hand::getRanking))
        .map(Hand::getRanking)
        .orElse(null);
  }

  public List<Player> findWinningPlayers() {
    var winningRanking = findWinningRanking();
    var contestingPlayers =
        players.stream()
            .filter(player -> player.getHand().getRanking().equals(winningRanking))
            .toList();
    if (contestingPlayers.size() == 1) return contestingPlayers;
    System.out.printf("Tie between %d players%n", contestingPlayers.size());
    return tiebreaker.findWinnersInTie(contestingPlayers);
  }

  public void disposePlayedCards() {
    players.forEach(Player::disposeHand);
  }

  public void displayEachPlayersHand() {
    players.forEach(
        player -> System.out.printf("%s\t[%s]%n", player, player.getHand().getRanking()));
  }
}
