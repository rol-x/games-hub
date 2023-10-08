package org.codeshop.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.codeshop.poker.card.Ranking;
import org.codeshop.poker.io.ConsoleIOHandler;
import org.codeshop.poker.io.IOHandler;
import org.codeshop.poker.player.ComputerPlayer;
import org.codeshop.poker.player.HumanPlayer;
import org.codeshop.poker.player.Player;

public class PokerGame {
  private final List<Player> players = new ArrayList<>();
  private final IOHandler ioHandler = new ConsoleIOHandler();

  public void play() {
    shufflePlayers();
    int ante = 20;
    do {
      var round = new PokerRound(players);
      var humanPlayers = players.stream().filter(HumanPlayer.class::isInstance).toList();

      // Initial Bets (Ante):
      // Each player places an initial bet into the pot. This is known as the "ante".
      ioHandler.acceptAnte(humanPlayers, ante);
      round.collectAnteFromPlayers(ante);

      // Deal the Cards:
      // Deal five cards to each player.
      round.dealHandToEachPlayer();
      ioHandler.displayHandsMidGame(players);

      // First Betting Round:
      // Each player has the opportunity to "check", "bet", "fold", "call", or "raise".
      round.playBettingRound();

      // Draw Phase:
      // Each player chooses how many cards to discard and replace (draw) from the deck (0-5).
      round.enterDrawPhase();
      ioHandler.displayHandsMidGame(humanPlayers);

      // Second Betting Round:
      // Another round of betting ensues, starting again with the player to the left of the dealer.
      round.playBettingRound();

      // Showdown:
      // If two or more players remain after the second betting round, there is a showdown.
      // Players show their hands, and the player with the best hand wins the pot.
      // If there is a tie, the pot is split equally among the winning players.
      round.enterShowdown();

      // Next Round:
      // The cards from players' hands are disposed, the deck is shuffled, and a new round begins.
      round.disposePlayedCards();
      removeBankruptPlayers();

      // Game over:
      // The game is finished when only one player is left with the money.
    } while (players.stream().filter(player -> !player.isBankrupt()).count() > 1);
    ioHandler.showWinner(
        players.stream().filter(player -> !player.isBankrupt()).findFirst().orElseThrow());
  }

  private void removeBankruptPlayers() {
    var bankruptPlayers = players.stream().filter(Player::isBankrupt).toList();
    bankruptPlayers.forEach(
        player ->
            ioHandler.writeLine("%s went bankrupt and left the game.".formatted(player.getName())));
    players.removeAll(bankruptPlayers);
  }

  @SuppressWarnings("unused")
  private static void printStatisticsAfterGame(List<Ranking> allRankings, int roundCount) {
    System.out.println("Rounds played: " + roundCount);
    var rankOccurrences = new StringBuilder();
    for (var ranking : Ranking.values())
      if (Collections.frequency(allRankings, ranking) > 0)
        rankOccurrences.append(
            "%s: %d%n".formatted(ranking, Collections.frequency(allRankings, ranking)));
    System.out.println(rankOccurrences);
  }

  @SuppressWarnings("unused")
  public static void simulateHandDealsAndReportStatistics(int numberOfHandDeals) {
    System.out.println("Simulation running...");
    Map<Ranking, Integer> rankingsFrequency = new EnumMap<>(Ranking.class);
    int roundCount = 0;
    do {
      var round = new PokerRound(List.of(new ComputerPlayer("Steven")));
      round.dealHandToEachPlayer();
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

  private void shufflePlayers() {
    Collections.shuffle(players);
  }
}
