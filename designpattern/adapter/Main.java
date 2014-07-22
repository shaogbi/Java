package designpattern.adapter;

public class Main {
  public static void main(String[] args) {
    IPS2Port ps2Port = new IPS2Port() {
      @Override
      public void workWithPS2() {
        System.out.println("I'm working with PS2Port.");
      }
    };
    IUSBPort ps2ToUSB = new PS2ToUSB(ps2Port);
    ps2ToUSB.workWithUSB();
  }
}
