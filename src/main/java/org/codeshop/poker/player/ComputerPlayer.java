package org.codeshop.poker.player;

import java.util.List;
import org.codeshop.poker.card.Card;

public class ComputerPlayer extends Player {
  public ComputerPlayer(String name) {
    super(name);
  }

  public List<Card> chooseCardsToExchange() {
    return getHand().getOtherCards();
  }
}
