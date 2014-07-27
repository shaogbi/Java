package designpattern.chainofresponsibility;

public class CSSHandler implements IHandler {
  public IHandler handler = null;
  @Override
  public void handleRequest(String message) {
    if(message.contains("CSS")) {
      System.out.println("CSS handler can handle CSS.");
    } else {
      System.out.printf("CSS handler can't handle %s, passing to next handler.\n", message);
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
