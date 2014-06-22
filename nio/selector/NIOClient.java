package nio.selector;

import java.io.IOException;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class NIOClient {
  private Selector selector;
  public void initClient(String ip, int port) throws IOException {
    SocketChannel channel = SocketChannel.open();
    channel.configureBlocking(false);
    this.selector = Selector.open();
    // 用channel.finishConnect();才能完成连接
    channel.connect(new InetSocketAddress(ip, port));
    channel.register(selector, SelectionKey.OP_CONNECT);
  }
  private void read(SelectionKey key) throws Exception {
    SocketChannel channel = (SocketChannel) key.channel();
    ByteBuffer buffer = ByteBuffer.allocate(100);
    channel.read(buffer);
    byte[] data = buffer.array();
    String msg = new String(data).trim();
    System.out.printf("Client receive msg from server: %s.\n", msg);
    ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
    channel.write(outBuffer);
  }
  public void listen() throws Exception {
    while (true) {
      selector.select();
      Iterator<?> ite = this.selector.selectedKeys().iterator();
      while (ite.hasNext()) {
        SelectionKey key = (SelectionKey)ite.next();
        ite.remove();
        if(key.isConnectable()) {
          SocketChannel channel = (SocketChannel)key.channel();
          if (channel.isConnectionPending()) {
            channel.finishConnect();
          }
          channel.configureBlocking(false);
          channel.write(ByteBuffer.wrap(new String("hello server").getBytes()));
          channel.register(this.selector, SelectionKey.OP_READ);
        } else if(key.isReadable()) {
          read(key);
        }
      }
    }
  }
  public static void main(String[] args) throws Exception {
    NIOClient client = new NIOClient();
    client.initClient("localhost", 8989);
    client.listen();
  }
}
