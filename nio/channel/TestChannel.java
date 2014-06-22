package nio.channel;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class TestChannel {
  public static void main(String[] args) throws Exception {
    long now = System.currentTimeMillis();
    String inputFilePath = "C:\\Users\\shaogbi\\Desktop\\EDD Accuracy Improvement White Paper.pdf";
    //String outputFilePath = inputFilePath + ".tmp";
    String outputFilePath = "C:\\Users\\shaogbi\\Desktop\\novels\\EDD Accuracy Improvement White Paper.pdf";
    FileInputStream fis = new FileInputStream(inputFilePath);
    FileOutputStream fos = new FileOutputStream(outputFilePath);
    FileChannel fic = fis.getChannel();
    FileChannel foc = fos.getChannel();
    ByteBuffer buffer = ByteBuffer.allocate(1024*10);
    /* this way is faster
    ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
    */
    int index = -1;
    while(true) {
      buffer.clear();
      index = fic.read(buffer);
      System.out.printf("Index is now %d, buffer info: %s.\n", index, buffer);
      System.out.printf("Capacity: %d, Limit: %d, Position: %d.\n", buffer.capacity(), buffer.limit(), buffer.position());
      System.out.printf("Now input channel size is %d bytes, output channel size is %d bytes.\n", fic.size(), foc.size());
      if(index == -1) {
        break;
      }
      buffer.flip();
      System.out.printf("After flip, buffer info is: %s.\n", buffer);
      foc.write(buffer);
      System.out.printf("After write, buffer info is: %s.\n", buffer);
    }
    fic.close();
    foc.close();
    fis.close();
    fos.close();
    System.out.printf("Costs %d milliseconds.\n", System.currentTimeMillis() - now);
  }
}
