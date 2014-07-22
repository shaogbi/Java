package designpattern.proxy;

public class Main {
  public static void main(String[] args) {
    // use proxy instead of real instance
    IPrinter printer = new PrinterProxy(null);
    printer.printMessage("test proxy pattern");
    printer.printMessage("test proxy pattern");
  }
}
