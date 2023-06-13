package org.codeshop.poker;

import java.util.ArrayList;
import java.util.List;
import org.codeshop.poker.card.Deck;
import org.codeshop.poker.player.ComputerPlayer;
import org.codeshop.poker.player.HumanPlayer;
import org.codeshop.poker.player.Player;

public class Poker {
  private final List<Player> players = new ArrayList<>();
  private final Deck deck = Deck.shuffled();

  public void play() {
    boolean alive = false;
    do {
      var round = new PokerRound(players, deck);
      round.dealHandToEachPlayer();
      var winningHand = round.findWinningHand();
      System.out.println("Winning hand: " + winningHand);

      var winner = round.findWinner();
      winner.win(100);
      System.out.println("Winner: " + winner);
      round.disposePlayedCards();
      alive = !alive;
    } while (alive);
  }

  public void addHumanPlayer(String name) {
    var player = new HumanPlayer(name);
    players.add(player);
  }

  public void addComputerPlayer(String name) {
    var player = new ComputerPlayer(name);
    players.add(player);
  }

  // removePlayer()
  // ? giveMoney() ?
  // ? takeMoney() ?
  // play()
}
