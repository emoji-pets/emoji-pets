package edu.cnm.deepdive.emojipets;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.emojipets.pojo.Player;
import java.io.IOException;
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
}

