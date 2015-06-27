package designpattern.templatemethod;

public abstract class Template {
  public abstract void firstStep();
  public abstract void secondStep();
  public abstract void thirdStep();
  public final void processTemplate() {
    firstStep();
    secondStep();
    thirdStep();
  }
}
