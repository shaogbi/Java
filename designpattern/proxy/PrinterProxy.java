package designpattern.proxy;

public class PrinterProxy implements IPrinter {
  private IPrinter printer;
  public PrinterProxy(IPrinter printer) {
    this.printer = printer;
  }
  @Override
  public void printMessage(String message) {
    if(printer == null) {
      printer = new Printer();
    }
    System.out.println("Hi, I'm the proxy of printer.");
    printer.printMessage(message);
  }
}
