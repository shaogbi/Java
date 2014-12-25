package network.webserver.servers;

import java.io.*;
import java.net.*;
import network.webserver.utils.*;

class Processor implements Runnable {
  private Socket socket;
  public Processor(Socket socket) {
    this.socket = socket;
  }
  @Override
  public void run() {
    try {
      InputStream input = socket.getInputStream();
      OutputStream output = socket.getOutputStream();
      Request request = new Request(input);
      System.out.printf("Get request: \"%s\", processed via socket %d at %d.\n", request.getParsedUri(), socket.hashCode(), System.currentTimeMillis());
      Response response = new Response(request, output);
      response.sendStaticResource();
      input.close();
      output.close();
      socket.close();
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
}

public class MultiThreadServer {
  private int port = 9000;
  private boolean shutdown = false;
  public MultiThreadServer() {}
  public MultiThreadServer(int port) {
    this.port = port;
  }
  public void shutdownServer() {
    shutdown = true;
  }
  public void await() {
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(port);
    } catch(IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
    System.out.printf("Server started, listening port %d...\n", port);
    while(!shutdown) {
      try {
        Socket socket = serverSocket.accept();
        long t1 = System.currentTimeMillis();
        new Thread(new Processor(socket)).start();
        System.out.println("Total " + (System.currentTimeMillis() - t1));
      } catch(IOException e) {
        e.printStackTrace();
      }
    }
  }
  public static void main(String[] args) {
    MultiThreadServer server = new MultiThreadServer();
    server.await();
  }
}
