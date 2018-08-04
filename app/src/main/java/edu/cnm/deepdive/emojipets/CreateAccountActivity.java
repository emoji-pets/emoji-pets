package edu.cnm.deepdive.emojipets;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.emojipets.pojo.Player;
import java.io.IOException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateAccountActivity extends AppCompatActivity {

  private EmojiPetService service;

  private TextView username;
  private TextView emojiName;
  private TextView emoji;

  private EditText usernameEditText;
  private EditText emojiNameEditText;
  private EditText emojiEditText;

  private Button usernameButton;
  private Button emojiNameButton;
  private Button emojiButton;
  private Button createAccountButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_account);

    setupServices();

    username = findViewById(R.id.create_username_textview);
    emojiName = findViewById(R.id.create_petname_textview);
    emoji = findViewById(R.id.create_emoji_textview);

    usernameEditText = findViewById(R.id.create_username_edittext);
    emojiNameEditText = findViewById(R.id.create_petname_edittext);
    emojiEditText = findViewById(R.id.create_emoji_edittext);

    usernameButton = findViewById(R.id.create_username_button);
    emojiNameButton = findViewById(R.id.create_petname_button);
    emojiButton = findViewById(R.id.create_emoji_button);
    createAccountButton = findViewById(R.id.create_account_button);
    createAccountButton.setClickable(false);

    usernameButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        toggleTextInput(username, usernameEditText, usernameButton);
      }
    });

    emojiNameButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        toggleTextInput(emojiName, emojiNameEditText, emojiNameButton);
      }
    });

    emojiButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        toggleTextInput(emoji, emojiEditText, emojiButton);
      }
    });

    username.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        toggleTextInput(username, usernameEditText, usernameButton);
      }
    });

    emojiName.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        toggleTextInput(emojiName, emojiNameEditText, emojiNameButton);
      }
    });

    emoji.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        toggleTextInput(emoji, emojiEditText, emojiButton);
      }
    });

    if (username.getVisibility() == View.GONE && emojiName.getVisibility() == View.GONE
        && emoji.getVisibility() == View.GONE) {
      createAccountButton.setClickable(true);
    }

    createAccountButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Player player = new Player();
        player.setOauthId(EmojiPetApplication.getInstance().getSignInAccount().getId());
        player.setDisplay_name(username.getText().toString());
        player.setPet_name(emojiName.getText().toString());
        player.setPet_emoji(emoji.getText().toString());
        player.setCouragePointsMax(100);
        player.setPowerPointsMax(100);
        player.setHealthPointsMax(100);
        player.setManaPointsMax(100);
        player.setManaPoints(System.currentTimeMillis());
        player.setPowerPoints(System.currentTimeMillis());
        player.setHealthPoints(System.currentTimeMillis());
        player.setCouragePoints(System.currentTimeMillis());
        new PostPlayerTask().execute(player);
      }
    });

  }

  private void setupServices() {
    Gson gson = new GsonBuilder()
        .create();
    service = new Retrofit.Builder()
        .baseUrl(getString(R.string.base_url))
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
