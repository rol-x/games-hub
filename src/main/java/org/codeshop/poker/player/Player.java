package org.codeshop.poker.player;

import lombok.Getter;
import org.codeshop.poker.bet.BettingAction;
import org.codeshop.poker.bet.BettingDecision;
import org.codeshop.poker.bet.BettingInfo;
import org.codeshop.poker.card.Card;
import org.codeshop.poker.card.Hand;

@Getter
public abstract class Player {

  private final String name;
  private int money;
  private Hand hand;
  private int currentBid;

  protected Player(String name) {
    this.name = name;
    this.money = 1000;
    this.hand = new Hand();
    this.currentBid = 0;
  }

  public void takeCard(Card card) {
    this.hand.add(card);
  }

  public void rankHand() {
    this.hand.rankCards();
  }

  public void disposeHand() {
    this.hand = new Hand();
  }

  public void win(int winAmount) {
    this.money += winAmount;
  }

  public void ante(int ante) {
    this.money -= ante;
  }

  public void bet(int playerBet) {
    this.money -= playerBet;
    this.currentBid += playerBet;
  }

  public void resetCurrentBid() {
    this.currentBid = 0;
  }

  public boolean isBankrupt() {
    return this.money <= 0;
  }

  @Override
  public String toString() {
    var result = new StringBuilder(name);
    result.append("\t[");
    for (var card : hand.getCardsInRank()) {
      result.append(card).append(" ");
    }
    for (var card : hand.getOtherCards()) {
      result.append(card).append(" ");
    }
    result.deleteCharAt(result.length() - 1);
    result.append("]");
    return result.toString();
  }

  public BettingDecision makeBettingDecision(BettingInfo bettingInfo) {
    var bid = bettingInfo.roundBid();
    if (money < bid) return new BettingDecision(BettingAction.FOLD, 0);
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
