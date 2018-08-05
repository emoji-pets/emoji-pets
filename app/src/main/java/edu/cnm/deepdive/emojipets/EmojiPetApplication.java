package edu.cnm.deepdive.emojipets;

import android.app.Application;
import android.os.AsyncTask;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.emojipets.pojo.Player;
import java.util.List;
import java.util.Map;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmojiPetApplication extends Application {

  private static EmojiPetApplication instance;

  private GoogleSignInClient signInClient;
  private GoogleSignInAccount signInAccount;
  private Player player;
  private List<Player> allPlayers;
  private Map<String, String> playerNameToIdMap;

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

  public static EmojiPetApplication getInstance() {
    return instance;
  }

  public GoogleSignInClient getSignInClient() {
    return signInClient;
  }

  public void setSignInClient(GoogleSignInClient signInClient) {
    this.signInClient = signInClient;
  }

  public GoogleSignInAccount getSignInAccount() {
    return signInAccount;
  }

  public void setSignInAccount(GoogleSignInAccount signInAccount) {
    this.signInAccount = signInAccount;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public List<Player> getAllPlayers() {
    return allPlayers;
  }

  public void setAllPlayers(List<Player> allPlayers) {
    this.allPlayers = allPlayers;
  }

  public Map<String, String> getPlayerNameToIdMap() {
    return playerNameToIdMap;
  }

  public void setPlayerNameToIdMap(Map<String, String> playerNameToIdMap) {
    this.playerNameToIdMap = playerNameToIdMap;
  }
}
