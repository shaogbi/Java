package algorithm.cluster;

public class Point {
  private double[] vector;
  public Point(double[] vector) {
    this.vector = vector;
  }
  public double[] getVector() {
    return vector;
  }
  public void setVector(double[] vector) {
    this.vector = vector;
  }
  public void updateValue(int index, double val) {
    vector[index] = val;
  }
}
