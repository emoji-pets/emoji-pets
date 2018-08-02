package edu.cnm.deepdive.emojipets;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  ActionBar actionBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    actionBar = getSupportActionBar();
    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffe864")));

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    PetFragment petFragment = new PetFragment();
    FragmentManager manager = getSupportFragmentManager();
    manager.beginTransaction()
        .replace(R.id.main_activity, petFragment)
        .commit();

    ObjectAnimator animation = ObjectAnimator.ofFloat(findViewById(R.id.emoji_pets_title), "translationX", 100f);
    animation.setDuration(1000);
    animation.start();

  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_logout:
        signOut();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void signOut() {
    EmojiPetApplication application = EmojiPetApplication.getInstance().getInstance();
    application.getSignInClient().signOut().addOnCompleteListener(this, (task) -> {
      application.setSignInAccount(null);
      Intent intent = new Intent(MainActivity.this, SignInActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
    });
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.pet_fragment) {
      PetFragment petFragment = new PetFragment();
      FragmentManager manager = getSupportFragmentManager();
      manager.beginTransaction()
          .replace(R.id.main_activity, petFragment)
          .commit();
    } else if (id == R.id.following_fragment) {
      FollowingFragment followingFragment = new FollowingFragment();
      FragmentManager manager = getSupportFragmentManager();
      manager.beginTransaction()
          .replace(R.id.main_activity, followingFragment)
          .commit();
    } else if (id == R.id.followers_fragment) {
      FollowersFragment followersFragment = new FollowersFragment();
      FragmentManager manager = getSupportFragmentManager();
      manager.beginTransaction()
          .replace(R.id.main_activity, followersFragment)
          .commit();
    } else if (id == R.id.settings_fragment) {
      SettingsFragment settingsFragment = new SettingsFragment();
      FragmentManager manager = getSupportFragmentManager();
      manager.beginTransaction()
          .replace(R.id.main_activity, settingsFragment)
          .commit();
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
}
