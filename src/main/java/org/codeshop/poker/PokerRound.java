package org.codeshop.poker;

import java.util.Comparator;
import java.util.List;
import org.codeshop.poker.bet.BettingDecision;
import org.codeshop.poker.bet.BettingInfo;
import org.codeshop.poker.card.Card;
import org.codeshop.poker.card.Dealer;
import org.codeshop.poker.card.Hand;
import org.codeshop.poker.card.Ranking;
import org.codeshop.poker.io.ConsoleIOHandler;
import org.codeshop.poker.io.IOHandler;
import org.codeshop.poker.player.ComputerPlayer;
import org.codeshop.poker.player.Player;
import org.codeshop.poker.tool.Tiebreaker;

public class PokerRound {
  private int pot;
  private final List<Player> players;
  private final Dealer dealer;
  private final Tiebreaker tiebreaker;
  private final IOHandler ioHandler = new ConsoleIOHandler();

  public PokerRound(List<Player> players) {
    this.players = players;
    this.dealer = new Dealer();
    this.tiebreaker = new Tiebreaker();
    this.pot = 0;
  }

  public void dealHandToEachPlayer() {
    for (int i = 0; i < 5; i++) {
      for (var player : players) {
        var card = dealer.dealCard();
        player.takeCard(card);
      }
    }
    players.forEach(Player::rankHand);
  }

  public Ranking findWinningRanking() {
    return getPlayersInGame().stream()
        .map(Player::getHand)
        .max(Comparator.comparing(Hand::getRanking))
        .map(Hand::getRanking)
        .orElse(null);
  }

  public List<Player> findWinningPlayers() {
    var winningRanking = findWinningRanking();
    var contestingPlayers =
        getPlayersInGame().stream()
            .filter(player -> player.getHand().getRanking().equals(winningRanking))
            .toList();
    if (contestingPlayers.size() == 1) return contestingPlayers;
    return tiebreaker.findWinnersInTie(contestingPlayers);
  }

  public void disposePlayedCards() {
    players.forEach(Player::disposeHand);
  }

  public void collectAnteFromPlayers(int ante) {
    players.forEach(player -> player.ante(ante));
    this.pot += players.size() * ante;
  }

  public void playBettingRound() {
    // Check: If no bet has been made in the current betting round, a player may pass on betting
    // by choosing to "check". If all active players check, the round is considered complete.
    // Bet: If no bet has been made, a player can choose to "bet" and wager a certain amount of
    // chips.
    // This establishes the current bet amount for the round.
    // Fold: If a player does not want to match the current bet, they can choose to "fold",
    // thereby forfeiting their hand and exiting the round.
    // Call: If a bet has been made, a player can "call" to match the amount of the current bet,
    // indicating they wish to continue in the round.
    // Raise: After a bet has been made, a player can "raise" to increase the amount of the current
    // bet.
    // All other players must then either call the new bet, raise again, or fold.

    var roundBid = 0;
    var bettingFinished = true;
    do {
      for (var player : getPlayersInGame()) {
        // Check if the round roundBid "returned" to the player who raised it
        if (player.getCurrentBid() == roundBid && roundBid > 0) {
          bettingFinished = true;
          break;
        }

        BettingDecision decision;
        var bettingInfo = new BettingInfo(player.getMoney(), roundBid, player.getCurrentBid(), pot);
        if (player instanceof ComputerPlayer computerPlayer) {
          decision = computerPlayer.makeBettingDecision(bettingInfo);
        } else {
          decision = ioHandler.getBettingDecision(bettingInfo);
        }
        ioHandler.writeBettingDecision(player.getName(), decision);

        // Update the roundBid (money each player has to bet), pot (money to win) and player money
        switch (decision.action()) {
          case FOLD -> player.getHand().fold();
          case BET -> {
            bettingFinished = false;
            roundBid = decision.newBid();
            player.bet(decision.newBid());
            pot += decision.newBid();
          }
          case CALL -> {
            var remainingDifference = roundBid - player.getCurrentBid();
            player.bet(remainingDifference);
            pot += remainingDifference;
          }
          case RAISE -> {
            bettingFinished = false;
            var playerBet = decision.newBid() - player.getCurrentBid();
            roundBid = decision.newBid();
            player.bet(playerBet);
            pot += playerBet;
          }
          default -> {}
        }
        ioHandler.writeLine("Bid: $%d\tPot: $%d".formatted(roundBid, pot));
      }
    } while (!bettingFinished);
    players.forEach(Player::resetCurrentBid);
  }

  private List<Player> getPlayersInGame() {
    return players.stream().filter(player -> !player.getHand().isFolded()).toList();
  }

  public void enterDrawPhase() {
    ioHandler.writeLine("");
    for (var player : getPlayersInGame()) {
      List<Card> cardsToExchange;
      if (player instanceof ComputerPlayer computerPlayer) {
        cardsToExchange = computerPlayer.chooseCardsToExchange();
      } else {
        cardsToExchange = ioHandler.readCardsToExchange(player);
      }
      player.disposeCardsToExchange(cardsToExchange);
      for (var __ : cardsToExchange) {
        var card = dealer.dealCard();
        player.takeCard(card);
      }
      player.getHand().rankCards();
      ioHandler.writeLine(
          "%s exchanged %d cards.".formatted(player.getName(), cardsToExchange.size()));
    }
  }

  public void enterShowdown() {
    ioHandler.displayHandsShowdown(players);
    var winners = findWinningPlayers();
    winners.forEach(winner -> winner.win(pot / winners.size()));
    ioHandler.announceWinners(winners);
  }
}
