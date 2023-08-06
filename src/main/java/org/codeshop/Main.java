package org.codeshop;

import org.codeshop.poker.PokerGame;

public class Main {
  public static void main(String[] args) {
    var poker = new PokerGame();
    poker.addComputerPlayer("Stephen");
    poker.addComputerPlayer("Maria");
    poker.addHumanPlayer("Charles");
    poker.play();
  }
}
