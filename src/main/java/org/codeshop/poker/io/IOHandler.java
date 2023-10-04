package org.codeshop.poker.io;

import java.util.List;
import org.codeshop.poker.bet.BettingDecision;
import org.codeshop.poker.bet.BettingInfo;
import org.codeshop.poker.card.Card;
import org.codeshop.poker.player.Player;

public interface IOHandler {
  void write(String message);

  void writeLine(String message);

  String readKey(String possibleKeys);

  void acceptAnte(List<Player> humanPlayers, int ante);

  BettingDecision getBettingDecision(BettingInfo bettingInfo);

  void writeBettingDecision(String name, BettingDecision decision);

  List<Card> readCardsToExchange(Player player);

  void displayHandsMidGame(List<Player> players);

  void displayHandsShowdown(List<Player> players);

  void announceWinners(List<Player> winners);
}
