package network.webserver.utils;

import java.io.*;

public class Response {
  private Request request;
  private OutputStream output;
  public Response(Request request, OutputStream output) {
    this.request = request;
    this.output = output;
  }
  public void sendStaticResource() throws IOException {
    String uri = request.getParsedUri();
    if(uri != null) {
      File file = new File(CommonConfig.WEB_ROOT, request.getParsedUri());
      if(!file.isFile()) {
        file = new File(CommonConfig.DEFAULT_FILE);
      }
      int s = 0;
      for(int i=0;i<100000;i++) {
        for(int j=0;j<10000;j++) {
          s+=(i+j);
        }
      }
      System.out.println(s);
      s = 0;
      for(int i=0;i<100000;i++) {
        for(int j=0;j<10000;j++) {
          s+=(i+j);
        }
      }
      System.out.println(s);
      System.out.printf("Finding resource: %s.\n", file.getAbsolutePath());
      byte[] bytes = new byte[1024];
      FileInputStream fis = new FileInputStream(file);
      int ch = fis.read(bytes, 0, 1024);
      while (ch!=-1) {
        output.write(bytes, 0, ch);
        ch = fis.read(bytes, 0, 1024);
      }
      fis.close();
    } else {
      System.out.println("Invalid uri, return 404.");
      String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
          "Content-Type: text/html\r\n" +
          "Content-Length: 23\r\n" +
          "\r\n" +
          "<h1>File Not Found</h1>";
      output.write(errorMessage.getBytes());
    }
  }
}
