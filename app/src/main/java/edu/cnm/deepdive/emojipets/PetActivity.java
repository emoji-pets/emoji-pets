package edu.cnm.deepdive.emojipets;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class PetActivity extends AppCompatActivity {

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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pet);

    power = findViewById(R.id.boost_power);
    mana = findViewById(R.id.boost_mana);
    courage = findViewById(R.id.boost_courage);
    health = findViewById(R.id.boost_health);

    powerPoints = (TextView) findViewById(R.id.power);
    manaPoints = (TextView) findViewById(R.id.mana);
    couragePoints = (TextView) findViewById(R.id.courage);
    healthPoints = (TextView) findViewById(R.id.health);

    powerCurrentTime = System.currentTimeMillis() + 100200;
    manaCurrentTime = System.currentTimeMillis() + 100200;
    courageCurrentTime = System.currentTimeMillis() + 100200;
    healthCurrentTime = System.currentTimeMillis() + 100200;

    powerCurrentValue = (float) powerCurrentTime;
    manaCurrentValue = (float) manaCurrentTime;
    courageCurrentValue = (float) courageCurrentTime;
    healthCurrentValue = (float) healthCurrentTime;

    Thread t = new Thread() {

      @Override
      public void run() {
        try {
          while (!isInterrupted()) {
            Thread.sleep(100);
            runOnUiThread(new Runnable() {
              @Override
              public void run() {
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
  }

//  private class myTimerTask extends TimerTask{
//    @Override
//    public void run() {
//      // TODO Auto-generated method stub
//      powerCurrentValue--;
//      manaCurrentValue--;
//      courageCurrentValue--;
//      healthCurrentValue--;
//      updateLabel.sendEmptyMessage(0);
//    }
//  }
//
//  private Handler updateLabel = new Handler(){
//    @Override
//    public void handleMessage(Message msg) {
//      // TODO Auto-generated method stub
//      //super.handleMessage(msg);
//
//      powerPoints.setText(String.format("%.2f power points", (float) powerCurrentValue / 10000));
//      manaPoints.setText(String.format("%.2f mana points", (float) manaCurrentValue / 10000));
//      healthPoints.setText(String.format("%.2f health points", (float) powerCurrentValue / 10000));
//      couragePoints.setText(String.format("%.2f courage points", (float) powerCurrentValue / 10000));
//    }
//  };
}
