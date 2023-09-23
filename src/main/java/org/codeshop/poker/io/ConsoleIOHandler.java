package org.codeshop.poker.io;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;
import org.codeshop.poker.bet.BettingAction;
import org.codeshop.poker.bet.BettingDecision;
import org.codeshop.poker.bet.BettingInfo;
import org.codeshop.poker.card.Card;
import org.codeshop.poker.player.Player;

public class ConsoleIOHandler implements IOHandler {
  @Override
  public void write(String message) {
    System.out.print(message);
  }

  @Override
  public void writeLine(String message) {
    System.out.println(message);
  }

  @Override
  public String readKey(String possibleKeys) {
    Scanner in = new Scanner(System.in);
    String key;
    do {
      key = String.valueOf(in.next());
    } while (!possibleKeys.toLowerCase().contains((key))
        && !possibleKeys.toUpperCase().contains((key)));
    return key;
  }

  @Override
  public void acceptAnte(List<Player> humanPlayers, int ante) {
    writeLine("\n- New Round -");
    writeLine("Ante: $%d%n".formatted(ante));
    for (var player : humanPlayers) {
      writeLine(player.getName());
      writeLine("[A] Place ante");
      writeLine("[E] Exit game");
      var key = readKey("AE");
      if (key.equalsIgnoreCase("E")) System.exit(0);
    }
  }

  @Override
  public void displayComputerPlayersHands(List<Player> computerPlayers) {
    computerPlayers.forEach(this::displayHumanPlayerHand);
  }

  @Override
  public void displayHumanPlayersHands(List<Player> humanPlayers) {
    humanPlayers.forEach(this::displayHumanPlayerHand);
  }

  private void displayHumanPlayerHand(Player player) {
    System.out.printf(
        "%s\t[%s]%nBet: $%d  (Total: $%d)%n%n",
        player, player.getHand().getRanking(), player.getCurrentBid(), player.getMoney());
  }

  @Override
  public BettingDecision getBettingDecision(BettingInfo bettingInfo) {
    if (bettingInfo.roundBid() == 0) {
      writeLine(
          "%nYour money: $%d\tBid: $0\tPot: $0%n[C] Check%n[B] Bet%n[F] Fold"
              .formatted(bettingInfo.playerMoney()));
      var key = readKey("CBF");
      if (key.equalsIgnoreCase("C")) {
        return new BettingDecision(BettingAction.CHECK, 0);
      }
      if (key.equalsIgnoreCase("B")) {
        write("Your bet: ");
        var newBid = readMoney(bettingInfo.playerMoney(), 0);
        return new BettingDecision(BettingAction.BET, newBid);
      }
      if (key.equalsIgnoreCase("F")) {
        return new BettingDecision(BettingAction.FOLD, 0);
      }
    } else {
      writeLine(
          "%nYour money: $%d\tPot: $%d%nYour bid: $%d\tCurrent bid: $%d%n[C] Call%n[R] Raise%n[F] Fold"
              .formatted(
                  bettingInfo.playerMoney(),
                  bettingInfo.pot(),
                  bettingInfo.playerBid(),
                  bettingInfo.roundBid()));
      var key = readKey("CRF");
      if (key.equalsIgnoreCase("C")) {
        return new BettingDecision(BettingAction.CALL, bettingInfo.roundBid());
      }
      if (key.equalsIgnoreCase("R")) {
        write("Your bet: ");
        var newBid = readMoney(bettingInfo.playerMoney(), bettingInfo.roundBid());
        return new BettingDecision(BettingAction.RAISE, newBid);
      }
      if (key.equalsIgnoreCase("F")) {
        return new BettingDecision(BettingAction.FOLD, bettingInfo.roundBid());
      }
    }
    return new BettingDecision(BettingAction.CHECK, 0);
  }

  private int readMoney(int playerMoney, int roundBid) {
    Scanner in = new Scanner(System.in);
    int inputMoney;
    do {
      inputMoney = in.nextInt();
    } while (inputMoney > playerMoney && inputMoney <= roundBid);
    return inputMoney;
  }

  @Override
  public void writeBettingDecision(String name, BettingDecision decision) {
    switch (decision.action()) {
      case FOLD -> writeLine("%s folds.".formatted(name));
      case CHECK -> writeLine("%s checks.".formatted(name));
      case CALL -> writeLine("%s calls with $%d.".formatted(name, decision.newBid()));
      case BET -> writeLine("%s bets $%d.".formatted(name, decision.newBid()));
      case RAISE -> writeLine("%s raises to $%d.".formatted(name, decision.newBid()));
    }
  }

  @Override
  public List<Card> readCardsToExchange(Player player) {
    String key;
    Map<Card, Boolean> toExchange = new HashMap<>();
    player.getHand().getCardsInRank().forEach(card -> toExchange.put(card, false));
    player.getHand().getOtherCards().forEach(card -> toExchange.put(card, false));
    var hand =
        toExchange.keySet().stream()
            .sorted(Comparator.comparing(Card::getRank).reversed())
            .toList();
    do {
      displayHumanPlayerHand(player);
      writeLine("Which cards to exchange?");
      IntStream.range(0, toExchange.size())
          .forEach(
              i ->
                  writeLine(
                      "[%d] %s (%s)"
                          .formatted(
                              i + 1,
                              hand.get(i),
                              Boolean.TRUE.equals(toExchange.get(hand.get(i))) ? "X" : " ")));
      writeLine("");
      writeLine("[A] Accept");
      key = readKey("12345A");
      if (!key.equalsIgnoreCase("a")) {
        var pickedCard = hand.get(Integer.parseInt(key) - 1);
        toExchange.put(pickedCard, !toExchange.get(pickedCard));
      }
    } while (!key.equalsIgnoreCase("a"));
    return toExchange.entrySet().stream()
        .filter(Map.Entry::getValue)
        .map(Map.Entry::getKey)
        .toList();
  }
}
