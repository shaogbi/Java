package others.callback;

public class SayHelloCallback implements ICallback {
  @Override
  public void executeCallback() {
    System.out.println("Hello!");
  }
}
