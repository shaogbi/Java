package nio.channel;

import java.io.*;
import java.nio.channels.*;

public class TestTransfer {
  public static void main(String[] args) throws Exception {
    long now = System.currentTimeMillis();
    RandomAccessFile fromFile = new RandomAccessFile("C:\\Users\\shaogbi\\Desktop\\novels\\EDD Accuracy Improvement White Paper.pdf", "rw");
    RandomAccessFile toFile = new RandomAccessFile("C:\\Users\\shaogbi\\Desktop\\to.pdf", "rw");
    FileChannel fromChannel = fromFile.getChannel();
    FileChannel toChannel = toFile.getChannel();
    toChannel.transferFrom(fromChannel, 0, fromChannel.size());
    fromChannel.close();
    toChannel.close();
    fromFile.close();
    toFile.close();
    System.out.printf("Costs %d milliseconds.\n", System.currentTimeMillis() - now);
  }
}
