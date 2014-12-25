package algorithm.cluster;

public class Main {
  public static void main(String[] args) {
    //double[] d = {0.35, 0.31, 0.47};
    //DailyRatioPoint p = new DailyRatioPoint("2014-03-09", 7, d);
    
    KMeans kMeans = new KMeans(10, 3, 2);
    System.out.println("ok");
    double[] d1 = {1, 1};
    kMeans.addPoint(new Point(d1));
    System.out.println("add one " + kMeans.getK());
    double[] d2 = {3, 0};
    kMeans.addPoint(new Point(d2));
    double[] d3 = {2, 5};
    double[] d4 = {3, 6};
    double[] d5 = {4, 7};
    double[] d6 = {5, 3};
    double[] d7 = {2, 7};
    double[] d8 = {4, 8};
    double[] d9 = {8, 3};
    double[] d10 = {2, 6};
    kMeans.addPoint(new Point(d3));
    kMeans.addPoint(new Point(d4));
    kMeans.addPoint(new Point(d5));
    kMeans.addPoint(new Point(d6));
    kMeans.addPoint(new Point(d7));
    kMeans.addPoint(new Point(d8));
    kMeans.addPoint(new Point(d9));
    kMeans.addPoint(new Point(d10));
    kMeans.doClassify();
  }
}
