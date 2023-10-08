package org.codeshop.poker.player;

import lombok.Getter;
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
    money = 1000;
    hand = new Hand();
    currentBid = 0;
  }

  public void takeCard(Card card) {
    hand.add(card);
  }

  public void rankHand() {
    hand.rankCards();
  }

  public void disposeHand() {
    hand = new Hand();
  }

  public void win(int winAmount) {
    money += winAmount;
  }

  public void ante(int ante) {
    money -= ante;
  }

  public void bet(int playerBet) {
    money -= playerBet;
    currentBid += playerBet;
  }

  public void resetCurrentBid() {
    currentBid = 0;
  }

  public boolean isBankrupt() {
    return money <= 0;
  }

  public void disposeCard(Card card) {
    hand.removeCard(card);
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
}
