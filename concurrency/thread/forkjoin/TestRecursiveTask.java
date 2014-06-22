package concurrency.thread.forkjoin;

import java.util.*;
import java.util.concurrent.*;

class Document {
  private String[] words = {"the", "hello", "goodbye", "java", "thread", "pool", "random", "class", "main"};
  public String[][] generateDocument(int numLines, int numWords, String word) {
    int counter = 0;
    String[][] document = new String[numLines][numWords];
    Random random = new Random();
    for(int i=0;i<numLines;i++) {
      for(int j=0;j<numWords;j++) {
        document[i][j] = words[random.nextInt(words.length)];
        if(document[i][j].equals(word)) {
          counter++;
        }
      }
    }
    System.out.printf("DocumentMock: The word '%s' appears %d times in the document.\n", word, counter);
    return document;
  }
}

class LineTask extends RecursiveTask<Integer> {
  private static final long serialVersionUID = 1L;
  private String[] line;
  private int start;
  private int end;
  private String word;
  public LineTask(String[] line, int start, int end, String word) {
    this.line = line;
    this.start = start;
    this.end = end;
    this.word = word;
  }
  private int count(String[] line, int start, int end, String word) throws Exception {
    int counter = 0;
    for(int i=start;i<end;i++) {
      if(line[i].equals(word)) {
        counter++;
      }
    }
    TimeUnit.MILLISECONDS.sleep(10);
    return counter;
  }
  @Override
  protected Integer compute() {
    int result = 0;
    if(end - start < 100) {
      try {
        result = count(line, start, end, word);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      int mid = (start + end) / 2;
      LineTask task1 = new LineTask(line, start, mid, word);
      LineTask task2 = new LineTask(line, mid, end, word);
      invokeAll(task1, task2);
      try {
        result = task1.get() + task2.get();
      } catch(InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }
    return result;
  }
}

class DocumentTask extends RecursiveTask<Integer> {
  private static final long serialVersionUID = 1L;
  private String[][] document;
  private int start;
  private int end;
  private String word;
  public DocumentTask (String[][] document, int start, int end, String word) {
    this.document = document;
    this.start = start;
    this.end = end;
    this.word = word;
  }
  private int processLines(String[][] document, int start, int end, String word) {
    List<LineTask> tasks = new ArrayList<LineTask>();
    for(int i=start;i<end;i++) {
      tasks.add(new LineTask(document[i], 0, document[i].length, word));
    }
    invokeAll(tasks);
    int result = 0;
    for(int i=0;i<tasks.size();i++) {
      try {
        result += tasks.get(i).get();
      } catch(InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }
    return result;
  }
  @Override
  protected Integer compute() {
    int result = 0;
    if(end - start < 10) {
      result = processLines(document, start, end, word);
    } else {
      int mid = (start + end) / 2;
      DocumentTask task1 = new DocumentTask(document, start, mid, word);
      DocumentTask task2 = new DocumentTask(document, mid, end, word);
      invokeAll(task1, task2);
      try {
        result = task1.get() + task2.get();
      } catch(InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }
    return result;
  }
}

public class TestRecursiveTask {
  public static void main(String[] args) throws Exception {
    final String TARGET = "the";
    String[][] document = new Document().generateDocument(100, 1000, TARGET);
    DocumentTask task = new DocumentTask(document, 0, 100, TARGET);
    ForkJoinPool pool = new ForkJoinPool();
    pool.execute(task);
    pool.shutdown();
    // this while loop is not necessary, just for demonstration
    while(!task.isDone()) {
      ExecutionTraceTool.outputCurrentExecutionState(pool, "SECOND", 1);
    }
    System.out.printf("Main: The word '%s' appears %d times in the document.\n", TARGET, task.get());
  }
}
