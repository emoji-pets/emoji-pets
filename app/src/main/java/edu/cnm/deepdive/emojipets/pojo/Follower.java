package edu.cnm.deepdive.emojipets.pojo;

public class Follower {

  String diplay_name;
  String pet_name;
  String pet_emoji;
  String status;

  public Follower(String display_name, String pet_name, String pet_emoji, String status) {
    this.diplay_name = display_name;
    this.pet_name = pet_name;
    this.pet_emoji = pet_emoji;
    this.status = status;
  }

  public String getDiplay_name() {
    return diplay_name;
  }

  public void setDiplay_name(String diplay_name) {
    this.diplay_name = diplay_name;
  }

  public String getPet_name() {
    return pet_name;
  }

  public void setPet_name(String pet_name) {
    this.pet_name = pet_name;
  }

  public String getPet_emoji() {
    return pet_emoji;
  }

  public void setPet_emoji(String pet_emoji) {
    this.pet_emoji = pet_emoji;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
