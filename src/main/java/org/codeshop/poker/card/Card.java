package org.codeshop.poker.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Card {
  private Suit suit;
  private Rank rank;

  @Override
  public String toString() {
    String result =
        switch (rank.getValue()) {
          case 11 -> "J";
          case 12 -> "Q";
          case 13 -> "K";
          case 14 -> "A";
          default -> String.valueOf(rank.getValue());
        };
    switch (suit) {
      case DIAMONDS -> result += "♦";
      case HEARTS -> result += "♥";
      case CLUBS -> result += "♣";
      case SPADES -> result += "♠";
    }
    return result;
  }
}
