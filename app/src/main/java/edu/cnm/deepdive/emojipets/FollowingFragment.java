package edu.cnm.deepdive.emojipets;


import android.content.Context;
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
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.emojipets.pojo.Follower;
import edu.cnm.deepdive.emojipets.pojo.Player;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Text;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingFragment extends Fragment {

  private ListView followingListView;
  private List<Follower> following;
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
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.following, container, false);
    getActivity().setTitle("Following");
    // setup auto complete for finding other pets
    friendSearchAdapter = new AutoCompleteAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, R.id.search_friend_text);
    final AutoCompleteTextView searchNamesAutoComplete = view.findViewById(R.id.search_friends_results);
    searchNamesAutoComplete.setAdapter(friendSearchAdapter);
    searchNamesAutoComplete.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        String playerName = ((TextView) view.findViewById(R.id.player_name_for_dropdown)).getText().toString();

        Toast.makeText(getContext(), "playerName", Toast.LENGTH_LONG).show();
      }
    });
    // Query all folling
    List<Follower> following = new ArrayList<>();
    following.add(new Follower("Blake", "lil boss", "\uD83D\uDE4A", "Just chillin at the lake RN crystal AF"));
    following.add(new Follower("Karol", "Baby", "\uD83D\uDECD", "I found my phone!"));
    following.add(new Follower("Husain", "Elvis", "\uD83C\uDF54", "Hanging with friends, see you all next week"));

    // create adapters
    followingAdapter = new FollowAdapter(following);
    // Set List Views for followers and following
    followingListView = (ListView) view.findViewById(R.id.following_list_view);
    // Set adapters
    followingListView.setAdapter(followingAdapter);

    // Notify both adapters data set changed
    updateLists();
    setupService();

    new GetAllPlayersNames().execute();

    return view;

  }

  private void updateLists() {
    followingAdapter.notifyDataSetChanged();
  }

  private class FollowAdapter extends BaseAdapter {

    List<Follower> followList;

    public FollowAdapter(List<Follower> followList) {
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
      convertView = getLayoutInflater().inflate(R.layout.friends_list, null);
      TextView emojiCharacter = convertView.findViewById(R.id.emoji_character);
      TextView playerName = convertView.findViewById(R.id.player_name);
      TextView emojiName = convertView.findViewById(R.id.emoji_name);
      TextView emojiStatus = convertView.findViewById(R.id.emoji_status);

      emojiCharacter.setText(followList.get(position).getPet_emoji());
      playerName.setText(followList.get(position).getName());
      emojiName.setText(followList.get(position).getPet_name());
      emojiStatus.setText(followList.get(position).getStatus());

      return convertView;
    }
  }


  private void setupService() {
    Gson gson = new GsonBuilder()
        .create();
    service = new Retrofit.Builder()
        .baseUrl(getString(R.string.base_url))
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
//      TextView friendsName;
//      TextView friendsPetName;
//      TextView friendsEmoji;
      if (convertView == null) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
      }
      if (suggestions.size() > 0 && position < suggestions.size()) {
        Player player = suggestions.get(position);
        if (player != null) {
//          friendsName = view.findViewById(R.id.player_name_for_dropdown);
//          friendsName.setText(player.getDisplay_name());
//          friendsEmoji = view.findViewById(R.id.emoji_character_for_dropdown);
//          friendsEmoji.setText(player.getPet_emoji());
//          friendsPetName = view.findViewById(R.id.emoji_name_for_dropdown);
//          friendsPetName.setText(player.getPet_name());
          ((TextView) view).setText(player.getDisplay_name());
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

}
