package org.codeshop.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.codeshop.poker.card.Ranking;
import org.codeshop.poker.player.ComputerPlayer;
import org.codeshop.poker.player.HumanPlayer;
import org.codeshop.poker.player.Player;

public class Poker {
  public static final int ROUNDS_LIMIT = 200;
  private final List<Player> players = new ArrayList<>();

  public void play() {
    boolean alive = true;
    List<Ranking> allRankings = new ArrayList<>();
    int roundCount = 0;
    do {
      var round = new PokerRound(players);
      round.dealHandToEachPlayer();
      round.rankEachHand();
      round.displayEachPlayersHand();
      var winningRanking = round.findWinningRanking();
      System.out.println("Winning ranking: " + winningRanking);
      allRankings.add(winningRanking);

      var winners = round.findWinningPlayers();
      winners.forEach(winner -> winner.win(100));
      System.out.println("Winners: " + winners);
      round.disposePlayedCards();
      roundCount += 1;
      if (roundCount >= ROUNDS_LIMIT) alive = false;
      System.out.println();
    } while (alive);
    printStatisticsAfterGame(allRankings, roundCount);
  }

  private static void printStatisticsAfterGame(List<Ranking> allRankings, int roundCount) {
    System.out.println("Rounds played: " + roundCount);
    var rankOccurrences = new StringBuilder();
    for (var ranking : Ranking.values())
      if (Collections.frequency(allRankings, ranking) > 0)
        rankOccurrences.append(
            "%s: %d%n".formatted(ranking, Collections.frequency(allRankings, ranking)));
    System.out.println(rankOccurrences);
  }

  public static void simulateHandDealsAndReportStatistics(int numberOfHandDeals) {
    System.out.println("Simulation running...");
    Map<Ranking, Integer> rankingsFrequency = new EnumMap<>(Ranking.class);
    int roundCount = 0;
    do {
      var round = new PokerRound(List.of(new ComputerPlayer("Steven")));
      round.dealHandToEachPlayer();
      round.rankEachHand();
      var ranking = round.findWinningRanking();

      if (rankingsFrequency.containsKey(ranking))
        rankingsFrequency.put(ranking, rankingsFrequency.get(ranking) + 1);
      else rankingsFrequency.put(ranking, 1);

      round.disposePlayedCards();
      roundCount += 1;
    } while (roundCount < numberOfHandDeals);
    System.out.println("Rounds played: " + roundCount);
    double totalRoundCount = roundCount;
    rankingsFrequency.forEach(
        (ranking, frequency) ->
            System.out.printf(
                "%s: %d (%.2f%%)%n", ranking, frequency, 100f * frequency / totalRoundCount));
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
