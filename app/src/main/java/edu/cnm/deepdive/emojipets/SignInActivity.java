package edu.cnm.deepdive.emojipets;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
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
import edu.cnm.deepdive.emojipets.pojo.SfAuthenticator;
import edu.cnm.deepdive.emojipets.pojo.SfCredentials;
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

  private EmojiPetService emojiPetService;
  private AuthenticateService sfAuthenticatorService;
  private Player player;
  private SfCredentials sfCredentials;
  private Gson gson;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_in);

    // Button for logging in to google to play the app.
    Button signIn = findViewById(R.id.imageButton1);
    signIn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
//        EmojiPetApplication.getInstance().getSignInClient().signOut();
//        EmojiPetApplication.getInstance().setSignInAccount(null);
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
    gson = new GsonBuilder()
        .create();
    AlertDialog.Builder dialog = new Builder(this);
    View dialogView = getLayoutInflater().inflate(R.layout.choose_backend_dialog, null);
    Button aws = dialogView.findViewById(R.id.dialog_use_aws_button);
    Button sf = dialogView.findViewById(R.id.dialog_use_salesforce_button);
    dialog.setView(dialogView);
    final AlertDialog dialogBuilt = dialog.create();
    dialogBuilt.show();
    aws.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        EmojiPetApplication.getInstance().setUsingForce(false);
        emojiPetService = new Retrofit.Builder()
            .baseUrl(getString(R.string.base_url_aws))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(EmojiPetService.class);
        new StartAWS().execute();
      }
    });
    sf.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        EmojiPetApplication.getInstance().setUsingForce(true);
        sfAuthenticatorService = new Retrofit.Builder()
            .baseUrl(getString(R.string.base_url_sf_authenticate))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(AuthenticateService.class);
        // Call to get token
        sfCredentials = new SfCredentials();
        sfCredentials.setUsername("jt@salesforce.com");
        sfCredentials.setPassword("W@rm2020jlQEUlHx7XENFopw5AP3qapj");
        new GetSalesForceUserData().execute();
        // New service with url to activate for SF

      }
    });
//    service = new Retrofit.Builder()
//        .baseUrl(getString(R.string.base_url_aws))
//        .addConverterFactory(GsonConverterFactory.create(gson))
//        .build()
//        .create(EmojiPetService.class);
//
//    new GetAccountTask().execute();
  }

  private class StartAWS extends AsyncTask<Void, Void, Void> {

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
        Response<Player> response = emojiPetService.get(getString(R.string.oauth2_header_format, token), personId).execute();
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

  private class GetSalesForceUserData extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPreExecute() {
      // progressSpinner.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids) {
      SfAuthenticator sfAuthenticator = null;
      try {
        Response<SfAuthenticator> response = sfAuthenticatorService.getSfToken(sfCredentials).execute();
        SfAuthenticator a = response.body();
        if (response.isSuccessful()) {
          sfAuthenticator = response.body();
          int indexOf = sfAuthenticator.getId().indexOf("id");
          String id = sfAuthenticator.getId().substring(indexOf + 3).replace("/", "");
          EmojiPetApplication.getInstance().setSfToken(sfAuthenticator.getAccessToken());
          EmojiPetApplication.getInstance().setSfId(id);
        }
      } catch (IOException e) {
        // Do nothing; passphrase is still null.
      } catch (Exception e) {
        throw e;
      } finally {
        if (sfAuthenticator == null) {
          cancel(true);
        }
      }
      return null;
    }

    @Override
    protected void onCancelled() {
      Toast.makeText(SignInActivity.this, "Sorry, SalesForce didn't go through.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      emojiPetService = new Retrofit.Builder()
          .baseUrl(getString(R.string.base_url_sf_actual))
          .addConverterFactory(GsonConverterFactory.create(gson))
          .build()
          .create(EmojiPetService.class);
      new StartSf().execute();
    }

  }

  private class StartSf extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
      Player player = null;
      try {
        String token = EmojiPetApplication.getInstance().getSfToken();
        String personId = EmojiPetApplication.getInstance().getSfId();
        Response<Player> response = emojiPetService.get(getString(R.string.oauth2_header_format, token), personId).execute();
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
  }

}

