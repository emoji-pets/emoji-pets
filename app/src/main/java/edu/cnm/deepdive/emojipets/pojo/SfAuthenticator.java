package edu.cnm.deepdive.emojipets.pojo;

import com.google.gson.annotations.SerializedName;

public class SfAuthenticator {

  @SerializedName("access_token")
  private String accessToken;

  private String id;

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
