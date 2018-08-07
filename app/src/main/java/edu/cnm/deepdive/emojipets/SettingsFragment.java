package edu.cnm.deepdive.emojipets;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.emojipets.pojo.Player;
import java.io.IOException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

  EmojiPetService service;

  EditText usernameEditText;
  EditText petNameEditText;
  EditText emojiCharEditText;

  Button usernameButton;
  Button petNameButton;
  Button emojiCharButton;

  public SettingsFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_settings, container, false);
    getActivity().setTitle("Settings");

    usernameEditText = view.findViewById(R.id.settings_username_edit_text);
    petNameEditText = view.findViewById(R.id.settings_pet_name_edit_text);
    emojiCharEditText = view.findViewById(R.id.settings_emoji_character_edit_text);

    usernameButton = view.findViewById(R.id.settings_username_button);
    petNameButton = view.findViewById(R.id.settings_pet_name_button);
    emojiCharButton = view.findViewById(R.id.settings_emoji_character_button);

    setupServices();

    usernameEditText.setText(EmojiPetApplication.getInstance().getPlayer().getDisplay_name());
    petNameEditText.setText(EmojiPetApplication.getInstance().getPlayer().getPet_name());
    emojiCharEditText.setText(EmojiPetApplication.getInstance().getPlayer().getPet_emoji());

    usernameButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        String text = usernameEditText.getText().toString();
        EmojiPetApplication.getInstance().getPlayer().setDisplay_name(text);
        new UpdatePlayer().execute();
        usernameEditText.clearFocus();
        InputMethodManager imm = (InputMethodManager) getContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
      }
    });

    petNameButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        String text = petNameEditText.getText().toString();
        EmojiPetApplication.getInstance().getPlayer().setPet_name(text);
        new UpdatePlayer().execute();
        petNameEditText.clearFocus();
        InputMethodManager imm = (InputMethodManager) getContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
      }
    });

    emojiCharButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        String text = emojiCharEditText.getText().toString();
        EmojiPetApplication.getInstance().getPlayer().setPet_emoji(text);
        new UpdatePlayer().execute();
        petNameEditText.clearFocus();
        InputMethodManager imm = (InputMethodManager) getContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
      }
    });

    return view;
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

  private class UpdatePlayer extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
      try {
        String token = EmojiPetApplication.getInstance().getSignInAccount().getIdToken();
        Response<Player> response = service.updatePlayer(
            getString(R.string.oauth2_header_format, token),
            EmojiPetApplication.getInstance().getSignInAccount().getId(),
            EmojiPetApplication.getInstance().getPlayer()).execute();
        if (response.isSuccessful()) {
          Player player = response.body();
          EmojiPetApplication.getInstance().setPlayer(player);
        }
      } catch (IOException e) {

      } finally {
//        if (player == null) {
//          cancel(true);
//        }
      }
      return null;
    }
  }

}
