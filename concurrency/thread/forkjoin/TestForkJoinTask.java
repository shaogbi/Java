package concurrency.thread.forkjoin;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

class FolderProcessor extends RecursiveTask<List<String>> {
  private static final long serialVersionUID = 1L;
  private String path;
  private String extension;
  public FolderProcessor(String path, String extension) {
    this.path = path;
    this.extension = extension;
  }
  @Override
  protected List<String> compute() {
    List<String> list = new ArrayList<>();
    List<FolderProcessor> tasks = new ArrayList<>();
    File file = new File(path);
    File[] content = file.listFiles();
    if(content != null) {
      for(int i=0;i<content.length;i++) {
        if(content[i].isDirectory()) {
          FolderProcessor task = new FolderProcessor(content[i].getAbsolutePath(), extension);
          task.fork();
          tasks.add(task);
        } else {
          if(content[i].getName().endsWith(extension)) {
            list.add(content[i].getAbsolutePath());
          }
        }
      }
      if(tasks.size() > 50) {
        System.out.printf("%s: %d tasks ran.\n", file.getAbsolutePath(), tasks.size());
      }
      for(FolderProcessor item: tasks) {
        list.addAll(item.join());
        /* or use
        try {
          list.addAll(item.get());
        } catch(InterruptedException | ExecutionException e) {
          e.printStackTrace();
        }
        */
      }
    }
    return list;
  }
}

public class TestForkJoinTask {
  public static void main(String[] args) {
    ForkJoinPool pool = new ForkJoinPool();
    FolderProcessor system = new FolderProcessor("C:\\Windows", "log");
    FolderProcessor apps = new FolderProcessor("C:\\Program Files", "log");
    FolderProcessor documents = new FolderProcessor("C:\\Documents And Settings", "log");
    pool.execute(system);
    pool.execute(apps);
    pool.execute(documents);
    pool.shutdown();
    // this while loop is not necessary, just for demonstration
    while(!system.isDone() || !apps.isDone() || !documents.isDone()) {
      ExecutionTraceTool.outputCurrentExecutionState(pool, "SECOND", 5);
    }
    // if no while loop, first join() method waiting for all tasks completed, so it takes several seconds
    // else, all tasks already completed, it takes just a moment
    List<String> results = system.join();
    for(String s: results) {
      System.out.println(s);
    }
    System.out.printf("System: %d files found.\n", results.size()); // system.get().size() is also available
    // second join() takes just a moment because all tasks already completed
    results = apps.join();
    System.out.printf("Apps: %d files found.\n", results.size());
    results = documents.join();
    System.out.printf("Documents: %d files found.\n", results.size());
  }
}
