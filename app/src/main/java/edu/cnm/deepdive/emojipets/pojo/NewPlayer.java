package edu.cnm.deepdive.emojipets.pojo;

public class NewPlayer {

  String displayName;
  String petName;
  String petEmoji;

  public NewPlayer(String displayName, String petName, String petEmoji) {
    this.displayName = displayName;
    this.petName = petName;
    this.petEmoji = petEmoji;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getPetName() {
    return petName;
  }

  public void setPetName(String petName) {
    this.petName = petName;
  }

  public String getPetEmoji() {
    return petEmoji;
  }

  public void setPetEmoji(String petEmoji) {
    this.petEmoji = petEmoji;
  }
}
