package algorithm.fundamental;

public class BinarySearchTool {
  // find the minimal position which satisfy some condition
  private static int getMinPosition(int[] arr, int target) {
    int l = 0, r = arr.length - 1;
    int ans = -1;
    while(l <= r) {
      int m = (l + r) >> 1;
      // feel free to replace the condition
      // here it means find the minimal position that the element not smaller than target
      if(arr[m] >= target) {
        ans = m;
        r = m - 1;
      } else {
        l = m + 1;
      }
    }
    return ans;
  }
  // find the maximal position which satisfy some condition
  private static int getMaxPosition(int[] arr, int target) {
    int l = 0, r = arr.length - 1;
    int ans = -1;
    while(l <= r) {
      int m = (l + r) >> 1;
      // feel free to replace the condition
      // here it means find the maximal position that the element less than target
      if(arr[m] < target) {
        ans = m;
        l = m + 1;
      } else {
        r = m - 1;
      }
    }
    return ans;
  }
  private static int getPosition(int[] arr, int target) {
    int l = 0, r = arr.length - 1;
    while(l <= r) {
      int m = (l + r) >> 1;
      if(arr[m] == target) {
        return m;
      }
      if(arr[m] < target) {
        l = m + 1;
      } else {
        r = m - 1;
      }
    }
    return -1;
  }
  public static void main(String[] args) {
    int[] a = {3, 5, 5, 7, 10, 15};
    System.out.println(BinarySearchTool.getPosition(a, 10));
    System.out.println(BinarySearchTool.getPosition(a, 9));
    System.out.println(BinarySearchTool.getMinPosition(a, 5));
    System.out.println(BinarySearchTool.getMinPosition(a, 6));
    System.out.println(BinarySearchTool.getMaxPosition(a, 8));
  }
}
