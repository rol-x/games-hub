package org.codeshop.poker.player;

import lombok.Getter;
import org.codeshop.poker.card.Card;
import org.codeshop.poker.card.Hand;

@Getter
public abstract class Player {

  private final String name;
  private int money;
  private Hand hand;

  protected Player(String name) {
    this.name = name;
    this.hand = new Hand();
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

  public void lose(int lossAmount) {
    this.money -= lossAmount;
  }

  public void win(int winAmount) {
    this.money += winAmount;
  }

  public boolean isPlaying() {
    return this.money > 0;
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
