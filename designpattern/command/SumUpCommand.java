package designpattern.command;

public class SumUpCommand implements ICommand {
  @Override
  public void executeCommand(int[] target) {
    int sum = 0;
    for(int i: target) {
      sum += i;
    }
    System.out.printf("Sum is %d.\n", sum);
  }
}
