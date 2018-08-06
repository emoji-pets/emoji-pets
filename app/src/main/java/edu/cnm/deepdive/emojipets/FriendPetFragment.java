package edu.cnm.deepdive.emojipets;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.emojipets.pojo.Player;
import java.io.IOException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendPetFragment extends Fragment {

  private TextView power;
  private TextView mana;
  private TextView courage;
  private TextView health;
  private TextView pet;
  private TextView petStatus;

  private float powerPoints;
  private float manaPoints;
  private float couragePoints;
  private float healthPoints;
  
  private String id;
  private Player friend;
  
  private EmojiPetService service;
  private Thread t;


  public FriendPetFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_friend_pet, container, false);
    id = getArguments().getString("id");

    setupServices();
    
    new UpdatePlayer().execute();

    power = (TextView) view.findViewById(R.id.power);
    mana = (TextView) view.findViewById(R.id.mana);
    courage = (TextView) view.findViewById(R.id.courage);
    health = (TextView) view.findViewById(R.id.health);
    pet = (TextView) view.findViewById(R.id.pet);
    petStatus = (TextView) view.findViewById(R.id.pet_status);

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
                power.setText(String.format("%.2f power points", powerPoints));
                mana.setText(String.format("%.2f mana points", manaPoints));
                health.setText(String.format("%.2f health points", healthPoints));
                courage.setText(String.format("%.2f courage points", couragePoints));
              }
            });
          }
        } catch (InterruptedException e) {
        }
      }
    };
    
    return view;
  }

  private void setPoint(String point) {
    switch (point) {
      case "power":
        powerPoints = (float) friend.getPowerPointsMax() + (float) (
            friend.getPowerPoints() - System.currentTimeMillis()
        )/10000;
        powerPoints = powerPoints < 0 ? 0f : powerPoints;
        break;
      case "mana":
        manaPoints = (float) friend.getManaPointsMax() + (float) (
            friend.getManaPoints() - System.currentTimeMillis()
        )/10000;
        manaPoints = manaPoints < 0 ? 0f : manaPoints;
        break;
      case "courage":
        couragePoints = (float) friend.getCouragePointsMax() + (float) (
            friend.getCouragePoints() - System.currentTimeMillis()
        )/10000;
        couragePoints = couragePoints < 0 ? 0f : couragePoints;
        break;
      case "health":
        healthPoints = (float) friend.getHealthPointsMax() + (float) (
            friend.getHealthPoints() - System.currentTimeMillis()
        )/10000;
        healthPoints = healthPoints < 0 ? 0f : healthPoints;
    }
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

  private class UpdatePlayer extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
      try {
        String token = EmojiPetApplication.getInstance().getSignInAccount().getIdToken();
        Response<Player> response = service.get(
            getString(R.string.oauth2_header_format, token),
            id).execute();
        if (response.isSuccessful()) {
          friend = response.body();
        }
      } catch (IOException e) {

      } finally {
//        if (player == null) {
//          cancel(true);
//        }
      }
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      pet.setText(friend.getPet_emoji());
      setPoint("courage");
      setPoint("health");
      setPoint("mana");
      setPoint("power");
      courage.setText(String.format("%.2f courage points", (float) couragePoints));
      mana.setText(String.format("%.2f courage points", (float) manaPoints));
      power.setText(String.format("%.2f courage points", (float) powerPoints));
      health.setText(String.format("%.2f courage points", (float) healthPoints));
      t.start();
    }
  }


  
}
