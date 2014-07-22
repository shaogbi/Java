package designpattern.factory.utils;

public class Banana implements IFruit {
  @Override
  public void plant() {
    System.out.println("Banana is planted.");
  }
  @Override
  public void grow() {
    System.out.println("Banana is growing."); 
  }
}
