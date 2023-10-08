package org.codeshop;

import org.codeshop.poker.PokerGame;
import org.codeshop.poker.io.ConsoleIOHandler;

public class Main {
  public static void main(String[] args) {
    var poker = new PokerGame(new ConsoleIOHandler());
    poker.addComputerPlayer("Stephen");
    poker.addComputerPlayer("Maria");
    poker.addHumanPlayer("Charles");
    poker.play();
  }
}
