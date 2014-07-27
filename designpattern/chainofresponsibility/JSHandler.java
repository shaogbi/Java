package designpattern.chainofresponsibility;

public class JSHandler implements IHandler {
  public IHandler handler = null;
  @Override
  public void handleRequest(String message) {
    if(message.contains("JS")) {
      System.out.println("JS handler can handle JS.");
    } else {
      System.out.printf("JS handler can't handle %s, passing to next handler.\n", message);
      if(handler != null) {
        handler.handleRequest(message);
      } else {
        System.out.printf("No handler can handle this message: %s.\n", message);
      }
    }
  }
  @Override
  public void setNextHandler(IHandler handler) {
    this.handler = handler;
  }
}
