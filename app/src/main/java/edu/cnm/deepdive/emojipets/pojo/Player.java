package edu.cnm.deepdive.emojipets.pojo;

import java.util.List;

public class Player {

  long id;
  String displayName;
  String status;
  String emoji;
  List<Follower> followers;
  List<Follower> following;
  String petName;
  int level;
  int xp;
  int maxXp;
  long timeStamp;
  int couragePoints;
  int couragePointsMax;
  int manaPoints;
  int manaPointsMax;
  int powerPoints;
  int powerPointsMax;
  int healthPoints;
  int healthPointsMax;
  String href;

  public Player(long id, String displayName, String status, String emoji,
      List<Follower> followers, List<Follower> following, String petName, int level, int xp,
      int maxXp, long timeStamp, int couragePoints, int couragePointsMax, int manaPoints,
      int manaPointsMax, int powerPoints, int powerPointsMax, int healthPoints, int healthPointsMax,
      String href) {
    this.id = id;
    this.displayName = displayName;
    this.status = status;
    this.emoji = emoji;
    this.followers = followers;
    this.following = following;
    this.petName = petName;
    this.level = level;
    this.xp = xp;
    this.maxXp = maxXp;
    this.timeStamp = timeStamp;
    this.couragePoints = couragePoints;
    this.couragePointsMax = couragePointsMax;
    this.manaPoints = manaPoints;
    this.manaPointsMax = manaPointsMax;
    this.powerPoints = powerPoints;
    this.powerPointsMax = powerPointsMax;
    this.healthPoints = healthPoints;
    this.healthPointsMax = healthPointsMax;
    this.href = href;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getEmoji() {
    return emoji;
  }

  public void setEmoji(String emoji) {
    this.emoji = emoji;
  }

  public List<Follower> getFollowers() {
    return followers;
  }

  public void setFollowers(List<Follower> followers) {
    this.followers = followers;
  }

  public List<Follower> getFollowing() {
    return following;
  }

  public void setFollowing(List<Follower> following) {
    this.following = following;
  }

  public String getPetName() {
    return petName;
  }

  public void setPetName(String petName) {
    this.petName = petName;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getXp() {
    return xp;
  }

  public void setXp(int xp) {
    this.xp = xp;
  }

  public int getMaxXp() {
    return maxXp;
  }

  public void setMaxXp(int maxXp) {
    this.maxXp = maxXp;
  }

  public long getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(long timeStamp) {
    this.timeStamp = timeStamp;
  }

  public int getCouragePoints() {
    return couragePoints;
  }

  public void setCouragePoints(int couragePoints) {
    this.couragePoints = couragePoints;
  }

  public int getCouragePointsMax() {
    return couragePointsMax;
  }

  public void setCouragePointsMax(int couragePointsMax) {
    this.couragePointsMax = couragePointsMax;
  }

  public int getManaPoints() {
    return manaPoints;
  }

  public void setManaPoints(int manaPoints) {
    this.manaPoints = manaPoints;
  }

  public int getManaPointsMax() {
    return manaPointsMax;
  }

  public void setManaPointsMax(int manaPointsMax) {
    this.manaPointsMax = manaPointsMax;
  }

  public int getPowerPoints() {
    return powerPoints;
  }

  public void setPowerPoints(int powerPoints) {
    this.powerPoints = powerPoints;
  }

  public int getPowerPointsMax() {
    return powerPointsMax;
  }

  public void setPowerPointsMax(int powerPointsMax) {
    this.powerPointsMax = powerPointsMax;
  }

  public int getHealthPoints() {
    return healthPoints;
  }

  public void setHealthPoints(int healthPoints) {
    this.healthPoints = healthPoints;
  }

  public int getHealthPointsMax() {
    return healthPointsMax;
  }

  public void setHealthPointsMax(int healthPointsMax) {
    this.healthPointsMax = healthPointsMax;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }
}
