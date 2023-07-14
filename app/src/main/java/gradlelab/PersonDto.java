package gradlelab;

import lombok.Value;

@Value
public class PersonDto {
  String firstName;
  String lastName;
  int age;

  public PersonDto(String firstName, String lastName, int age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
  }
}