package designpattern.chainofresponsibility;

public interface IHandler {
  public void handleRequest(String message);
  public void setNextHandler(IHandler handler);
}
