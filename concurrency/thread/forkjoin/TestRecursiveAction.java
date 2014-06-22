package concurrency.thread.forkjoin;

import java.util.*;
import java.util.concurrent.*;

class Product {
  private String name;
  private double price;
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public double getPrice() {
    return price;
  }
  public void setPrice(double price) {
    this.price = price;
  }
}

class ProductListGenerator {
  public List<Product> generate(int size) {
    List<Product> products = new ArrayList<Product>();
    for(int i=0;i<size;i++) {
      Product product = new Product();
      product.setName("Product-" + i);
      product.setPrice(10);
      products.add(product);
    }
    return products;
  }
}

class Task extends RecursiveAction {
  private static final long serialVersionUID = 1L;
  private List<Product> products;
  private int first;
  private int last;
  private double increment;
  public Task(List<Product> products, int first, int last, double increment) {
    this.products = products;
    this.first = first;
    this.last = last;
    this.increment = increment;
  }
  private void updatePrices() {
    for(int i=first;i<last;i++) {
      Product product = products.get(i);
      product.setPrice(product.getPrice() * (1 + increment));
    }
  }
  @Override
  protected void compute() {
    if(last - first < 10) {
      updatePrices();
    } else {
      int middle = (last + first) / 2;
      System.out.printf("Task: Pending tasks: %s\n", getQueuedTaskCount());
      Task t1 = new Task(products, first, middle+1, increment);
      Task t2 = new Task(products, middle+1, last, increment);
      invokeAll(t1, t2);
    }
  }
}

public class TestRecursiveAction {
  public static void main(String[] args) {
    List<Product> products = new ProductListGenerator().generate(10000);
    Task task = new Task(products, 0, products.size(), 0.20);
    ForkJoinPool pool = new ForkJoinPool();
    pool.execute(task);
    pool.shutdown();
    System.out.printf("Available processors is %d.\n", Runtime.getRuntime().availableProcessors());
    while(!task.isDone()) {
      ExecutionTraceTool.outputCurrentExecutionState(pool, "MILLISECOND", 5);
    }
    System.out.printf("Main: The process has completed normally? %s.\n", task.isCompletedNormally());
    for(int i=0;i<products.size();i++) {
      Product product = products.get(i);
      if(product.getPrice() != 12) {
        System.out.printf("Incorrect product found, name is %s, price is %f.\n", product.getName(), product.getPrice());
      }
    }
    System.out.println("Main: End of the program.");
  }
}
