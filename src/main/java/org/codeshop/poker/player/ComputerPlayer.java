package org.codeshop.poker.player;

import java.util.List;
import org.codeshop.poker.bet.BettingAction;
import org.codeshop.poker.bet.BettingDecision;
import org.codeshop.poker.bet.BettingInfo;
import org.codeshop.poker.card.Card;

public class ComputerPlayer extends Player {
  public ComputerPlayer(String name) {
    super(name);
  }

  public List<Card> chooseCardsToExchange() {
    return getHand().getOtherCards();
  }

  public BettingDecision makeBettingDecision(BettingInfo bettingInfo) {
    var bid = bettingInfo.roundBid();
    if (super.getMoney() < bid) return new BettingDecision(BettingAction.FOLD, 0);
    if (bid == 0) return new BettingDecision(BettingAction.CHECK, 0);
    return new BettingDecision(BettingAction.CALL, bid);
    // look at your hand
    // look at the pot size
    // look at your money
    // look at the current roundBid
    // if you have good hand and the roundBid is high, you want to ride it
    // if you have good hand and the roundBid is low, you want to raise it inconspicuously
    // if you have low money, you will be more hesitant and careful
    // if you have a lot of money, you will be more investing and risk-taking
    // if you have ok hand, you will play along until the roundBid is too high
    // if you have ok hand, you will sometimes bluff (depending also on the pot size)
    // if you have bad hand, you will sometimes bluff but will fold/check in most cases

    // one strategy is taken or continued and amount is generated
  }
}
