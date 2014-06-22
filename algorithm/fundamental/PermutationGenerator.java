package algorithm.fundamental;

public class PermutationGenerator {
  // A(n, n)
  private static void outputPermutations(int[] arr) {
    int n = arr.length;
    int i, j, te, x, y;
    while(true) {
      for(i=0;i<n;i++) {
        System.out.printf("%d ", arr[i]);
      }
      System.out.println();
      for(i=n-1;i>0;i--) {
        if(arr[i] > arr[i-1]) {
          break;
        }
      }
      if(i == 0) {
        break;
      }
      for(i--,j=n-1;j>i;j--) {
        if(arr[j] > arr[i]) {
          te = arr[i];
          arr[i] = arr[j];
          arr[j] = te;
          break;
        }
      }
      for(x=i+1,y=n-1;x<y;x++,y--) {
        te = arr[x];
        arr[x] = arr[y];
        arr[y] = te;
      }
    }
  }
  public static void main(String[] args) {
    int[] a = {3, 5, 6, 7};
    PermutationGenerator.outputPermutations(a);
  }
}
