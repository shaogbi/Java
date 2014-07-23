package designpattern.command;

public class Commander {
  private ICommand command;
  public void setCommand(ICommand command) {
    this.command = command;
  }
  public void executeCommand(int[] target) {
    command.executeCommand(target);
  }
}
