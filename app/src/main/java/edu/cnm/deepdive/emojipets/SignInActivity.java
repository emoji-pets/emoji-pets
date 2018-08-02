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
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class SignInActivity extends AppCompatActivity {

  private static final int REQUEST_CODE = 1000;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_in);

    Button signIn = findViewById(R.id.imageButton1);
    signIn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = EmojiPetApplication.getInstance().getSignInClient().getSignInIntent();
        startActivityForResult(intent, REQUEST_CODE);
      }
    });

    ConstraintLayout constraintLayout = findViewById(R.id.layout);
    AnimationDrawable animationDrawable = (AnimationDrawable)constraintLayout.getBackground();
    animationDrawable.setEnterFadeDuration(2000);
    animationDrawable.setExitFadeDuration(4000);
    animationDrawable.start();

//    // Rotation animation
//    final ImageButton imageButton3 = findViewById(R.id.imageButton3);
//    imageButton3.setOnClickListener(new OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        imageButton3.animate().rotation(imageButton3.getRotation()-360).start();
//      }
//    });

//    // Fade animation
//    final ImageButton imageButton2 = findViewById(R.id.imageButton2);
//    imageButton2.setOnClickListener(new OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        Animation animation = new AlphaAnimation(1.0f, 0.0f);
//        animation.setDuration(1000);
//        imageButton2.startAnimation(animation);
//      }
//    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_CODE) {
      try {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        GoogleSignInAccount account = task.getResult(ApiException.class);
        EmojiPetApplication.getInstance().setSignInAccount(account);
        switchToMain();
      } catch (ApiException e) {
        Toast.makeText(this, "Unable to sign in.  Please check you credentials and connection.",
            Toast.LENGTH_LONG).show();
      }
    }
  }

  private void switchToMain() {
    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

  //  // Tried having sliding transition animation when switching ti another activity but didn't work (no effect) LR
//  public void openNewGameActivity(View view) {
//    Intent intent = new Intent(this, NewGameActivity.class);
//    startActivity(intent);
//    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//  }
}

