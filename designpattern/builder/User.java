package designpattern.builder;

public class User {
  private final String firstName; // required
  private final String lastName; // required
  private final int age; // optional
  private final String phone; // optional
  private final String address; // optional
  public static class UserBuilder {
    private final String firstName;
    private final String lastName;
    private int age;
    private String phone;
    private String address;
    public UserBuilder(String firstName, String lastName) {
      this.firstName = firstName;
      this.lastName = lastName;
    }
    public UserBuilder withAge(int age) {
      this.age = age;
      return this;
    }
    public UserBuilder withPhone(String phone) {
      this.phone = phone;
      return this;
    }
    public UserBuilder withAddress(String address) {
      this.address = address;
      return this;
    }
    public User build() {
      // if(age < 0) { ... } return new User(this);
      // not thread-safe, a second thread may modify the value of age
      User user = new User(this);
      if (user.getAge() < 0) {
        throw new IllegalStateException("Age out of range!"); // thread-safe
      }
      return user;
    }
  }
  private User(UserBuilder builder) {
    this.firstName = builder.firstName;
    this.lastName = builder.lastName;
    this.age = builder.age;
    this.phone = builder.phone;
    this.address = builder.address;
  }
  public String getFirstName() {
    return firstName;
  }
  public String getLastName() {
    return lastName;
  }
  public int getAge() {
    return age;
  }
  public String getPhone() {
    return phone;
  }
  public String getAddress() {
    return address;
  }
}
