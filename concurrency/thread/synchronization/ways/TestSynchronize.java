package concurrency.thread.synchronization.ways;

class Account {
  private double balance;
  public double getBalance() {
    return balance;
  }
  public void setBalance(double balance) {
    this.balance = balance;
  }
  public synchronized void addAmount(double amount) {
    double tmp = balance;
    try {
      Thread.sleep(10);
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
    tmp += amount;
    balance = tmp;
  }
  public synchronized void subtractAmount(double amount) {
    double tmp = balance;
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    tmp -= amount;
    balance = tmp;
  }
}

class Company implements Runnable {
  private Account account;
  public Company(Account account) {
    this.account = account;
  }
  @Override
  public void run() {
    for(int i=0;i<100;i++) {
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
    for(int i=0;i<100;i++) {
      account.subtractAmount(1000);
    }
  }
}

class Cinema {
  private int seats1;
  private int seats2;
  private final Object controlCinema1;
  private final Object controlCinema2;
  public Cinema() {
    this.seats1 = 20;
    this.seats2 = 20;
    this.controlCinema1 = new Object();
    this.controlCinema2 = new Object();
  }
  // it is ok that at one time there are 2 threads:
  // one is doing sellTickets1 while another is doing sellTickets2
  // if use synchronized(this), only one thread can be executed at same time
  public boolean sellTickets1(int number) {
    synchronized(controlCinema1) {
      if(number < seats1) {
        seats1 -= number;
        return true;
      } else {
        return false;
      }
    }
  }
  public boolean sellTickets2(int number) {
    synchronized(controlCinema2) {
      if(number < seats2) {
        seats2 -= number;
        return true;
      } else {
        return false;
      }
    }
  }
  public boolean returnTickets1(int number) {
    synchronized(controlCinema1) {
      seats1 += number;
      return true;
    }
  }
  public boolean returnTickets2(int number) {
    synchronized(controlCinema2) {
      seats2 += number;
      return true;
    }
  }
}

public class TestSynchronize {
  public static void main(String[] args) {
    Account account = new Account();
    account.setBalance(1000);
    Company company = new Company(account);
    Thread companyThread = new Thread(company);
    Bank bank = new Bank(account);
    Thread bankThread = new Thread(bank);
    System.out.printf("Account : Initial Balance: %f\n", account.getBalance());
    companyThread.start();
    bankThread.start();
    try {
      companyThread.join();
      bankThread.join();
      System.out.printf("Account : Final Balance: %f\n", account.getBalance());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    /* output is:
     * Account : Initial Balance: 1000.000000
     * Account : Final Balance: 1000.000000
     * if not using synchronized, initial and final will be different
     */
  }
}
