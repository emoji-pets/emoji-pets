package edu.cnm.deepdive.emojipets;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.emojipets.pojo.Player;
import java.io.IOException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This is the CreateAccountActivity class which hosts the "Create Account" page of the app.
 * This holds the code for creating usernames, pet names, and the emoji of the user.
 */
public class CreateAccountActivity extends AppCompatActivity {

  private EmojiPetService service;

  private TextInputEditText usernameEditText;
  private TextInputEditText emojiNameEditText;
  private TextInputEditText emojiEditText;

  private Button createAccountButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_account);
//    getActionBar().setDisplayHomeAsUpEnabled(true);
//
//    if (getSupportActionBar() != null) {
//      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//      getSupportActionBar().setDisplayShowHomeEnabled(true);
//    }

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        EmojiPetApplication application = EmojiPetApplication.getInstance();
        application.getSignInClient().signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task) {
            EmojiPetApplication.getInstance().setSignInAccount(null);
          }
        });
        finish();
      }
    });

    setupServices();

    usernameEditText = findViewById(R.id.create_username_edittext);
    emojiNameEditText = findViewById(R.id.create_petname_edittext);
    emojiEditText = findViewById(R.id.create_emoji_edittext);

    createAccountButton = findViewById(R.id.create_account_button);
    createAccountButton.setClickable(false);

    if (!usernameEditText.getText().toString().equals("") &&
        !emojiEditText.getText().toString().equals("") &&
        !emojiNameEditText.getText().toString().equals("")) {
      createAccountButton.setClickable(true);
    }

    createAccountButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Player player = new Player();
        player.setOauthId(EmojiPetApplication.getInstance().getSignInAccount().getId());
        player.setDisplay_name(usernameEditText.getText().toString());
        player.setPet_name(emojiNameEditText.getText().toString());
        player.setPet_emoji(emojiEditText.getText().toString());
        player.setCouragePointsMax(100);
        player.setPowerPointsMax(100);
        player.setHealthPointsMax(100);
        player.setManaPointsMax(100);
        player.setManaPoints(System.currentTimeMillis());
        player.setPowerPoints(System.currentTimeMillis());
        player.setHealthPoints(System.currentTimeMillis());
        player.setCouragePoints(System.currentTimeMillis());
        player.setStatus("I'm a blank status. Tap on me! \uD83D\uDC49");
        new PostPlayerTask().execute(player);
      }
    });

  }

  private void setupServices() {
    Gson gson = new GsonBuilder()
        .create();
    service = new Retrofit.Builder()
        .baseUrl(getString(R.string.base_url_aws))
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(EmojiPetService.class);
  }

  private void toggleTextInput(TextView textView, EditText editText, Button button) {
    if (textView.getVisibility() == View.VISIBLE) {
      String text = textView.getText().toString();
      editText.setVisibility(View.VISIBLE);
      button.setVisibility(View.VISIBLE);
      editText.setText(text);
      textView.setVisibility(View.GONE);
    } else {
      String text = editText.getText().toString();
      textView.setVisibility(View.VISIBLE);
      textView.setText(text);
      editText.setVisibility(View.GONE);
      button.setVisibility(View. GONE);
    }
  }

  private class PostPlayerTask extends AsyncTask<Player, Void, Player> {


    @Override
    protected Player doInBackground(Player... players) {
      Player player = null;
      try {
        String token = EmojiPetApplication.getInstance().getSignInAccount().getIdToken();

        Response<Player> response = service.addUser(
            getString(R.string.oauth2_header_format, token), players[0]).execute();
        if (response.isSuccessful()) {
          player = response.body();
        }
      } catch (IOException e) {

      } finally {
        if (player == null) {
          cancel(true);
        }
      }
      return player;
    }

    @Override
    protected void onCancelled() {
      Toast.makeText(CreateAccountActivity.this, "Sorry, it doesn't look like things are working out for you right now",
          Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPostExecute(Player player) {
      EmojiPetApplication.getInstance().setPlayer(player);
      Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
    }
  }

}
