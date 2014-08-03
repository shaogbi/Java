package others.consistenthash;

import java.util.*;

public class Server {
  private String name;
  private Set<Integer> nodes;
  public Server(String name) {
    this.name = name;
    nodes = new HashSet<Integer>();
  }
  public String getName() {
    return name;
  }
  public Set<Integer> getNodes() {
    return nodes;
  }
  public void addVirtualNode(int nodeHash) {
    nodes.add(nodeHash);
  }
}
