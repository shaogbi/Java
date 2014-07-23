package others.callback;

public class CallbackOperator {
  public static void executeCallback(ICallback callback) {
    System.out.println("Following will execute callback:");
    callback.executeCallback();
  }
}
