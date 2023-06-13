package org.codeshop;

import org.codeshop.poker.Poker;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
  public static void main(String[] args) {
    var poker = new Poker();
    poker.addComputerPlayer("Steven");
    poker.addComputerPlayer("Maria");
    poker.addHumanPlayer("Charles");
    poker.play();
  }
}
