package cst438.hw3.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="country")
public class Country {
  @Id
  private String code;
  private String name;

  // Default constructor
  public Country() {
    code = "code";
    name = "name";
  }

  // Constructor
  public Country(String code, String name) {
    this.code = code;
    this.name = name;
  }

  // Getters and setters
  public String getCode() { return code; }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  // Convert object data to a single string
  @Override
  public String toString() {
    return "Country [code=" + code + ", name=" + name + "]";
  }
}
