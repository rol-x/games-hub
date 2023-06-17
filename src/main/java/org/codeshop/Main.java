package org.codeshop;

import org.codeshop.poker.Poker;

public class Main {
  public static void main(String[] args) {
    var poker = new Poker();
    poker.addComputerPlayer("Stephen");
    poker.addComputerPlayer("Maria");
    poker.addHumanPlayer("Charles");
    poker.play();
  }
}
