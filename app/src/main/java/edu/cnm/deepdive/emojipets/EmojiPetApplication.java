package edu.cnm.deepdive.emojipets;

import android.app.Application;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import edu.cnm.deepdive.emojipets.pojo.Player;
import java.util.List;
import java.util.Map;

/**
 * This is the EmojiPetApplication class and it hosts to "Google Sign In."
 * This class extends the Application class to override the onCreate method.
 * It contains setters and getters for players, signInClient, instance,
 * allPlayers, playerNameToIdMap, friendPeak, and signInAccount variables.
 * It also holds the requestedIdToken.
 */
public class EmojiPetApplication extends Application {

  private static EmojiPetApplication instance;

  private GoogleSignInClient signInClient;
  private GoogleSignInAccount signInAccount;
  private String sfId;
  private String sfToken;
  private Player player;
  private List<Player> allPlayers;
  private Map<String, String> playerNameToIdMap;
  private String friendPeak;
  private boolean usingForce;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    GoogleSignInOptions options = new GoogleSignInOptions.Builder()
        .requestEmail()
        .requestId()
        .requestIdToken(getString(R.string.web_client_id))
        .build();
    signInClient = GoogleSignIn.getClient(this, options);
  }

  /**
   *
   * @return This is an accessor method for the instance variable.
   * This method returns the value of this private member variable.
   */
  public static EmojiPetApplication getInstance() {
    return instance;
  }

  /**
   *
   * @return This is an accessor method for the signInClient.
   * This method returns the value of this private member variable.
   */
  public GoogleSignInClient getSignInClient() {
    return signInClient;
  }

  /**
   *
   * @param signInClient This is a  mutator method for the signInClient variable.
   * This method is used to control changes to this variable.
   */
  public void setSignInClient(GoogleSignInClient signInClient) {
    this.signInClient = signInClient;
  }

  /**
   *
   * @return This is an accessor method for the signInAccount variable.
   * This method returns the value of this private member variable.
   */
  public GoogleSignInAccount getSignInAccount() {
    return signInAccount;
  }

  /**
   *
   * @param signInAccount This is a  mutator method for the signInAccount variable.
   * This method is used to control changes to this variable.
   */
  public void setSignInAccount(GoogleSignInAccount signInAccount) {
    this.signInAccount = signInAccount;
  }

  /**
   *
   * @return This is an accessor method for the player variable.
   * This method returns the value of this private member variable.
   */
  public Player getPlayer() {
    return player;
  }

  /**
   *
   * @param player This is a  mutator method for the player variable.
   * This method is used to control changes to this variable.
   */
  public void setPlayer(Player player) {
    this.player = player;
  }

  /**
   *
   * @return This is an accessor method for the allPlayers variable.
   * This method returns the value of this private member variable.
   */
  public List<Player> getAllPlayers() {
    return allPlayers;
  }

  /**
   *
   * @param allPlayers This is a  mutator method for the allPlayers variable.
   * This method is used to control changes to this variable.
   */
  public void setAllPlayers(List<Player> allPlayers) {
    this.allPlayers = allPlayers;
  }

  /**
   *
   * @return This is an accessor method for the playerNameToIdMap variable.
   * This method returns the value of this private member variable.
   */
  public Map<String, String> getPlayerNameToIdMap() {
    return playerNameToIdMap;
  }

  /**
   *
   * @param playerNameToIdMap This is a  mutator method for the playerNameToIdMap variable.
   * This method is used to control changes to this variable.
   */
  public void setPlayerNameToIdMap(Map<String, String> playerNameToIdMap) {
    this.playerNameToIdMap = playerNameToIdMap;
  }

  /**
   *
   * @return This is an accessor method for the friendPeak variable.
   * This method returns the value of this private member variable.
   */
  public String getFriendPeak() {
    return friendPeak;
  }

  /**
   *
   * @param friendPeak This is a  mutator method for the friendPeak variable.
   * This method is used to control changes to this variable.
   */
  public void setFriendPeak(String friendPeak) {
    this.friendPeak = friendPeak;
  }

  /**
   *
   * @return This is an accessor method for the sfId variable.
   * This method returns the value of this private member variable.
   */
  public String getSfId() {
    return sfId;
  }

  /**
   *
   * @param sfId This is a  mutator method for the sfId variable.
   * This method is used to control changes to this variable.
   */
  public void setSfId(String sfId) {
    this.sfId = sfId;
  }

  /**
   *
   * @return This is an accessor method for the usingForce variable.
   * This method returns the value of this private member variable.
   */
  public boolean isUsingForce() {
    return usingForce;
  }

  /**
   *
   * @param usingForce This is a  mutator method for the usingForce variable.
   * This method is used to control changes to this variable.
   */
  public void setUsingForce(boolean usingForce) {
    this.usingForce = usingForce;
  }

  /**
   *
   * @return This is an accessor method for the sfToken variable.
   * This method returns the value of this private member variable.
   */
  public String getSfToken() {
    return sfToken;
  }

  /**
   *
   * @param sfToken This is a  mutator method for the sfToken variable.
   * This method is used to control changes to this variable.
   */
  public void setSfToken(String sfToken) {
    this.sfToken = sfToken;
  }
}
