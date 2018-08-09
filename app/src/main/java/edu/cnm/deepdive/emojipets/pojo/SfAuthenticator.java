package edu.cnm.deepdive.emojipets.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * This is the SfAuthenticator class which is a "Plain Old Java Object" or POJO for short.
 * This POJO is a pure data structure that has fields with getters and setters.
 */
public class SfAuthenticator {

  @SerializedName("access_token")
  private String accessToken;

  private String id;

  /**
   *
   * @return This is an accessor method for the accessToken variable.
   * This method returns the value of this private member variable.
   */
  public String getAccessToken() {
    return accessToken;
  }

  /**
   *
   * @param accessToken This is a  mutator method for the accessToken variable.
   * This method is used to control changes to this variable.
   */
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  /**
   *
   * @return This is an accessor method for the id variable.
   * This method returns the value of this private member variable.
   */
  public String getId() {
    return id;
  }

  /**
   *
   * @param id This is a  mutator method for the id variable.
   * This method is used to control changes to this variable.
   */
  public void setId(String id) {
    this.id = id;
  }
}
