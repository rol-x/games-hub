package org.codeshop.poker;

import java.util.ArrayList;
import java.util.List;
import org.codeshop.poker.player.ComputerPlayer;
import org.codeshop.poker.player.HumanPlayer;
import org.codeshop.poker.player.Player;

public class Poker {
  public static final int ROUNDS_LIMIT = 2;
  private final List<Player> players = new ArrayList<>();

  public void play() {
    boolean alive = true;
    int roundCount = 1;
    do {
      var round = new PokerRound(players);
      round.dealHandToEachPlayer();
      round.rankEachHand();
      round.displayEachPlayersHand();
      var winningHand = round.findBestHand();
      System.out.println("Winning hand: " + winningHand);

      var winner = round.findWinningPlayer();
      winner.win(100);
      System.out.println("Winner: " + winner);
      round.disposePlayedCards();
      roundCount += 1;
      if (roundCount > ROUNDS_LIMIT) alive = false;
      System.out.println();
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
}
