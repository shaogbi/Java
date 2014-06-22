package algorithm.fundamental;

public class CombinationGenerator {
  // C(n, k)
  private static void outputCombinations(int[] arr, int n, int k) {
    int[] p = new int[k];
    for(int i=0;i<k;i++) {
      p[i] = i;
    }
    int curr = k - 2;
    while(true) {
      for(int i=0;i<k;i++) {
        System.out.printf("%d ", arr[p[i]]);
      }
      System.out.println();
      if(p[k-1] < n - 1) {
        p[k-1]++;
      } else {
        if(p[curr] + k - curr < n) {
          p[curr]++;
        } else {
          if(curr == 0) {
            break;
          } else {
            curr--;
            p[curr]++;
          }
        }
        for(int i=curr+1,j=1;i<k;i++,j++) {
          p[i] = p[curr] + j;
        }
      }
    }
  }
  public static void main(String[] args) {
    int[] a = {1, 2, 3, 4, 5, 6, 7};
    CombinationGenerator.outputCombinations(a, 7, 3);
  }
}
