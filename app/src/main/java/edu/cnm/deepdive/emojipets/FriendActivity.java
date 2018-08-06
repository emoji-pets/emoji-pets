package edu.cnm.deepdive.emojipets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class FriendActivity extends AppCompatActivity {

  private TextView mTextMessage;
  private String friendId;

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
      = new BottomNavigationView.OnNavigationItemSelectedListener() {


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()) {
        case R.id.navigation_home:
          FriendPetFragment friendPetFragment = new FriendPetFragment();
          FragmentManager manager = getSupportFragmentManager();
          manager.beginTransaction()
              .replace(R.id.friend_activity, friendPetFragment)
              .commit();
          return true;
        case R.id.navigation_dashboard:
          // mTextMessage.setText(R.string.title_dashboard);
          return true;
        case R.id.navigation_notifications:
          // mTextMessage.setText(R.string.title_notifications);
          return true;
      }
      return false;
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_friend);

    Intent intent = getIntent();
    friendId = intent.getStringExtra("id");

    Bundle bundle = new Bundle();
    bundle.putString("id", friendId);

    FriendPetFragment friendPetFragment = new FriendPetFragment();
    friendPetFragment.setArguments(bundle);
    FragmentManager manager = getSupportFragmentManager();
    manager.beginTransaction()
        .replace(R.id.friend_activity, friendPetFragment)
        .commit();

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });


    mTextMessage = (TextView) findViewById(R.id.message);
    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_nav);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
  }
}
