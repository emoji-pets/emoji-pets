package edu.cnm.deepdive.emojipets;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;

public class GameMenuActivity extends AppCompatActivity {

  private Button newGame;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game_menu);

    newGame = findViewById(R.id.imageButton);
    newGame.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i = new Intent(GameMenuActivity.this, NewGameActivity.class);
        startActivity(i);
      }
    });

    ConstraintLayout constraintLayout = findViewById(R.id.layout);
    AnimationDrawable animationDrawable = (AnimationDrawable)constraintLayout.getBackground();
    animationDrawable.setEnterFadeDuration(2000);
    animationDrawable.setExitFadeDuration(4000);
    animationDrawable.start();

    // Rotation animation
    final ImageButton imageButton3 = findViewById(R.id.imageButton3);
    imageButton3.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        imageButton3.animate().rotation(imageButton3.getRotation()-360).start();
        Intent i = new Intent(GameMenuActivity.this, HelpActivity.class);
        startActivity(i);
      }
    });

    // Fade animation
    final ImageButton imageButton2 = findViewById(R.id.imageButton2);
    imageButton2.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Animation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(1000);
        imageButton2.startAnimation(animation);
      }
    });
  }
}

