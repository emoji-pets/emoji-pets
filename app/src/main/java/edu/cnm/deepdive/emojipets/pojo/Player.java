package edu.cnm.deepdive.emojipets.pojo;

import java.util.List;

public class Player {

  long id;
  String oauthId;
  String display_name;
  String status;
  String pet_name;
  String pet_emoji;
  List<Follower> followers;
  List<Follower> following;
  int level;
  int xp;
  int maxXp;
  long couragePoints;
  int couragePointsMax;
  long manaPoints;
  int manaPointsMax;
  long powerPoints;
  int powerPointsMax;
  long healthPoints;
  int healthPointsMax;
  String href;

  public Player() {

  }

  public String getOauthId() {
    return oauthId;
  }

  public void setOauthId(String oauthId) {
    this.oauthId = oauthId;
  }

  public String getDisplay_name() {
    return display_name;
  }

  public void setDisplay_name(String display_name) {
    this.display_name = display_name;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
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

  public long getCouragePoints() {
    return couragePoints;
  }

  public void setCouragePoints(long couragePoints) {
    this.couragePoints = couragePoints;
  }

  public int getCouragePointsMax() {
    return couragePointsMax;
  }

  public void setCouragePointsMax(int couragePointsMax) {
    this.couragePointsMax = couragePointsMax;
  }

  public long getManaPoints() {
    return manaPoints;
  }

  public void setManaPoints(long manaPoints) {
    this.manaPoints = manaPoints;
  }

  public int getManaPointsMax() {
    return manaPointsMax;
  }

  public void setManaPointsMax(int manaPointsMax) {
    this.manaPointsMax = manaPointsMax;
  }

  public long getPowerPoints() {
    return powerPoints;
  }

  public void setPowerPoints(long powerPoints) {
    this.powerPoints = powerPoints;
  }

  public int getPowerPointsMax() {
    return powerPointsMax;
  }

  public void setPowerPointsMax(int powerPointsMax) {
    this.powerPointsMax = powerPointsMax;
  }

  public long getHealthPoints() {
    return healthPoints;
  }

  public void setHealthPoints(long healthPoints) {
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
