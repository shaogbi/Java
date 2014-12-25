package nio.selector;

import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.*;

public class NIOServer {
  private Selector selector;
  public void initServer(int port) throws Exception {
    ServerSocketChannel serverChannel = ServerSocketChannel.open();
    serverChannel.configureBlocking(false);
    serverChannel.socket().bind(new InetSocketAddress(port));
    this.selector = Selector.open();
    serverChannel.register(selector, SelectionKey.OP_ACCEPT);
  }
  private void read(SelectionKey key) throws Exception {
    SocketChannel channel = (SocketChannel)key.channel();
    ByteBuffer buffer = ByteBuffer.allocate(100);
    channel.read(buffer);
    byte[] data = buffer.array();
    String msg = new String(data).trim();
    System.out.printf("Server receive from client: %s.\n", msg);
    ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
    channel.write(outBuffer);
  }
  public void listen() throws Exception {
    System.out.println("Server started:");
    while(true) {
      selector.select();
      Iterator<?> it = this.selector.selectedKeys().iterator();
      while(it.hasNext()) {
        SelectionKey key = (SelectionKey)it.next();
        it.remove();
        if(key.isAcceptable()) {
          ServerSocketChannel server = (ServerSocketChannel)key.channel();
          SocketChannel channel = server.accept();
          channel.configureBlocking(false);
          channel.write(ByteBuffer.wrap(new String("hello client").getBytes()));
          channel.register(this.selector, SelectionKey.OP_READ);
        } else if(key.isReadable()) {
          read(key);
        } else if(key.isConnectable()) {
          System.out.printf("%s is connectable.\n", key);
        }
      }
      TimeUnit.SECONDS.sleep(2);
    }
  }
  public static void main(String[] args) throws Throwable {
    NIOServer server = new NIOServer();
    server.initServer(8989);
    server.listen();
  }
}
