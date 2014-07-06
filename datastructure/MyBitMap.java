/* usage:
    // this is similar with BitSet supported by java.util.BitSet
    int[] arr = {6, 13, 25, 21, 20, 18, 33, 67, 54, 3};
    MyBitMap myBitMap = new MyBitMap();
    myBitMap.add(4);
    myBitMap.add(7);
    myBitMap.add(2);
    myBitMap.add(5);
    myBitMap.add(3);
    System.out.println(myBitMap.exists(2));
    System.out.println(myBitMap.exists(6));
    System.out.println(myBitMap.exists(3));
    myBitMap.remove(119);
    myBitMap.remove(120);
    System.out.println(myBitMap.exists(119));
    System.out.println(myBitMap.exists(120));
    myBitMap.outputSortedNumbers(arr);
*/

package datastructure;

public class MyBitMap {
  private byte[] bytes;
  private int len = Integer.MAX_VALUE / 8;
  public MyBitMap() {
    bytes = new byte[len];
    /* this part can be omitted to save a little time
    for(int i=0;i<len;i++) {
      bytes[i]=0;
    }
    */
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
  }
  public boolean exists(int x) {
    return ((bytes[getIndex(x)] >> getPosition(x)) & 1) == 1;
  }
}
