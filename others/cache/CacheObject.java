package others.cache;

import java.util.concurrent.TimeUnit;

public class CacheObject {
  private String name;
  public CacheObject(String name) {
    try {
      TimeUnit.MILLISECONDS.sleep(3000);
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
    this.name = name;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
}
