package others.callback;

public class CallbackOperator {
    public static void executeCallback(ICallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Following will execute callback:");
                callback.executeCallback();
            }
        }).start();
        System.out.println("After calling callback, let's wait for callback's result...");
    }
}
