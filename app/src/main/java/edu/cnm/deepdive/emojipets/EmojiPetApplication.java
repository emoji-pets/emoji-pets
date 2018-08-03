package edu.cnm.deepdive.emojipets;

import android.app.Application;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import edu.cnm.deepdive.emojipets.pojo.Player;

public class EmojiPetApplication extends Application {

  private static EmojiPetApplication instance;

  private GoogleSignInClient signInClient;
  private GoogleSignInAccount signInAccount;
  private Player player;

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
}
