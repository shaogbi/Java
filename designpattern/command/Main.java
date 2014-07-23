package designpattern.command;

public class Main {
  public static void main(String[] args) {
    int[] target = { 3, 5, 2, 6, 5 };
    ICommand sumUpCommand = new SumUpCommand();
    Commander commander = new Commander();
    commander.setCommand(sumUpCommand);
    commander.executeCommand(target);
    // anonymous way
    commander.setCommand(new ICommand() {
      @Override
      public void executeCommand(int[] target) {
        for(int i: target) {
          System.out.printf("Element is %d.\n", i);
        }
      }
    });
    commander.executeCommand(target);
  }
}
