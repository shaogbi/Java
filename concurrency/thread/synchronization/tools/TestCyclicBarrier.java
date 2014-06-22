package concurrency.thread.synchronization.tools;

import java.util.Random;
import java.util.concurrent.*;

class MatrixMock {
  private int[][] data;
  public MatrixMock(int n, int m, int target) {
    int counter = 0;
    data = new int[n][m];
    Random random = new Random();
    for(int i=0;i<n; i++) {
      for(int j=0;j<m;j++) {
        data[i][j] = random.nextInt(10);
        if(data[i][j] == target) {
          counter++;
        }
      }
    }
    System.out.printf("There are %d ocurrences of target %d in generated data.\n", counter, target);
  }
  public int[] getRow(int row) {
    return data[row];
  }
}

class Results {
  private int[] data;
  public Results(int size) {
    data = new int[size];
  }
  public int[] getData() {
    return data;
  }
  public void setData(int position, int number) {
    data[position] = number;
  }
}

class Mapper implements Runnable {
  private MatrixMock mock;
  private int target;
  private int fromRow;
  private int toRow;
  private Results results;
  private CyclicBarrier barrier;
  public Mapper(MatrixMock mock, int target, int fromRow, int toRow, Results results, CyclicBarrier barrier) {
    this.mock = mock;
    this.target = target;
    this.fromRow = fromRow;
    this.toRow = toRow;
    this.results = results;
    this.barrier = barrier;
  }
  @Override
  public void run() {
    int counter;
    int[] row;
    System.out.printf("%s: Processing lines from %d to %d.\n", Thread.currentThread().getName(), fromRow, toRow);
    for(int i=fromRow;i<toRow;i++) {
      row = mock.getRow(i);
      counter = 0;
      for(int j=0;j<row.length;j++) {
        if(row[j] == target) {
          counter++;
        }
      }
      results.setData(i, counter);
    }
    System.out.printf("%s: Lines processed.\n", Thread.currentThread().getName());
    try {
      barrier.await();
    } catch(InterruptedException e) {
      e.printStackTrace();
    } catch(BrokenBarrierException e) {
      e.printStackTrace();
    }
  }
}

class Reducer implements Runnable {
  private Results results;
  public Reducer(Results results) {
    this.results = results;
  }
  @Override
  public void run() {
    int finalResult = 0;
    System.out.printf("Reducer: Preparation done! Processing results...\n");
    int[] data = results.getData();
    for(int number: data) {
      finalResult += number;
    }
    System.out.printf("Reducer: Total result: %d.\n", finalResult);
  }
}

public class TestCyclicBarrier {
  public static void main(String[] args) {
    final int N = 10000;
    final int M = 1000;
    final int TARGET = 8;
    final int MAPPER_COUNT = 5;
    final int RANGE_SIZE = N / MAPPER_COUNT;
    MatrixMock mock = new MatrixMock(N, M, TARGET);
    Results results = new Results(N);
    Reducer Reducer = new Reducer(results);
    // Reducer task begins once all Mapper tasks done
    CyclicBarrier barrier = new CyclicBarrier(MAPPER_COUNT, Reducer);
    for(int i=0;i<MAPPER_COUNT;i++) {
      new Thread(new Mapper(mock, TARGET, i*RANGE_SIZE, (i+1)*RANGE_SIZE, results, barrier)).start();
    }
    System.out.printf("Main: The main thread has finished.\n");
  }
}
