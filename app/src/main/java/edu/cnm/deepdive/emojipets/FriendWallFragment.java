package edu.cnm.deepdive.emojipets;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.emojipets.pojo.Player;
import java.io.IOException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * This fragment is the page of the user's friend
 * where the user can write on his or her friend's wall.
 */
public class FriendWallFragment extends Fragment {

  private EmojiPetService service;
  private TextInputEditText wall;
  private Button wallButton;
  private Button cancelButton;
  private String id;
  private Player friend;
  private String wallText;

  public FriendWallFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_friend_wall, container, false);

    wall = view.findViewById(R.id.wall_edit_text);
    cancelButton = view.findViewById(R.id.wall_cancel_button);
    wallButton = view.findViewById(R.id.wall_submit_button);

    id = EmojiPetApplication.getInstance().getFriendPeak();

    setupServices();

    new SetupWall().execute();

    wall.setText("");

    cancelButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        wall.clearFocus();
        InputMethodManager imm = (InputMethodManager) getContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        wall.setText(wallText);
      }
    });

    wallButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        wallText = wall.getText().toString();
        friend.setWall(wallText);
        new UpdateFriend().execute();
        wall.clearFocus();
        InputMethodManager imm = (InputMethodManager) getContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        Toast.makeText(getContext(), getString(R.string.wall_marked), Toast.LENGTH_SHORT).show();
      }
    });

    return view;
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

  private class SetupWall extends AsyncTask<Void,Void,Void> {

    @Override
    protected Void doInBackground(Void... voids) {
      try {
        String token = EmojiPetApplication.getInstance().getSignInAccount().getIdToken();
        Response<Player> response = service.get(
            getString(R.string.oauth2_header_format, token),
            id).execute();
        if (response.isSuccessful()) {
          friend = response.body();
        }
      } catch (IOException e) {

      } finally {

      }
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      wallText = friend.getWall();
      wall.setText(wallText);
    }
  }

  private class UpdateFriend extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
      try {
        String token = EmojiPetApplication.getInstance().getSignInAccount().getIdToken();
        Response<Player> response = service.updatePlayer(
            getString(R.string.oauth2_header_format, token),
            EmojiPetApplication.getInstance().getSignInAccount().getId(),
            friend).execute();
        if (response.isSuccessful()) {
          Player player = response.body();
          friend = player;
        }
      } catch (IOException e) {

      } finally {

      }
      return null;
    }
  }

}
