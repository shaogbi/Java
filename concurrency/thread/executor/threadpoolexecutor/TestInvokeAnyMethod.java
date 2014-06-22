package concurrency.thread.executor.threadpoolexecutor;

import java.util.*;
import java.util.concurrent.*;

class UserValidator {
  private String name;
  public UserValidator(String name) {
    this.name = name;
  }
  public String getName() {
    return name;
  }
  public boolean validate(String name) throws Exception {
    Random random = new Random();
    long duration = (long)(Math.random()*10);
    System.out.printf("Validator %s: Validating needs %d seconds.\n", this.name, duration);
    TimeUnit.SECONDS.sleep(duration);
    return random.nextBoolean();
  }
}

class TaskValidator implements Callable<String> {
  private UserValidator validator;
  private String user;
  public TaskValidator(UserValidator validator, String user) {
    this.validator = validator;
    this.user = user;
  }
  @Override
  public String call() throws Exception {
    if(!validator.validate(user)) {
      System.out.printf("%s: The user has not been found.\n", validator.getName());
      throw new Exception("Error validating user " + validator.getName() + "!");
    }
    System.out.printf("User %s has been found.\n", validator.getName());
    return validator.getName();
  }
}

public class TestInvokeAnyMethod {
  public static void main(String[] args) {
    String username = "test";
    UserValidator ldapValidator = new UserValidator("LDAP");
    UserValidator dbValidator = new UserValidator("DataBase");
    UserValidator websiteValidator = new UserValidator("Website");
    TaskValidator ldapTask = new TaskValidator(ldapValidator, username);
    TaskValidator dbTask = new TaskValidator(dbValidator, username);
    TaskValidator websiteTask = new TaskValidator(websiteValidator, username);
    List<TaskValidator> taskList = new ArrayList<>();
    taskList.add(ldapTask);
    taskList.add(dbTask);
    taskList.add(websiteTask);
    ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newCachedThreadPool();
    String result;
    /*
    try {
      List<Future<String>> resultList = executor.invokeAll(taskList);
      System.out.printf("All done:\n");
      for(int i=0;i<resultList.size();i++) {
        System.out.printf("%s\n", resultList.get(i).get());
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
    */
    try {
      result = executor.invokeAny(taskList);
      System.out.printf("Main: Result: %s.\n", result);
    } catch(InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    executor.shutdown();
    System.out.printf("Main: End of the Execution.\n");
  }
}

/* get exception if and only if all tasks get exception
 * example 1(first failed, second succeed):
Validator LDAP: Validating needs 4 seconds.
Validator DataBase: Validating needs 5 seconds.
Validator Website: Validating needs 0 seconds.
Website: The user has not been found.
User LDAP has been found.
Main: Result: LDAP.
Main: End of the Execution.
 * example 2(all failed, so exception got):
Validator LDAP: Validating needs 2 seconds.
Validator Website: Validating needs 7 seconds.
Validator DataBase: Validating needs 7 seconds.
LDAP: The user has not been found.
Website: The user has not been found.
DataBase: The user has not been found.
java.util.concurrent.ExecutionException: java.lang.Exception: Error validating user!
  at java.util.concurrent.FutureTask.report(FutureTask.java:122)
  at java.util.concurrent.FutureTask.get(FutureTask.java:188)
  at java.util.concurrent.AbstractExecutorService.doInvokeAny(AbstractExecutorService.java:193)
  at java.util.concurrent.AbstractExecutorService.invokeAny(AbstractExecutorService.java:215)
  at concurrency.thread.executor.threadpoolexecutor.TestInvokeAnyMethod.main(TestInvokeAnyMethod.java:60)
Caused by: java.lang.Exception: Error validating user!
  at concurrency.thread.executor.threadpoolexecutor.TaskValidator.call(TestInvokeAnyMethod.java:36)
  at concurrency.thread.executor.threadpoolexecutor.TaskValidator.call(TestInvokeAnyMethod.java:1)
  at java.util.concurrent.FutureTask.run(FutureTask.java:262)
  at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:471)
  at java.util.concurrent.FutureTask.run(FutureTask.java:262)
  at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1145)
  at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:615)
  at java.lang.Thread.run(Thread.java:744)
Main: End of the Execution.
*/