package designpattern.chainofresponsibility;

public class HTMLHandler implements IHandler {
  public IHandler handler = null;
  @Override
  public void handleRequest(String message) {
    if(message.contains("HTML")) {
      System.out.println("HTML handler can handle HTML.");
    } else {
      System.out.printf("HTML handler can't handle %s, passing to next handler.\n", message);
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
