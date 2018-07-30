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

  long powerCurrentValue;
  long manaCurrentValue;
  long courageCurrentValue;
  long healthCurrentValue;

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

    powerCurrentTime = System.currentTimeMillis();
    manaCurrentTime = System.currentTimeMillis();
    courageCurrentTime = System.currentTimeMillis();
    healthCurrentTime = System.currentTimeMillis();

    powerCurrentValue = 1000000;
    manaCurrentValue = 1000000;
    courageCurrentValue = 1000000;
    healthCurrentValue = 1000000;

    powerPoints.setText(String.format("%.2f power points", (float) powerCurrentValue / 10000));
    manaPoints.setText(String.format("%.2f mana points", (float) powerCurrentValue / 10000));
    couragePoints.setText(String.format("%.2f courage points", (float) powerCurrentValue / 10000));
    healthPoints.setText(String.format("%.2f health points", (float) powerCurrentValue / 10000));



    Thread t = new Thread() {

      @Override
      public void run() {
        try {
          while (!isInterrupted()) {
            Thread.sleep(100);
            runOnUiThread(new Runnable() {
              @Override
              public void run() {
                powerCurrentValue = powerCurrentValue + (powerCurrentTime - System.currentTimeMillis());
                powerCurrentValue = powerCurrentValue < 0 ? 0 : powerCurrentValue;
                powerCurrentValue = powerCurrentValue > 1000000 ? 1000000 : powerCurrentValue;
                powerPoints.setText(String.format("%.2f power points", (float) powerCurrentValue / 10000));
                manaCurrentValue = manaCurrentValue + (manaCurrentTime - System.currentTimeMillis());
                manaCurrentValue = manaCurrentValue < 0 ? 0 : manaCurrentValue;
                manaCurrentValue = manaCurrentValue > 1000000 ? 1000000 : manaCurrentValue;
                manaPoints.setText(String.format("%.2f mana points", (float) manaCurrentValue / 10000));
                healthCurrentValue = healthCurrentValue + (healthCurrentTime - System.currentTimeMillis());
                healthCurrentValue = healthCurrentValue < 0 ? 0 : healthCurrentValue;
                healthCurrentValue = healthCurrentValue > 1000000 ? 1000000 : healthCurrentValue;
                healthPoints.setText(String.format("%.2f health points", (float) healthCurrentValue / 10000));
                courageCurrentValue = courageCurrentValue + (courageCurrentTime - System.currentTimeMillis());
                courageCurrentValue = courageCurrentValue < 0 ? 0 : courageCurrentValue;
                courageCurrentValue = courageCurrentValue > 1000000 ? 1000000 : courageCurrentValue;
                couragePoints.setText(String.format("%.2f courage points", (float) courageCurrentValue / 10000));
              }
            });
          }
        } catch (InterruptedException e) {
        }
      }
    };

    power.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        powerCurrentTime = powerCurrentTime > System.currentTimeMillis() ? powerCurrentTime :
            System.currentTimeMillis() + 2000;
      }
    });

    mana.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        manaCurrentTime = manaCurrentTime > System.currentTimeMillis() ? manaCurrentTime :
            System.currentTimeMillis() + 2000;
      }
    });

    courage.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        courageCurrentTime = courageCurrentTime > System.currentTimeMillis() ? courageCurrentTime :
            System.currentTimeMillis() + 2000;
      }
    });

    health.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        healthCurrentTime = healthCurrentTime > System.currentTimeMillis() ? healthCurrentTime :
            System.currentTimeMillis() + 2000;
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
