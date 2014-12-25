package datastructure;

public class MyBitMap {
  private byte[] bytes;
  private int len = Integer.MAX_VALUE / 8;
  public MyBitMap() {
    long now = System.currentTimeMillis();
    bytes = new byte[len];
    System.out.println(System.currentTimeMillis() - now);
    now = System.currentTimeMillis();
    // this part can be omitted to save a little time
    for(int i=0;i<len;i++) {
      bytes[i]=0;
    }
    
    System.out.println(System.currentTimeMillis() - now);
  }
  private int getIndex(int x) {
    return x / 8;
  }
  private int getPosition(int x) {
    return x & 7; // means x%8
  }
  public void outputSortedNumbers(int[] arr) {
    MyBitMap myBitMap = new MyBitMap();
    int arrLen = arr.length;
    for(int i=0;i<arrLen;i++) {
      myBitMap.add(arr[i]);
    }
    int te = 0;
    for(int i=0;i<len;i++) {
      for(int j=0;j<8;j++) {
        if(((myBitMap.bytes[i] >> j) & 1) == 1) {
          System.out.printf("%d ", te + j);
        }
      }
      te += 8;
    }
    System.out.println();
  }
  public void remove(int x) {
    bytes[getIndex(x)] &= ~(1 << getPosition(x));
  }
  public void add(int x) {
    bytes[getIndex(x)] |= (1 << getPosition(x));
    /*
    int id = getIndex(x);
    bytes[id] = (byte) (bytes[id] | (1 << getPosition(x)));
    */
  }
  public boolean exists(int x) {
    return ((bytes[getIndex(x)] >> getPosition(x)) & 1) == 1;
  }
  public static void main(String[] args) {
    MyBitMap m = new MyBitMap();
    m.add(4);
    m.add(7);
    m.add(2);
    m.add(5);
    m.add(3);
    System.out.println(m.exists(2));
    System.out.println(m.exists(6));
    System.out.println(m.exists(3));
    
    m.add(103);
    m.add(128);
    m.add(127);
    m.add(120);
    m.add(119);
    System.out.println(m.exists(103));
    System.out.println(m.exists(105));
    System.out.println(m.exists(120));
    
    m.remove(119);
    System.out.println(m.exists(119));
    m.remove(120);
    System.out.println(m.exists(120));
    
    long now = System.currentTimeMillis();
    for(int i=0;i<100000000;i++) {
      m.add(i);
    }
    System.out.println(System.currentTimeMillis() - now);
    
    /*
    int[] arr = {6, 13, 25, 21, 20, 18, 33, 67, 54, 3};
    now = System.currentTimeMillis();
    m.outputSortedNumbers(arr);
    System.out.println(System.currentTimeMillis() - now);
    BitSet bs = new BitSet();
    */
  }
}
