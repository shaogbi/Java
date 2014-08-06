package designpattern.memento;

import java.util.*;

public class Main {
  public static void main(String[] args) {
    List<Life.Memento> savedTimes = new ArrayList<Life.Memento>();
    Life life = new Life();
    life.setTime("2000 B.C.");
    savedTimes.add(life.createMemento());
    life.setTime("2000 A.D.");
    savedTimes.add(life.createMemento());
    life.setTime("3000 A.D.");
    savedTimes.add(life.createMemento());
    life.setTime("4000 A.D.");
    life.restoreFromMemento(savedTimes.get(0));   
  }
}
