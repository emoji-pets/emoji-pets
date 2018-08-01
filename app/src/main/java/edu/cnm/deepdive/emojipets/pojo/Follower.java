package edu.cnm.deepdive.emojipets.pojo;

public class Follower {

  String name;
  String pet_name;
  String pet_emoji;
  String status;

  // TODO check if needed when implementing gson
  public Follower(String name, String pet_name, String pet_emoji, String status) {
    this.name = name;
    this.pet_name = pet_name;
    this.pet_emoji = pet_emoji;
    this.status = status;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
