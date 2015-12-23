package others.proxy;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {
        IDataInterface myData = (IDataInterface)Proxy.newProxyInstance(
                                                        IDataInterface.class.getClassLoader(),
                                                        new Class<?>[] { IDataInterface.class },
                                                        new MyInvocationHandler(new MyData()));
        myData.setData("abc");
        String data = myData.getData();
        System.out.println(data);
        Future<String> future = myData.getFuture();
        try {
            System.out.println(future.get());
        } catch(InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

/*
Output:
[Proxy] Will call method: setData
Set data: abc.
[Proxy] After method call.
[Proxy] Will call method: getData
Get data.
[Proxy] After method call.
abc
[Proxy] Will call method: getFuture
// after 5 seconds...
Get data.
[Proxy] After method call.
abc
*/