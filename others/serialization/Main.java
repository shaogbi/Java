package others.serialization;

import java.io.*;

public class Main implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  public Main() {
    this.name = "An Object";
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public static void main(String[] args) throws IOException, ClassNotFoundException {
    for(int i=0;i<10;i++) {
      long now = System.currentTimeMillis();
      Main obj = new Main();
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(obj);
      oos.close();
      baos.close();
      byte[] bytes = baos.toByteArray();
      System.out.printf("Cost %dms, size is %d bytes.\n", System.currentTimeMillis() - now, bytes.length);
      now = System.currentTimeMillis();
      ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
      ObjectInputStream ois = new ObjectInputStream(bais);
      obj = (Main)ois.readObject();
      System.out.println(obj.getName());
      ois.close();
      bais.close();
      System.out.printf("Cost %dms, size is %d bytes.\n", System.currentTimeMillis() - now, bytes.length);
    }
  }
}
