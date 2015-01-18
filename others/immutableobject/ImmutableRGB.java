package others.immutableobject;

import java.util.Date;

public final class ImmutableRGB {
  private final int red;
  private final int green;
  private final int blue;
  private final String name;
  private final Date createDate;
  public ImmutableRGB(int red, int green, int blue, String name, Date createDate) {
    check(red, green, blue);
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.name = name;
    this.createDate = new Date(createDate.getTime());
  }
  private void check(int red, int green, int blue) {
    if(red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
      throw new IllegalArgumentException();
    }
  }
  public int getRGB() {
    return ((red << 16) | (green << 8) | blue);
  }
  public String getName() {
    return name;
  }
  public Date getCreateDate() {
    // Date is immutable, so return a new Date instance
    // if use "return createDate;":
    // Date date = rgb.getCreateDate();
    // date.setYear(2015); // ImmutableRGB instance rgb also changes!
    return new Date(createDate.getTime()); 
  }
  public ImmutableRGB invert() {
    return new ImmutableRGB(255 - red, 255 - green, 255 - blue, "Inverse of " + name, new Date(createDate.getTime()));
  }
}
