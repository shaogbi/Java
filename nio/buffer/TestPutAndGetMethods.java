package nio.buffer;

import java.nio.*;

public class TestPutAndGetMethods {
  private static final int EIGHT = 8;
  private static int getSize(int bits) {
    return bits / EIGHT;
  }
  public static void main(String[] args) throws Exception {
    ByteBuffer buffer = ByteBuffer.allocate(20); // if less than 20(which is 4 + 8 + 8), will get exception
    buffer.putInt(30);
    buffer.putLong(7000000000000L);
    buffer.putDouble(Math.PI);
    buffer.flip();
    System.out.printf("Integer is %d bytes, Long is %d bytes, Double is %d bytes.\n", getSize(Integer.SIZE), getSize(Long.SIZE), getSize(Double.SIZE));
    System.out.println(buffer.getInt());
    System.out.println(buffer.getLong());
    System.out.println(buffer.getDouble());
  }
}
