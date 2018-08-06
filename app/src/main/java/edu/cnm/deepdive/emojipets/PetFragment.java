package edu.cnm.deepdive.emojipets;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;
import java.util.Timer;
import java.util.TimerTask;


public class PetFragment extends Fragment {

  SparkButton power;
  SparkButton mana;
  SparkButton courage;
  SparkButton health;
  ImageButton petStatusButton;

  TextView powerPoints;
  TextView manaPoints;
  TextView couragePoints;
  TextView healthPoints;
  TextView petStatus;

  EditText petStatusEdit;

  long powerCurrentTime;
  long manaCurrentTime;
  long courageCurrentTime;
  long healthCurrentTime;

  float powerCurrentValue;
  float manaCurrentValue;
  float courageCurrentValue;
  float healthCurrentValue;

  Timer timer;
  TimerTask timerTask;

  Thread t;

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

    ConstraintLayout constraintLayout = v.findViewById(R.id.pet_layout);
    AnimationDrawable animationDrawable = (AnimationDrawable)constraintLayout.getBackground();
    animationDrawable.setEnterFadeDuration(2000);
    animationDrawable.setExitFadeDuration(4000);
    animationDrawable.start();

    power = v.findViewById(R.id.potty);
    mana = v.findViewById(R.id.play);
    courage = v.findViewById(R.id.cuddles);
    health = v.findViewById(R.id.food);

    powerPoints = (TextView) v.findViewById(R.id.power);
    manaPoints = (TextView) v.findViewById(R.id.mana);
    couragePoints = (TextView) v.findViewById(R.id.courage);
    healthPoints = (TextView) v.findViewById(R.id.health);

    powerCurrentTime = System.currentTimeMillis() + 100200;
    manaCurrentTime = System.currentTimeMillis() + 100200;
    courageCurrentTime = System.currentTimeMillis() + 100200;
    healthCurrentTime = System.currentTimeMillis() + 100200;

    powerCurrentValue = (float) powerCurrentTime;
    manaCurrentValue = (float) manaCurrentTime;
    courageCurrentValue = (float) courageCurrentTime;
    healthCurrentValue = (float) healthCurrentTime;

    // TODO add pet status endpoints here
    petStatus = v.findViewById(R.id.pet_status);
    petStatus.setText("TEST TEXT");
    petStatusEdit = v.findViewById(R.id.pet_status_edit);
    petStatusEdit.setVisibility(View.GONE);
    petStatusButton = v.findViewById(R.id.pet_status_set_button);
    petStatusButton.setVisibility(View.GONE);

    // TODO just fix the button from being pushed off screen
    DisplayMetrics displayMetrics = new DisplayMetrics();
    ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    int width = displayMetrics.widthPixels;
    petStatusEdit.setMaxWidth(width - 240);

    t = new Thread() {

      @Override
      public void run() {
        try {
          while (!isInterrupted()) {
            Thread.sleep(100);
            getActivity().runOnUiThread(new Runnable() {
              @Override
              public void run() {
                // Here we update the value of points
                powerCurrentValue = powerCurrentTime - System.currentTimeMillis();
                powerCurrentValue = powerCurrentValue < 0 ? 0 : powerCurrentValue;
                powerCurrentValue = powerCurrentValue > 100000 ? 100000 : powerCurrentValue;
                powerPoints.setText(String.format("%.2f Potty", (float) powerCurrentValue / 1000));
                manaCurrentValue = manaCurrentTime - System.currentTimeMillis();
                manaCurrentValue = manaCurrentValue < 0 ? 0 : manaCurrentValue;
                manaCurrentValue = manaCurrentValue > 100000 ? 100000 : manaCurrentValue;
                manaPoints.setText(String.format("%.2f Play", (float) manaCurrentValue / 1000));
                healthCurrentValue = healthCurrentTime - System.currentTimeMillis();
                healthCurrentValue = healthCurrentValue < 0 ? 0 : healthCurrentValue;
                healthCurrentValue = healthCurrentValue > 100000 ? 100000 : healthCurrentValue;
                healthPoints.setText(String.format("%.2f Hunger", (float) healthCurrentValue / 1000));
                courageCurrentValue = courageCurrentTime - System.currentTimeMillis();
                courageCurrentValue = courageCurrentValue < 0 ? 0 : courageCurrentValue;
                courageCurrentValue = courageCurrentValue > 100000 ? 100000 : courageCurrentValue;
                couragePoints.setText(String.format("%.2f Cuddle", (float) courageCurrentValue / 1000));
              }
            });
          }
        } catch (InterruptedException e) {
        }
      }
    };

    petStatus.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        // TODO add pet status put endpoints here
        String text = petStatus.getText().toString();
        petStatusEdit.setText(text);
        petStatus.setVisibility(View.GONE);
        petStatusEdit.setSelectAllOnFocus(true);
        petStatusEdit.requestFocus();
        petStatusEdit.setVisibility(View.VISIBLE);
        petStatusButton.setVisibility(View.VISIBLE);
        petStatusButton.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            String text = petStatusEdit.getText().toString();
            petStatusEdit.clearFocus();
            petStatus.setText(text);
            petStatusEdit.setVisibility(View.GONE);
            petStatusButton.setVisibility(View.GONE);
            petStatus.setVisibility(View.VISIBLE);
            InputMethodManager imm = (InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
          }
        });
      }
    });

    powerPoints.setText(String.format("%.2f potty", (powerCurrentValue) / 1000));
    manaPoints.setText(String.format("%.2f play", (manaCurrentValue) / 1000));
    couragePoints.setText(String.format("%.2f cuddle", (courageCurrentValue)/ 1000));
    healthPoints.setText(String.format("%.2f hunger", (healthCurrentValue)/ 1000));

    health.setEventListener(new SparkEventListener() {
      @Override
      public void onEvent(ImageView button, boolean buttonState) {
        healthCurrentTime = System.currentTimeMillis() + 100200;
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
        courageCurrentTime = System.currentTimeMillis() + 100200;
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
        manaCurrentTime = System.currentTimeMillis() + 100200;
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
        powerCurrentTime = System.currentTimeMillis() + 100200;
      }

      @Override
      public void onEventAnimationEnd(ImageView button, boolean buttonState) {
        power.setChecked(false);
      }

      @Override
      public void onEventAnimationStart(ImageView button, boolean buttonState) {

      }
    });

    t.start();

    return v;
  }

  @Override
  public void onDetach() {
    super.onDetach();
    t.interrupt();
  }
}
