package designpattern.memento;

public class Life {
  public static class Memento {
    private final String time;
    public Memento(String timeToSave) {
      time = timeToSave;
    }
    public String getSavedTime() {
      return time;
    }
  }
  private String time;
  public void setTime(String time) {
    this.time = time;
  }
  public Memento createMemento() {
    System.out.printf("Saving time '%s' to memento...\n", time);
    return new Memento(time);
  }
  public void restoreFromMemento(Memento memento) {
    System.out.printf("Time restored from memento: '%s'.\n", memento.getSavedTime());
  }
}
