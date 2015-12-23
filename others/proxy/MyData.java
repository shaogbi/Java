package others.proxy;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class MyData implements IDataInterface {
    private String data;
    
    @Override
    public void setData(String data) {
        System.out.printf("Set data: %s.\n", data);
        this.data = data;
    }
    
    @Override
    public String getData() {
        System.out.println("Get data.");
        return String.valueOf(data);
    }
    
    @Override
    public Future<String> getFuture() {
        Callable<String> callMe = () -> {
            TimeUnit.SECONDS.sleep(5);
            return getData();
        };
        FutureTask<String> task = new FutureTask<String>(callMe);
        task.run(); // must call run(), or it will block forever!
        return task;
    }
}
