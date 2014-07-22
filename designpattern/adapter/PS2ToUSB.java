package designpattern.adapter;

public class PS2ToUSB implements IUSBPort {
  private IPS2Port ps2Port;
  public PS2ToUSB(IPS2Port ps2Port) {
    this.ps2Port = ps2Port;
  }
  @Override
  public void workWithUSB() {
    System.out.println("Before using adapter, work with PS2Port.");
    ps2Port.workWithPS2();
    System.out.println("After using adapter, work with USB.");
  }
}
