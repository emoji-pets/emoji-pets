package edu.cnm.deepdive.emojipets;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
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
import java.util.Timer;
import java.util.TimerTask;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PetFragment extends Fragment {

  Button power;
  Button mana;
  Button courage;
  Button health;
  Button petStatusButton;

  TextView powerPointsTextView;
  TextView manaPointsTextView;
  TextView couragePointsTextView;
  TextView healthPointsTextView;
  TextView petStatusTextView;
  TextView petTextView;

  TextInputEditText petStatusEdit;

  long powerCurrentTime;
  long manaCurrentTime;
  long courageCurrentTime;
  long healthCurrentTime;

  float powerPoints;
  float manaPoints;
  float couragePoints;
  float healthPoints;

  Timer timer;
  TimerTask timerTask;

  Thread t;

  EmojiPetService service;

  public PetFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment PetFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static PetFragment newInstance(String param1, String param2) {
    PetFragment fragment = new PetFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }



  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View v = inflater.inflate(R.layout.fragment_pet, container, false);

    setupServices();

    power = v.findViewById(R.id.boost_power);
    mana = v.findViewById(R.id.boost_mana);
    courage = v.findViewById(R.id.boost_courage);
    health = v.findViewById(R.id.boost_health);

    powerPointsTextView = (TextView) v.findViewById(R.id.power);
    manaPointsTextView = (TextView) v.findViewById(R.id.mana);
    couragePointsTextView = (TextView) v.findViewById(R.id.courage);
    healthPointsTextView = (TextView) v.findViewById(R.id.health);
    petTextView = (TextView) v.findViewById(R.id.pet);
    petTextView.setText(EmojiPetApplication.getInstance().getPlayer().getPet_emoji());

    setPoint("courage");
    setPoint("health");
    setPoint("mana");
    setPoint("power");

    // long manaPoints = manaPointsMax + (manaTime - currentTime);

    // TODO add pet status endpoints here
    petStatusEdit = v.findViewById(R.id.pet_status_edit);
    petStatusEdit.setText(EmojiPetApplication.getInstance().getPlayer().getStatus());
    petStatusButton = v.findViewById(R.id.pet_status_set_button);

    t = new Thread() {

      @Override
      public void run() {
        try {
          while (!isInterrupted()) {
            Thread.sleep(500);
            getActivity().runOnUiThread(new Runnable() {
              @Override
              public void run() {
                // Here we update the value of points
                setPoint("courage");
                setPoint("health");
                setPoint("mana");
                setPoint("power");
                powerPointsTextView.setText(String.format("%.2f power points", powerPoints));
                manaPointsTextView.setText(String.format("%.2f mana points", manaPoints));
                healthPointsTextView.setText(String.format("%.2f health points", healthPoints));
                couragePointsTextView.setText(String.format("%.2f courage points", couragePoints));
              }
            });
          }
        } catch (InterruptedException e) {
        }
      }
    };

    petStatusButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (petStatusEdit.isFocused()) {
          String text = petStatusEdit.getText().toString();
          petStatusEdit.setFocusable(false);
          EmojiPetApplication.getInstance().getPlayer().setStatus(text);
          new UpdatePlayer().execute(EmojiPetApplication.getInstance().getPlayer());
        } else {
          petStatusEdit.setFocusable(true);
          petStatusEdit.requestFocus();
        }

//        petStatusTextView.setText(text);
//        petStatusEdit.setVisibility(View.GONE);
//        petStatusButton.setVisibility(View.GONE);
//        petStatusTextView.setVisibility(View.VISIBLE);
//        InputMethodManager imm = (InputMethodManager) getContext()
//            .getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
      }
    });

    couragePointsTextView
        .setText(String.format("%.2f courage points", (float) couragePoints));
    manaPointsTextView.setText(String.format("%.2f courage points", (float) manaPoints));
    powerPointsTextView.setText(String.format("%.2f courage points", (float) powerPoints));
    healthPointsTextView.setText(String.format("%.2f courage points", (float) healthPoints));

    power.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        EmojiPetApplication.getInstance().getPlayer().setPowerPoints(System.currentTimeMillis());
        new UpdatePlayer().execute(EmojiPetApplication.getInstance().getPlayer());
      }
    });

    mana.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        EmojiPetApplication.getInstance().getPlayer().setManaPoints(System.currentTimeMillis());
        new UpdatePlayer().execute(EmojiPetApplication.getInstance().getPlayer());
      }
    });

    health.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        EmojiPetApplication.getInstance().getPlayer().setHealthPoints(System.currentTimeMillis());
        new UpdatePlayer().execute(EmojiPetApplication.getInstance().getPlayer());
      }
    });

    courage.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        EmojiPetApplication.getInstance().getPlayer().setCouragePoints(System.currentTimeMillis());
        new UpdatePlayer().execute(EmojiPetApplication.getInstance().getPlayer());
      }
    });

    t.start();

    return v;
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

  @Override
  public void onDetach() {
    super.onDetach();
    t.interrupt();
  }

  private void setPoint(String point) {
    switch (point) {
      case "power":
        powerPoints = (float) EmojiPetApplication.getInstance().getPlayer().getPowerPointsMax() + (float) (
            EmojiPetApplication.getInstance().getPlayer().getPowerPoints() - System.currentTimeMillis()
        )/10000;
        powerPoints = powerPoints < 0 ? 0f : powerPoints;
        break;
      case "mana":
        manaPoints = (float) EmojiPetApplication.getInstance().getPlayer().getManaPointsMax() + (float) (
            EmojiPetApplication.getInstance().getPlayer().getManaPoints() - System.currentTimeMillis()
        )/10000;
        manaPoints = manaPoints < 0 ? 0f : manaPoints;
        break;
      case "courage":
        couragePoints = (float) EmojiPetApplication.getInstance().getPlayer().getCouragePointsMax() + (float) (
            EmojiPetApplication.getInstance().getPlayer().getCouragePoints() - System.currentTimeMillis()
        )/10000;
        couragePoints = couragePoints < 0 ? 0f : couragePoints;
        break;
      case "health":
        healthPoints = (float) EmojiPetApplication.getInstance().getPlayer().getHealthPointsMax() + (float) (
            EmojiPetApplication.getInstance().getPlayer().getHealthPoints() - System.currentTimeMillis()
        )/10000;
        healthPoints = healthPoints < 0 ? 0f : healthPoints;
    }
  }

  private class UpdatePlayer extends AsyncTask<Player, Void, Void> {

    @Override
    protected Void doInBackground(Player... players) {
      Player player = null;
      try {
        String token = EmojiPetApplication.getInstance().getSignInAccount().getIdToken();

        Response<Player> response = service.updatePlayer(
            getString(R.string.oauth2_header_format, token), players[0]).execute();
        if (response.isSuccessful()) {
          player = response.body();
          EmojiPetApplication.getInstance().setPlayer(player);
        }
      } catch (IOException e) {

      } finally {
        if (player == null) {
          cancel(true);
        }
      }
      return null;
    }
  }

}
