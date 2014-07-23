// similar with Command pattern

package others.callback;

public class Main {
  public static void main(String[] args) {
    CallbackOperator.executeCallback(new ICallback() {
      @Override
      public void executeCallback() {
        System.out.println(System.currentTimeMillis());
      }
    });
    CallbackOperator.executeCallback(new SayHelloCallback());
  }
}
