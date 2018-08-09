package edu.cnm.deepdive.emojipets;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;
import edu.cnm.deepdive.emojipets.pojo.Player;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This is the PetFragment subclass. This hosts to the main screen of the app.
 * This is where the user wall. This contains the codes for interacting with
 * the user's emoji petz.
 */
public class PetFragment extends Fragment {

  SparkButton power;
  SparkButton mana;
  SparkButton courage;
  SparkButton health;
  ImageButton petStatusButton;

  TextView pottyPointsTextView;
  TextView playPointsTextView;
  TextView cuddlePointsTextView;
  TextView hungerPointsTextView;
  TextView petStatusTextView;
  TextView petTextView;

  TextInputEditText petStatusEdit;

  LottieAnimationView animationView;

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
    getActivity().setTitle(EmojiPetApplication.getInstance().getPlayer().getPet_name());

    setupServices();

    ConstraintLayout constraintLayout = v.findViewById(R.id.pet_layout);
    AnimationDrawable animationDrawable = (AnimationDrawable)constraintLayout.getBackground();
    animationDrawable.setEnterFadeDuration(2000);
    animationDrawable.setExitFadeDuration(4000);
    animationDrawable.start();


    power = v.findViewById(R.id.potty);
    mana = v.findViewById(R.id.play);
    courage = v.findViewById(R.id.cuddles);
    health = v.findViewById(R.id.food);

    pottyPointsTextView = (TextView) v.findViewById(R.id.potty_top);
    playPointsTextView = (TextView) v.findViewById(R.id.play_top);
    cuddlePointsTextView = (TextView) v.findViewById(R.id.cuddle);
    hungerPointsTextView = (TextView) v.findViewById(R.id.hunger_top);
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

    v.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        v.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
      }
    });

    petStatusEdit.setOnFocusChangeListener(new OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
          petStatusButton.setVisibility(View.VISIBLE);
        } else {
          petStatusButton.setVisibility(View.GONE);
        }
      }
    });
    petStatusButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (petStatusEdit.isFocused()) {
          String text = petStatusEdit.getText().toString();
          EmojiPetApplication.getInstance().getPlayer().setStatus(text);
          new UpdatePlayer().execute();
          petStatusEdit.clearFocus();
          InputMethodManager imm = (InputMethodManager) getContext()
              .getSystemService(Context.INPUT_METHOD_SERVICE);
          imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } else {
          petStatusEdit.setFocusable(true);
          petStatusEdit.requestFocus();
        }
      }
    });

    pottyPointsTextView.setText(String.format(getString(R.string.hunger_points), powerPoints));
    playPointsTextView.setText(String.format(getString(R.string.poopy_points), manaPoints));
    hungerPointsTextView.setText(String.format(getString(R.string.play_points), healthPoints));
    cuddlePointsTextView.setText(String.format(getString(R.string.love_points), couragePoints));

    health.setEventListener(new SparkEventListener() {
      @Override
      public void onEvent(ImageView button, boolean buttonState) {
        EmojiPetApplication.getInstance().getPlayer().setHealthPoints(System.currentTimeMillis());
        new UpdatePlayer().execute();
      }
      
      @Override
      public void onEventAnimationEnd(ImageView button, boolean buttonState) {
        health.setChecked(false);
      }

      @Override
      public void onEventAnimationStart(ImageView button, boolean buttonState) {

      }
    });

    courage.setEventListener(new SparkEventListener() {
      @Override
      public void onEvent(ImageView button, boolean buttonState) {
        EmojiPetApplication.getInstance().getPlayer().setCouragePoints(System.currentTimeMillis());
        new UpdatePlayer().execute();
      }

      @Override
      public void onEventAnimationEnd(ImageView button, boolean buttonState) {
        courage.setChecked(false);
      }

      @Override
      public void onEventAnimationStart(ImageView button, boolean buttonState) {

      }
    });
    
    mana.setEventListener(new SparkEventListener() {
      @Override
      public void onEvent(ImageView button, boolean buttonState) {
        EmojiPetApplication.getInstance().getPlayer().setManaPoints(System.currentTimeMillis());
        new UpdatePlayer().execute();
      }

      @Override
      public void onEventAnimationEnd(ImageView button, boolean buttonState) {
        mana.setChecked(false);
      }

      @Override
      public void onEventAnimationStart(ImageView button, boolean buttonState) {

      }
    });

    power.setEventListener(new SparkEventListener() {
      @Override
      public void onEvent(ImageView button, boolean buttonState) {
        EmojiPetApplication.getInstance().getPlayer().setPowerPoints(System.currentTimeMillis());
        new UpdatePlayer().execute();
      }

      @Override
      public void onEventAnimationEnd(ImageView button, boolean buttonState) {
        power.setChecked(false);
      }

      @Override
      public void onEventAnimationStart(ImageView button, boolean buttonState) {
      }
    });

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
                pottyPointsTextView
                    .setText(String.format(getString(R.string.poopy_points), powerPoints));
                playPointsTextView
                    .setText(String.format(getString(R.string.play_points), manaPoints));
                hungerPointsTextView
                    .setText(String.format(getString(R.string.hunger_points), healthPoints));
                cuddlePointsTextView
                    .setText(String.format(getString(R.string.love_points), couragePoints));
              }
            });
          }
        } catch (InterruptedException e) {
        }
      }
    };

    t.start();

    return v;
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
