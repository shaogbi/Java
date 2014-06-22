package algorithm.cluster;

import java.util.*;

public class Cluster {
  private Point center;
  private List<Integer> pointIndexes;
  public Cluster(Point center) {
    this.setCenter(center);
    this.pointIndexes = new ArrayList<Integer>();
  }
  public Point getCenterPoint() {
    return center;
  }
  public void setCenter(Point center) {
    this.center = center;
  }
  public List<Integer> getPointIndexes() {
    return pointIndexes;
  }
  public void clearPointIndexes() {
    pointIndexes.clear();
  }
  public void addPointIndex(int pointIndex) {
    pointIndexes.add(pointIndex);
  }
}
