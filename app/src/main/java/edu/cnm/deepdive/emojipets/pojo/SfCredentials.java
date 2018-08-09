package edu.cnm.deepdive.emojipets.pojo;

/**
 * This is the SfCredentials class which is a "Plain Old Java Object" or POJO for short.
 * This POJO is a pure data structure that has fields with getters and setters.
 */
public class SfCredentials {

  private String username;
  private String password;

  /**
   *
   * @param username This is a  mutator method for the username variable.
   * This method is used to control changes to this variable.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   *
   * @param password This is a  mutator method for the password variable.
   * This method is used to control changes to this variable.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   *
   * @return This is an accessor method for the username variable.
   * This method returns the value of this private member variable.
   */
  public String getUsername() {
    return username;
  }

  /**
   *
   * @return This is an accessor method for the password variable.
   * This method returns the value of this private member variable.
   */
  public String getPassword() {
    return password;
  }
}
