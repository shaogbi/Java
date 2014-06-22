package nio.buffer;

import java.nio.*;

public class TestSlice {
  private static final String FOOTER = "****************************************************************";
  private static void outputBufferInfo(ByteBuffer buffer, String title) {
    buffer.position(0);
    buffer.limit(buffer.capacity());
    System.out.printf("%s\n%s: %s.\n", FOOTER, title, buffer);
    while(buffer.remaining() > 0) {
      System.out.println(buffer.get());
    }
    System.out.println(FOOTER);
  }
  public static void main(String[] args) {
    ByteBuffer buffer = ByteBuffer.allocate(6);
    for(int i=0;i<buffer.capacity();i++) {
      buffer.put((byte)i);
    }
    outputBufferInfo(buffer, "Initial Buffer is");
    buffer.position(2);
    buffer.limit(4);
    ByteBuffer slice = buffer.slice();
    /* if so, will get Exception once updating slice
    slice = slice.asReadOnlyBuffer();
    */
    outputBufferInfo(slice, "Initial SliceBuffer is");
    for(int i=0;i<slice.capacity();i++) {
      byte b = slice.get(i);
      b *= 3;
      System.out.printf("Put %d into SliceBuffer at position %d.\n", b, i);
      slice.put(i, b);
    }
    outputBufferInfo(slice, "Now SliceBuffer is");
    outputBufferInfo(buffer, "Now Buffer is");
  }
}
