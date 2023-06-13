package org.codeshop.poker.player;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import org.codeshop.poker.card.Card;
import org.codeshop.poker.card.RankedCards;
import org.codeshop.poker.card.Ranking;

@Getter
public abstract class Player {

  private final String name;
  private int money;
  private final Set<Card> hand = new HashSet<>();
  private final PokerHandEvaluator evaluator = new PokerHandEvaluator();

  protected Player(String name) {
    this.name = name;
  }

  public void takeCard(Card card) {
    this.hand.add(card);
  }

  public RankedCards rankHand() {
    return evaluator.evaluate(hand);
  }

  public void disposeHand() {
    this.hand.clear();
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
    result.append(" [");
    for (var card : hand) {
      result.append(card).append(" ");
    }
    result.deleteCharAt(result.length() - 1);
    result.append("]");
    return result.toString();
  }
}
