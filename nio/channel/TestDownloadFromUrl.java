package nio.channel;

import java.io.*;
import java.net.*;
import java.nio.channels.*;

public class TestDownloadFromUrl {
  public static void main(String[] args) {
    FileOutputStream fos = null;
    try {
      URL url = new URL("http://www.baidu.com/");
      ReadableByteChannel rbc = Channels.newChannel(url.openStream());
      String fileName = "C:\\Users\\shaogbi\\Desktop\\res\\" + System.currentTimeMillis();
      fos = new FileOutputStream(fileName);
      fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    } catch(Exception e) {
      e.printStackTrace();
    } finally {
      if(fos != null) {
        try {
          fos.close();
        } catch(IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
