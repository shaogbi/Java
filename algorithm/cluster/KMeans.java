package algorithm.cluster;

import java.util.*;

public class KMeans {
  private final int N;
  private final int K;
  private final int D;
  private final List<Point> points;
  private List<Cluster> clusters;
  private List<Cluster> teClusters;
  private final double MAX_DIS = 1000000000.0;
  public KMeans(int n, int k, int d) {
    this.N = n;
    this.K = k;
    this.D = d;
    this.points = new ArrayList<Point>(N);
    this.clusters = new ArrayList<Cluster>(K);
    this.teClusters = new ArrayList<Cluster>(K);
  }
  public int getK() {
    return K;
  }
  private double getDistance(Point p1, Point p2) {
    double dis = 0.0;
    for(int i=0;i<D;i++) {
      dis += Math.pow(p1.getVector()[i] - p2.getVector()[i], 2);
    }
    return Math.sqrt(dis);
  }
  public void addPoint(Point point) {
    this.points.add(point);
  }
  public void doClassify() {
    for(int i=0;i<N;i++) {
      System.out.println(points.get(i));
    }
    int i, j, k, id;
    double dis, minDis;
    double[] teVector, origVector;
    ArrayList<Integer> tePointIndexes;
    Cluster teCluster;
    for(i=0;i<K;i++) {
      Cluster cluster = new Cluster(points.get(i));
      clusters.add(cluster);
      teClusters.add(cluster);
    }
    while(true) {
      for(i=0;i<K;i++) {
        teClusters.get(i).clearPointIndexes();
      }
      // for each point, calculate minimum distance to center
      for(id=-1,i=0;i<N;i++) {
        for(minDis=MAX_DIS,j=0;j<K;j++) {
          dis = getDistance(points.get(i), clusters.get(j).getCenterPoint());
          if(dis < minDis) {
            minDis = dis;
            id = j;
          }
        }
        System.out.println(minDis + " " + id);
        if(id != -1) {
          teClusters.get(id).addPointIndex(i);
        }
      }
      //clusters.clear();
      // update center
      for(i=0;i<K;i++) {
        teCluster = teClusters.get(i);
        tePointIndexes = (ArrayList<Integer>)teCluster.getPointIndexes();
        // calculate center
        double[] vals = new double[D];
        for(j=0;j<D;j++) {
          vals[j] = 0.0;
        }
        for(j=0;j<tePointIndexes.size();j++) {
          for(k=0;k<D;k++) {
            //vals[k] += Math.pow(teCluster.getCenterPoint().getVector()[k] - points.get(j).getVector()[k], 2);
            vals[k] += teCluster.getCenterPoint().getVector()[k];
          }
        }
        for(j=0;j<D;j++) {
          //vals[j] = Math.sqrt(vals[j]);
          vals[j] /= D;
        }
        // test whether teCluster is same with cluster
        teVector = teCluster.getCenterPoint().getVector();
        origVector = clusters.get(i).getCenterPoint().getVector();
        for(j=0;j<D;j++) {
          if(teVector[j] != origVector[j]) {
            break;
          }
        }
        // if one center not change, break
        if(j == D) {
          break;
        }
        // update new cluster
        clusters.get(i).setCenter(new Point(vals));
        clusters.get(i).clearPointIndexes();
        for(j=0;j<tePointIndexes.size();j++) {
          clusters.get(i).addPointIndex(tePointIndexes.get(j));
        }
      }
      if(i < K) {
        break;
      }
    }
    System.out.println("cool!!!");
    for(i=0;i<K;i++) {
      System.out.printf("Cluster %d:\n", i);
      tePointIndexes = (ArrayList<Integer>)clusters.get(i).getPointIndexes();
      for(j=0;j<tePointIndexes.size();j++) {
        System.out.printf("Point %d: %f %f\n", tePointIndexes.get(j), points.get(tePointIndexes.get(j)).getVector()[0], points.get(tePointIndexes.get(j)).getVector()[1]);
      }
    }
  }
}
