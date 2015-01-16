package designpattern.builder;

public class Main {
  public static void main(String[] args) {
    User user1 = new User.UserBuilder("Jhon", "Doe")
      .withAge(30)
      .withPhone("1234567")
      .withAddress("Fake address 1234").build();
    System.out.printf("User1 age is %d.\n", user1.getAge());
    User user2 = new User.UserBuilder("Jhon", "Doe").build();
    System.out.printf("User2 address is %s.\n", user2.getAddress());
    User user3 = new User.UserBuilder("Jhon", "Doe")
      .withAge(-1).build();
    System.out.printf("User3 age is %d.\n", user3.getAge());
  }
}
