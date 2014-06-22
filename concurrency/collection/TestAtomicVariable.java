package concurrency.collection;

import java.util.concurrent.atomic.*;

class Account {
  private AtomicLong balance;
  public Account() {
    this.balance = new AtomicLong();
  }
  public AtomicLong getBalance() {
    return balance;
  }
  public void setBalance(long balance) {
    this.balance.set(balance);
  }
  public void addAmount(long amount) {
    balance.getAndAdd(amount);
  }
  public void subtractAmount(long amount) {
    balance.getAndAdd(-amount);
  }
}

class Company implements Runnable {
  private Account account;
  public Company(Account account) {
    this.account = account;
  }
  @Override
  public void run() {
    for(int i=0;i<10;i++) {
      account.addAmount(1000);
    }
  }
}

class Bank implements Runnable {
  private Account account;
  public Bank(Account account) {
    this.account = account;
  }
  @Override
  public void run() {
    for(int i=0;i<10;i++) {
      account.subtractAmount(1000);
    }
  }
}

class Incrementer implements Runnable {
  private AtomicIntegerArray array;
  public Incrementer(AtomicIntegerArray array) {
       this.array = array;
  }
  @Override
  public void run() {
    for(int i=0;i<array.length();i++) {
      array.getAndIncrement(i);
    }
  }
}

class Decrementer implements Runnable {
  private AtomicIntegerArray array;
  public Decrementer(AtomicIntegerArray array) {
    this.array = array;
  }
  @Override
  public void run() {
    for(int i=0;i<array.length();i++) {
      array.getAndDecrement(i);
    }
  }
}

public class TestAtomicVariable {
  public static void main(String[] args) throws Exception {
    Account account = new Account();
    account.setBalance(1000);
    Company company = new Company(account);
    Thread companyThread = new Thread(company);
    Bank bank = new Bank(account);
    Thread bankThread = new Thread(bank);
    System.out.printf("Account : Initial Balance: %d.\n", account.getBalance().intValue());
    companyThread.start();
    bankThread.start();
    companyThread.join();
    bankThread.join();
    System.out.printf("Account : Final Balance: %d.\n", account.getBalance().intValue());
    System.out.println("Test Array:");
    final int THREADS = 100;
    AtomicIntegerArray array = new AtomicIntegerArray(1000);
    Incrementer incrementer = new Incrementer(array);
    Decrementer decrementer = new Decrementer(array);
    Thread[] threadIncrementer = new Thread[THREADS];
    Thread[] threadDecrementer = new Thread[THREADS];
    for(int i=0;i<THREADS;i++) {
      threadIncrementer[i] = new Thread(incrementer);
      threadDecrementer[i] = new Thread(decrementer);
      threadIncrementer[i].start();
      threadDecrementer[i].start();
    }
    for(int i=0;i<THREADS;i++) {
      threadIncrementer[i].join();
      threadDecrementer[i].join();
    }
    for(int i=0;i<array.length();i++) {
      if(array.get(i) != 0) {
        System.out.printf("Error: Array[%d]: %d.\n", i, array.get(i));
      } else {
        System.out.printf("Array[%d]: %d.\n", i, array.get(i));
      }
    }
    System.out.println("Main: End of the example.");
  }
}
