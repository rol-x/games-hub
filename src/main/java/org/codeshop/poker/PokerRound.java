package org.codeshop.poker;

import java.util.Comparator;
import java.util.List;
import org.codeshop.poker.card.RankedCards;
import org.codeshop.poker.player.Player;

class PokerRound {
  private List<Player> players;
  private Deck deck;

  public PokerRound(List<Player> players, Deck deck) {
    this.players = players;
    this.deck = deck;
  }

  public void dealHandToEachPlayer() {
    for (int i = 0; i < 5; i++) {
      for (var player : players) {
        var card = deck.takeTopCard();
        player.takeCard(card);
      }
    }
  }

  public Player findWinner() {
    var bestRanking =
        players.stream()
            .sorted(Comparator.comparing(o -> o.rankHand().getRanking()))
            .toList()
            .get(0)
            .rankHand()
            .getRanking();
    var contestingPlayers =
        players.stream()
            .filter(player -> player.rankHand().getRanking().equals(bestRanking))
            .toList();
    if (contestingPlayers.size() == 1) return contestingPlayers.get(0);
    System.out.println("# of contenders: " + contestingPlayers.size());
    return contestingPlayers.get(0);
  }

  public void disposePlayedCards() {
    players.forEach(Player::disposeHand);
  }

  public RankedCards findWinningHand() {
    return players.stream()
        .sorted(Comparator.comparing(o -> o.rankHand().getRanking()))
        .toList()
        .get(0)
        .rankHand();
  }
}
