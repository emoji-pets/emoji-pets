package edu.cnm.deepdive.emojipets;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.emojipets.pojo.Player;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This is the SignInActivity Class. It contains a lot of button animations. It hosts to the
 * logging in activities.
 */
public class SignInActivity extends AppCompatActivity {

  private static final int REQUEST_CODE = 1000;

  private EmojiPetService service;
  private Player player;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_in);

    // Button for logging in to google to play the app.
    Button signIn = findViewById(R.id.imageButton1);
    signIn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = EmojiPetApplication.getInstance().getSignInClient().getSignInIntent();
        startActivityForResult(intent, REQUEST_CODE);
      }
    });

    //Background animation starter for the background of the SignInActivity.
    ConstraintLayout constraintLayout = findViewById(R.id.layout);
    AnimationDrawable animationDrawable = (AnimationDrawable)constraintLayout.getBackground();
    animationDrawable.setEnterFadeDuration(2000);
    animationDrawable.setExitFadeDuration(4000);
    animationDrawable.start();

    final ImageButton imageButton2 = findViewById(R.id.imageButton2);
    imageButton2.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Animation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(1000);
        imageButton2.startAnimation(animation);
      }
    });
//    // Rotation animation for a button.
//    final ImageButton imageButton3 = findViewById(R.id.imageButton3);
//    imageButton3.setOnClickListener(new OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        imageButton3.animate().rotation(imageButton3.getRotation()-360).start();
//      }
//    });

//    // Fade animation for a button.
//    final ImageButton imageButton2 = findViewById(R.id.imageButton2);
//    imageButton2.setOnClickListener(new OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        Animation animation = new AlphaAnimation(1.0f, 0.0f);
//        animation.setDuration(1000);
//        imageButton2.startAnimation(animation);
//      }
//    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_CODE) {
      try {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        GoogleSignInAccount account = task.getResult(ApiException.class);
        EmojiPetApplication.getInstance().setSignInAccount(account);
        getAccount();
      } catch (ApiException e) {
        Toast.makeText(this, "Unable to sign in.  Please check you credentials and connection.",
            Toast.LENGTH_LONG).show();
      }
    }
  }
  private void getAccount() {
    // make first request to server to test if player exists with for this account
    Gson gson = new GsonBuilder()
        .create();
    service = new Retrofit.Builder()
        .baseUrl(getString(R.string.base_url))
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(EmojiPetService.class);

    new GetAccountTask().execute();
  }

  private class GetAccountTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPreExecute() {
      // progressSpinner.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids) {
      Player player = null;
      try {
        String token = EmojiPetApplication.getInstance().getSignInAccount().getIdToken();
        String personId = EmojiPetApplication.getInstance().getSignInAccount().getId();
        Response<Player> response = service.get(getString(R.string.oauth2_header_format, token), personId).execute();
        if (response.isSuccessful()) {
          player = response.body();
          EmojiPetApplication.getInstance().setPlayer(player);
        }
      } catch (IOException e) {
        // Do nothing; passphrase is still null.
      } finally {
        if (player == null) {
          cancel(true);
        }
      }
      return null;
    }

    @Override
    protected void onCancelled() {
      Intent intent = new Intent(SignInActivity.this, CreateAccountActivity.class);
      startActivity(intent);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      Intent intent = new Intent(SignInActivity.this, MainActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
    }

  }

  //  // Tried having sliding transition animation when switching to another activity but didn't work (no effect) LR
//  public void openNewGameActivity(View view) {
//    Intent intent = new Intent(this, NewGameActivity.class);
//    startActivity(intent);
//    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//  }
}

//Commented out animations might be of use later.

