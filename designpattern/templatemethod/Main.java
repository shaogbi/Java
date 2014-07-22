package designpattern.templatemethod;

public class Main {
  public static void main(String[] args) {
    Template haveDinnerTemplate = new HaveDinnerTemplate();
    haveDinnerTemplate.processTemplate();
    Template threeStepTemplate = new ThreeStepTemplate();
    threeStepTemplate.processTemplate();
  }
}
