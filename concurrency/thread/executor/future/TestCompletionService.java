package concurrency.thread.executor.future;

import java.util.concurrent.*;

class ReportGenerator implements Callable<String> {
  private String sender;
  private String title;
  public ReportGenerator(String sender, String title) {
    this.sender = sender;
    this.title = title;
  }
  @Override
  public String call() throws Exception {
    Long duration = (long)(Math.random() * 10);
    System.out.printf("%s_%s: ReportGenerator: Generating a report during %d seconds.\n", sender, title, duration);
    TimeUnit.SECONDS.sleep(duration);
    return sender + ": " + title;
  }
}

class ReportRequest implements Runnable {
  private String name;
  private CompletionService<String> service;
  public ReportRequest(String name, CompletionService<String> service) {
    this.name = name;
    this.service = service;
  }
  @Override
  public void run() {
    ReportGenerator reportGenerator = new ReportGenerator(name, "Report");
    service.submit(reportGenerator);
  }
}

class ReportProcessor implements Runnable {
  private CompletionService<String> service;
  private boolean end;
  public ReportProcessor(CompletionService<String> service) {
    this.service = service;
    this.end = false;
  }
  public void setEnd(boolean end) {
    this.end = end;
  }
  @Override
  public void run() {
    while(!end) {
      try {
        Future<String> result = service.poll(20, TimeUnit.SECONDS);
        if(result != null) {
          String report = result.get();
          System.out.printf("ReportReceiver: Report Received: %s.\n", report);
        }
      } catch(InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }
    System.out.printf("ReportSender: End.\n");
  }
}

public class TestCompletionService {
  public static void main(String[] args) {
    ExecutorService executor = (ExecutorService)Executors.newCachedThreadPool();
    CompletionService<String> service = new ExecutorCompletionService<>(executor);
    ReportRequest faceRequest = new ReportRequest("Face", service);
    ReportRequest onlineRequest = new ReportRequest("Online", service);
    Thread faceThread = new Thread(faceRequest);
    Thread onlineThread = new Thread(onlineRequest);
    ReportProcessor processor = new ReportProcessor(service);
    Thread senderThread = new Thread(processor);
    System.out.printf("Main: Starting the Threads\n");
    faceThread.start();
    onlineThread.start();
    senderThread.start();
    try {
      System.out.printf("Main: Waiting for the report generators.\n");
      faceThread.join();
      onlineThread.join();
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
    System.out.printf("Main: Shutting down the executor.\n");
    executor.shutdown();
    try {
      executor.awaitTermination(1, TimeUnit.DAYS);
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
    processor.setEnd(true);
    System.out.println("Main: Ends");
  }
}
