package others.proxy;

import java.util.concurrent.Future;

public interface IDataInterface {
    public void setData(String data);
    public String getData();
    public Future<String> getFuture();
}
