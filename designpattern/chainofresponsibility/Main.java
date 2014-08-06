package designpattern.chainofresponsibility;

public class Main {
  public static void main(String[] args) {
    IHandler htmlHandler = new HTMLHandler();
    IHandler jsHandler = new JSHandler();
    IHandler cssHandler = new CSSHandler();
    htmlHandler.setNextHandler(jsHandler);
    jsHandler.setNextHandler(cssHandler);
    htmlHandler.handleRequest("JS");
    htmlHandler.handleRequest("UNKNOWN");
  }
}
