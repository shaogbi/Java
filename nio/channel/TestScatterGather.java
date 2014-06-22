package nio.channel;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class TestScatterGather {
  public static void main(String[] args) throws Exception {
    long now = System.currentTimeMillis();
    String inputFilePath = "C:\\Users\\shaogbi\\Desktop\\EDD Accuracy Improvement White Paper.pdf";
    String outputFilePath = "C:\\Users\\shaogbi\\Desktop\\novels\\EDD Accuracy Improvement White Paper.pdf";
    FileInputStream fis = new FileInputStream(inputFilePath);
    FileOutputStream fos = new FileOutputStream(outputFilePath);
    FileChannel fic = fis.getChannel();
    FileChannel foc = fos.getChannel();
    ByteBuffer buffer1 = ByteBuffer.allocate(1024);
    ByteBuffer buffer2 = ByteBuffer.allocate(1024);
    ByteBuffer[] buffers = { buffer1, buffer2 };
    long index = -1;
    while(true) {
      for(int i=0;i<buffers.length;i++) {
        buffers[i].clear();
      }
      index = fic.read(buffers);
      System.out.printf("Index is now %d.\n", index);
      for(int i=0;i<buffers.length;i++) {
        System.out.printf("Buffer-%d, Capacity: %d, Limit: %d, Position: %d.\n", i, buffers[i].capacity(), buffers[i].limit(), buffers[i].position());
      }
      if(index == -1) {
        break;
      }
      for(int i=0;i<buffers.length;i++) {
        buffers[i].flip();
      }
      foc.write(buffers);
    }
    fic.close();
    foc.close();
    fis.close();
    fos.close();
    System.out.printf("Costs %d milliseconds.\n", System.currentTimeMillis() - now);
  }
}
