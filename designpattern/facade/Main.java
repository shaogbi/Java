package designpattern.facade;

public class Main {
  public static void main(String[] args) {
    Facade facade = new Facade();
    facade.serveFood("chicken", 10.0);
  }
}
