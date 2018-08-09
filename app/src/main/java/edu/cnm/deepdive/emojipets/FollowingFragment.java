package edu.cnm.deepdive.emojipets;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.emojipets.pojo.Player;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * This fragment is where users can see who they are following.
 */
public class FollowingFragment extends Fragment {

  private ListView followingListView;
  private List<Player> following;
  private FollowAdapter followingAdapter;
  private EmojiPetService service;
  private List<Player> listOfPlayers = new ArrayList<>();
  private Map<String,String> mapOfNamesToIds = new HashMap<>();
  private List<String> names = new ArrayList<>();
  private AutoCompleteAdapter friendSearchAdapter;

  public FollowingFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // setup rest service
    setupService();
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.following, container, false);
    getActivity().setTitle("Following");
    // Query all following
    following = EmojiPetApplication.getInstance().getPlayer().getFollowing();
    // create adapters
    followingAdapter = new FollowAdapter(following);
    // Set List Views for followers and following
    followingListView = (ListView) view.findViewById(R.id.following_list_view);
    // Set adapters
    followingListView.setAdapter(followingAdapter);

    followingListView.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView displayName = view.findViewById(R.id.friends_list_player_name);
        String friendId = mapOfNamesToIds.get(displayName.getText().toString());
        Intent intent = new Intent(getActivity(), FriendActivity.class);
        intent.putExtra("id", friendId);
        intent.putExtra("display name", displayName.getText().toString());
        startActivity(intent);
      }
    });

    // Notify both adapters data set changed
    updateLists();

    // query to get all players for autocomplete
    new GetAllPlayersNames().execute();

    // setup auto complete for finding other pets
    friendSearchAdapter = new AutoCompleteAdapter(getContext(), R.layout.friends_dropdown_item, R.id.search_friend_text);
    final AutoCompleteTextView searchNamesAutoComplete = view.findViewById(R.id.search_friends_results);
    searchNamesAutoComplete.setAdapter(friendSearchAdapter);
    searchNamesAutoComplete.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String otherPlayerName = ((TextView) view.findViewById(R.id.player_name_for_dropdown)).getText().toString();
        new AddFollower().execute(otherPlayerName);
        searchNamesAutoComplete.setText("");
      }
    });

    return view;

  }

  private void updateLists() {
    followingAdapter.notifyDataSetChanged();
  }

  private class FollowAdapter extends BaseAdapter {

    List<Player> followList;

    public FollowAdapter(List<Player> followList) {
      this.followList = followList;
    }

    @Override
    public int getCount() {
      return followList.size();
    }

    @Override
    public Object getItem(int position) {
      return null;
    }

    @Override
    public long getItemId(int position) {
      return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      if (convertView == null) {
        convertView = getLayoutInflater().inflate(R.layout.friends_list_item, null);
      }
      TextView friendsName = convertView.findViewById(R.id.friends_list_player_name);
      TextView friendsPetName = convertView.findViewById(R.id.friends_list_emoji_name);
      TextView friendsEmoji = convertView.findViewById(R.id.friends_list_emoji_character);
      TextView friendsStatus = convertView.findViewById(R.id.friends_list_status);

      friendsName.setText(followList.get(position).getDisplay_name());
      friendsEmoji.setText(followList.get(position).getPet_emoji());
      friendsPetName.setText("Pet name: " + followList.get(position).getPet_name());
      friendsStatus.setText("Pet status: " + followList.get(position).getStatus());

      return convertView;
    }
  }


  private void setupService() {
    Gson gson = new GsonBuilder()
        .create();
    service = new Retrofit.Builder()
        .baseUrl(getString(R.string.base_url_aws))
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(EmojiPetService.class);
  }



  // This needs to be optimized when we scale.
  private class GetAllPlayersNames extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
      List<Player> players = new ArrayList<>();
      Map<String,String> nameToId = new HashMap<>();
      List<String> namesPulled = new ArrayList<>();
      try {
        String token = EmojiPetApplication.getInstance().getSignInAccount().getIdToken();
        String personId = EmojiPetApplication.getInstance().getSignInAccount().getId();
        Response<List<Player>> response = service
            .getAllPlayers(getString(R.string.oauth2_header_format, token)).execute();
        if (response.isSuccessful()) {
          players = response.body();
          for (Player player : players) {
            if (!nameToId.containsKey(player.getDisplay_name())) {
              nameToId.put(player.getDisplay_name(), player.getOauthId());
              namesPulled.add(player.getDisplay_name());
            }
          }
          listOfPlayers.clear();
          listOfPlayers.addAll(players);
          mapOfNamesToIds.clear();
          mapOfNamesToIds.putAll(nameToId);
          names.clear();
          names.addAll(namesPulled);
          friendSearchAdapter.clear();
          friendSearchAdapter.addAll(listOfPlayers);
        }
      } catch (IOException e) {

      } finally {
        if (players.size() == 0) {
          cancel(true);
        }
      }
      return null;
    }
  }

  private class AutoCompleteAdapter extends ArrayAdapter<Player> implements Filterable {

    Context context;
    int resource, textViewResourceId;
    List<Player> players;
    List<String> tempPlayers;
    List<Player> suggestions;

    public AutoCompleteAdapter(Context context, int resource, int textViewResourceId) {
      super(context, resource, textViewResourceId);
      this.context = context;
      this.resource = resource;
      this.textViewResourceId = textViewResourceId;
      this.players = new ArrayList<>();
      this.tempPlayers = new ArrayList<>(); // this makes the difference.
      this.suggestions = new ArrayList<>();
    }

    @Override
    public void addAll(@NonNull Collection<? extends Player> collection) {
      this.players.addAll(collection);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      View view = convertView;
      TextView friendsName;
      TextView friendsPetName;
      TextView friendsEmoji;
      TextView friendsStatus;
      if (convertView == null) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.friends_dropdown_item, parent, false);
      }
      if (suggestions.size() > 0 && position < suggestions.size()) {
        Player player = suggestions.get(position);
        if (player != null) {
          friendsName = view.findViewById(R.id.player_name_for_dropdown);
          friendsName.setText(player.getDisplay_name());
          friendsEmoji = view.findViewById(R.id.emoji_character_for_dropdown);
          friendsEmoji.setText(player.getPet_emoji());
          friendsPetName = view.findViewById(R.id.drop_down_pet_name);
          friendsPetName.setText("Pet name: " + player.getPet_name());
          friendsStatus = view.findViewById(R.id.drop_down_pet_status);
          friendsStatus.setText("Pet status: " + player.getStatus());
//          ((TextView) view).setText(player.getDisplay_name());
        }
      }
      return view;
    }

    @Override
    public Filter getFilter() {
      return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {

      @Override
      protected FilterResults performFiltering(CharSequence constraint) {
        if (constraint != null) {
          suggestions.clear();
          for (Player player : players) {
            if (player.getDisplay_name().toLowerCase().contains(constraint.toString().toLowerCase())) {
              suggestions.add(player);
            }
          }
          FilterResults filterResults = new FilterResults();
          filterResults.values = suggestions;
          filterResults.count = suggestions.size();
          return filterResults;
        } else {
          return new FilterResults();
        }
      }

      @Override
      protected void publishResults(CharSequence constraint, FilterResults results) {
        List<Player> filterList = (ArrayList<Player>) results.values;
        if (results != null && results.count > 0) {
          clear();
          AutoCompleteAdapter.super.addAll(filterList);
        }
      }
    };
  }

  // This needs to be optimized when we scale.
  private class AddFollower extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... strings) {
      String otherPlayerOauth = mapOfNamesToIds.get(strings[0]);
      try {
        String token = EmojiPetApplication.getInstance().getSignInAccount().getIdToken();
        String playerOauth = EmojiPetApplication.getInstance().getSignInAccount().getId();
        Response response = service.postFollow(getString(R.string.oauth2_header_format, token),
            playerOauth,
            otherPlayerOauth,
            EmojiPetApplication.getInstance().getPlayer()
        ).execute();
        if (response.isSuccessful()) {
          EmojiPetApplication.getInstance().setPlayer((Player) response.body());
          following.clear();
          following.addAll(EmojiPetApplication.getInstance().getPlayer().getFollowing());
        }
      } catch (IOException e) {

      }
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      updateLists();
    }
  }
}
