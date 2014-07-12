package algorithm.fundamental;

/* recommend use "java.math.BigInteger"
BigInteger x = new BigInteger("11");
BigInteger y = new BigInteger("10");
long l = x.longValue();
BigInteger z = x.subtract(y);
z = x.add(y);
z = x.multiply(y);
z = x.pow(1000);
System.out.println(z);
*/

public class BigIntegerSummator {
  public static String sum(String x, String y) {
    char[] a = (new StringBuffer(x)).reverse().toString().toCharArray();
    char[] b = (new StringBuffer(y)).reverse().toString().toCharArray();
    int n1 = a.length, n2 = b.length;
    int n = Math.min(n1, n2), nn = Math.max(n1, n2);
    char[] c = new char[nn + 1];
    int carry = 0;
    for(int i=0;i<n;i++) {
      int te = a[i] - '0' + b[i] - '0' + carry;
      c[i] = (char)(te % 10 + '0');
      carry = te / 10;
    }
    if(n == n1) {
      for(int i=n;i<n2;i++) {
        int te = b[i] - '0' + carry;
        c[i] = (char)(te % 10 + '0');
        carry = te / 10;
      }
    } else {
      for(int i=n;i<n1;i++) {
        int te = a[i] - '0' + carry;
        c[i] = (char)(te % 10 + '0');
        carry = te / 10;
      }
    }
    if(carry > 0) {
      c[nn] = (char)('0' + carry);
    }
    return (new StringBuffer(String.valueOf(c))).reverse().toString().trim();
  }
  public static void main(String[] args) {
    System.out.println(BigIntegerSummator.sum("13", "25"));
    System.out.println(BigIntegerSummator.sum("93", "25"));
    System.out.println(BigIntegerSummator.sum("93", "8"));
  }
}
