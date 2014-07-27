package designpattern.chainofresponsibility;

public class Main {
  public static void main(String[] args) {
    HTMLHandler htmlHandler = new HTMLHandler();
    JSHandler jsHandler = new JSHandler();
    CSSHandler cssHandler = new CSSHandler();
    htmlHandler.setNextHandler(jsHandler);
    jsHandler.setNextHandler(cssHandler);
    htmlHandler.handleRequest("JS");
    htmlHandler.handleRequest("UNKNOWN");
  }
}
