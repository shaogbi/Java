package network.webserver.utils;

import java.io.*;

public class Request {
  private InputStream input;
  private String uri = null;
  public Request(InputStream input) {
    this.input = input;
  }
  public String getParsedUri() {
    if(uri != null) {
      return uri;
    }
    StringBuffer request = new StringBuffer(2048);
    int i;
    byte[] buffer = new byte[2048];
    try {
      i = input.read(buffer);
    } catch(IOException e) {
      e.printStackTrace();
      return null;
    }
    for(int j=0;j<i;j++) {
      request.append((char)buffer[j]);
    }
    String requestStr = request.toString();
    int index1, index2;
    index1 = requestStr.indexOf(" ");
    if(index1 != -1) {
      index2 = requestStr.indexOf(" ", index1 + 1);
      if(index2 > index1) {
        uri = requestStr.substring(index1 + 1, index2);
      }
    }
    return uri;
  }
}
