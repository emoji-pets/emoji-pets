package edu.cnm.deepdive.emojipets.pojo;

import java.util.List;

/**
 * This is the Player class which is a "Plain Old Java Object" or POJO for short.
 * This POJO is a pure data structure that has fields with getters and setters.
 */
public class Player {

  private long id;
  private String oauthId;
  private String display_name;
  private String status;
  private String pet_name;
  private String pet_emoji;
  private List<Player> followers;
  private List<Player> following;
  private int level;
  private int xp;
  private int maxXp;
  private long couragePoints;
  private int couragePointsMax;
  private long manaPoints;
  private int manaPointsMax;
  private long powerPoints;
  private int powerPointsMax;
  private long healthPoints;
  private int healthPointsMax;
  private String wall;
//  String href;

  public Player() {

  }

  /**
   *
   * @return This is an accessor method for the id variable.
   * This method returns the value of this private member variable.
   */
  public long getId() {
    return id;
  }

  /**
   *
   * @param id This is a  mutator method for the id variable.
   * This method is used to control changes to this variable.
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   *
   * @return This is an accessor method for the oauthId variable.
   * This method returns the value of this private member variable.
   */
  public String getOauthId() {
    return oauthId;
  }

  /**
   *
   * @param oauthId This is a  mutator method for the oauthId variable.
   * This method is used to control changes to this variable.
   */
  public void setOauthId(String oauthId) {
    this.oauthId = oauthId;
  }

  /**
   *
   * @return This is an accessor method for the display_name variable.
   * This method returns the value of this private member variable.
   */
  public String getDisplay_name() {
    return display_name;
  }

  /**
   *
   * @param display_name This is a  mutator method for the display_name variable.
   * This method is used to control changes to this variable.
   */
  public void setDisplay_name(String display_name) {
    this.display_name = display_name;
  }

  /**
   *
   * @return This is an accessor method for the status variable.
   * This method returns the value of this private member variable.
   */
  public String getStatus() {
    return status;
  }

  /**
   *
   * @param status This is a  mutator method for the status variable.
   * This method is used to control changes to this variable.
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   *
   * @return This is an accessor method for the pet_name variable.
   * This method returns the value of this private member variable.
   */
  public String getPet_name() {
    return pet_name;
  }

  /**
   *
   * @param pet_name This is a  mutator method for the pet_name variable.
   * This method is used to control changes to this variable.
   */
  public void setPet_name(String pet_name) {
    this.pet_name = pet_name;
  }

  /**
   *
   * @return This is an accessor method for the pet_emoji variable.
   * This method returns the value of this private member variable.
   */
  public String getPet_emoji() {
    return pet_emoji;
  }

  /**
   *
   * @param pet_emoji This is a  mutator method for the pet_emoji variable.
   * This method is used to control changes to this variable.
   */
  public void setPet_emoji(String pet_emoji) {
    this.pet_emoji = pet_emoji;
  }

  /**
   *
   * @return This is an accessor method for the followers variable.
   * This method returns the value of this private member variable.
   */
  public List<Player> getFollowers() {
    return followers;
  }

  /**
   *
   * @param followers This is a  mutator method for the followers variable.
   * This method is used to control changes to this variable.
   */
  public void setFollowers(List<Player> followers) {
    this.followers = followers;
  }

  /**
   *
   * @return This is an accessor method for the following variable.
   * This method returns the value of this private member variable.
   */
  public List<Player> getFollowing() {
    return following;
  }

  /**
   *
   * @param following This is a  mutator method for the following variable.
   * This method is used to control changes to this variable.
   */
  public void setFollowing(List<Player> following) {
    this.following = following;
  }

  /**
   *
   * @return This is an accessor method for the level variable.
   * This method returns the value of this private member variable.
   */
  public int getLevel() {
    return level;
  }

  /**
   *
   * @param level This is a  mutator method for the level variable.
   * This method is used to control changes to this variable.
   */
  public void setLevel(int level) {
    this.level = level;
  }

  /**
   *
   * @return This is an accessor method for the xp variable.
   * This method returns the value of this private member variable.
   */
  public int getXp() {
    return xp;
  }

  /**
   *
   * @param xp This is a  mutator method for the xp variable.
   * This method is used to control changes to this variable.
   */
  public void setXp(int xp) {
    this.xp = xp;
  }

  /**
   *
   * @return This is an accessor method for the maxXp variable.
   * This method returns the value of this private member variable.
   */
  public int getMaxXp() {
    return maxXp;
  }

  /**
   *
   * @param maxXp This is a  mutator method for the maxXp variable.
   * This method is used to control changes to this variable.
   */
  public void setMaxXp(int maxXp) {
    this.maxXp = maxXp;
  }

  /**
   *
   * @return This is an accessor method for the couragePoints variable.
   * This method returns the value of this private member variable.
   */
  public long getCouragePoints() {
    return couragePoints;
  }

  /**
   *
   * @param couragePoints This is a  mutator method for the couragePoints variable.
   * This method is used to control changes to this variable.
   */
  public void setCouragePoints(long couragePoints) {
    this.couragePoints = couragePoints;
  }

  /**
   *
   * @return This is an accessor method for the couragePointsMax variable.
   * This method returns the value of this private member variable.
   */
  public int getCouragePointsMax() {
    return couragePointsMax;
  }

  /**
   *
   * @param couragePointsMax This is a  mutator method for the couragePointsMax variable.
   * This method is used to control changes to this variable.
   */
  public void setCouragePointsMax(int couragePointsMax) {
    this.couragePointsMax = couragePointsMax;
  }

  /**
   *
   * @return This is an accessor method for the manaPoints variable.
   * This method returns the value of this private member variable.
   */
  public long getManaPoints() {
    return manaPoints;
  }

  /**
   *
   * @param manaPoints This is a  mutator method for the manaPoints variable.
   * This method is used to control changes to this variable.
   */
  public void setManaPoints(long manaPoints) {
    this.manaPoints = manaPoints;
  }

  /**
   *
   * @return This is an accessor method for the manaPointsMax variable.
   * This method returns the value of this private member variable.
   */
  public int getManaPointsMax() {
    return manaPointsMax;
  }

  /**
   *
   * @param manaPointsMax This is a  mutator method for the manaPointsMax variable.
   * This method is used to control changes to this variable.
   */
  public void setManaPointsMax(int manaPointsMax) {
    this.manaPointsMax = manaPointsMax;
  }

  /**
   *
   * @return This is an accessor method for the powerPoints variable.
   *    * This method returns the value of this private member variable.
   */
  public long getPowerPoints() {
    return powerPoints;
  }

  /**
   *
   * @param powerPoints This is a  mutator method for the powrPoints variable.
   * This method is used to control changes to this variable.
   */
  public void setPowerPoints(long powerPoints) {
    this.powerPoints = powerPoints;
  }

  /**
   *
   * @return This is an accessor method for the powerPointsMax variable.
   * This method returns the value of this private member variable.
   */
  public int getPowerPointsMax() {
    return powerPointsMax;
  }

  /**
   *
   * @param powerPointsMax This is a  mutator method for the powerPointsMax variable.
   * This method is used to control changes to this variable.
   */
  public void setPowerPointsMax(int powerPointsMax) {
    this.powerPointsMax = powerPointsMax;
  }

  /**
   *
   * @return This is an accessor method for the healthPoints variable.
   * This method returns the value of this private member variable.
   */
  public long getHealthPoints() {
    return healthPoints;
  }

  /**
   *
   * @param healthPoints This is a  mutator method for the healthPoints variable.
   * This method is used to control changes to this variable.
   */
  public void setHealthPoints(long healthPoints) {
    this.healthPoints = healthPoints;
  }

  /**
   *
   * @return This is an accessor method for the healthPointsMax variable.
   * This method returns the value of this private member variable.
   */
  public int getHealthPointsMax() {
    return healthPointsMax;
  }

  /**
   *
   * @param healthPointsMax This is a  mutator method for the healthPointsMax variable.
   * This method is used to control changes to this variable.
   */
  public void setHealthPointsMax(int healthPointsMax) {
    this.healthPointsMax = healthPointsMax;
  }

  /**
   *
   * @return This is an accessor method for the wall variable.
   * This method returns the value of this private member variable.
   */
  public String getWall() {
    return wall;
  }

  /**
   *
   * @param wall This is a  mutator method for the wall variable.
   * This method is used to control changes to this variable.
   */
  public void setWall(String wall) {
    this.wall = wall;
  }

  //
//  public String getHref() {
//    return href;
//  }
//
//  public void setHref(String href) {
//    this.href = href;
//  }

}
