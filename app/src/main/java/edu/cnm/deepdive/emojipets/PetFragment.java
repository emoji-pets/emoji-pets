package edu.cnm.deepdive.emojipets;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link PetFragment.OnFragmentInteractionListener} interface to handle interaction events. Use the
 * {@link PetFragment#newInstance} factory method to create an instance of this fragment.
 */
public class PetFragment extends Fragment {

  Button power;
  Button mana;
  Button courage;
  Button health;

  TextView powerPoints;
  TextView manaPoints;
  TextView couragePoints;
  TextView healthPoints;

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

    power = v.findViewById(R.id.boost_power);
    mana = v.findViewById(R.id.boost_mana);
    courage = v.findViewById(R.id.boost_courage);
    health = v.findViewById(R.id.boost_health);

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

    t = new Thread() {

      @Override
      public void run() {
        try {
          while (!isInterrupted()) {
            Thread.sleep(100);
            getActivity().runOnUiThread(new Runnable() {
              @Override
              public void run() {
                // Here we update the value of
                powerCurrentValue = powerCurrentTime - System.currentTimeMillis();
                powerCurrentValue = powerCurrentValue < 0 ? 0 : powerCurrentValue;
                powerCurrentValue = powerCurrentValue > 100000 ? 100000 : powerCurrentValue;
                powerPoints.setText(String.format("%.2f power points", (float) powerCurrentValue / 1000));
                manaCurrentValue = manaCurrentTime - System.currentTimeMillis();
                manaCurrentValue = manaCurrentValue < 0 ? 0 : manaCurrentValue;
                manaCurrentValue = manaCurrentValue > 100000 ? 100000 : manaCurrentValue;
                manaPoints.setText(String.format("%.2f mana points", (float) manaCurrentValue / 1000));
                healthCurrentValue = healthCurrentTime - System.currentTimeMillis();
                healthCurrentValue = healthCurrentValue < 0 ? 0 : healthCurrentValue;
                healthCurrentValue = healthCurrentValue > 100000 ? 100000 : healthCurrentValue;
                healthPoints.setText(String.format("%.2f health points", (float) healthCurrentValue / 1000));
                courageCurrentValue = courageCurrentTime - System.currentTimeMillis();
                courageCurrentValue = courageCurrentValue < 0 ? 0 : courageCurrentValue;
                courageCurrentValue = courageCurrentValue > 100000 ? 100000 : courageCurrentValue;
                couragePoints.setText(String.format("%.2f courage points", (float) courageCurrentValue / 1000));
              }
            });
          }
        } catch (InterruptedException e) {
        }
      }
    };

    powerPoints.setText(String.format("%.2f power points", (powerCurrentValue) / 1000));
//    powerPoints.setText("TEST");
    manaPoints.setText(String.format("%.2f mana points", (manaCurrentValue) / 1000));
    couragePoints.setText(String.format("%.2f courage points", (courageCurrentValue)/ 1000));
    healthPoints.setText(String.format("%.2f health points", (healthCurrentValue)/ 1000));

    power.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        powerCurrentTime = System.currentTimeMillis() + 100200;
      }
    });

    mana.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        manaCurrentTime = System.currentTimeMillis() + 100200;
      }
    });

    courage.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        courageCurrentTime = System.currentTimeMillis() + 100200;
      }
    });

    health.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        healthCurrentTime = System.currentTimeMillis() + 100200;
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

  /**
   * This interface must be implemented by activities that contain this fragment to allow an
   * interaction in this fragment to be communicated to the activity and potentially other fragments
   * contained in that activity.
   * <p>
   * See the Android Training lesson <a href= "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */

}
