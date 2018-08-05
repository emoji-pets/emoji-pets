package edu.cnm.deepdive.emojipets;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.emojipets.pojo.Follower;
import edu.cnm.deepdive.emojipets.pojo.Player;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class FollowersFragment extends Fragment {

  ListView followersListView;
  List<Player> followers = new ArrayList<>();
  FollowAdapter followersAdapter;
  EmojiPetService service;


  public FollowersFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.followers, container, false);
    getActivity().setTitle("Followers");
    // Get followers
    followers = EmojiPetApplication.getInstance().getPlayer().getFollowers();

    // create adapters
    followersAdapter = new FollowAdapter(followers);
    // Set List Views for followers and followers
    followersListView = (ListView) view.findViewById(R.id.followers_list_view);
    // Set adapters
    followersListView.setAdapter(followersAdapter);

    // Notify both adapters data set changed
    updateLists();

    return view;

  }

  private void updateLists() {
    followersAdapter.notifyDataSetChanged();
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
      convertView = getLayoutInflater().inflate(R.layout.friends_list, null);
      TextView emojiCharacter = convertView.findViewById(R.id.emoji_character);
      TextView playerName = convertView.findViewById(R.id.player_name);
      TextView emojiName = convertView.findViewById(R.id.emoji_name);
      TextView emojiStatus = convertView.findViewById(R.id.emoji_status);

      emojiCharacter.setText(followList.get(position).getPet_emoji());
      playerName.setText(followList.get(position).getDisplay_name());
      emojiName.setText(followList.get(position).getPet_name());
      emojiStatus.setText(followList.get(position).getStatus());

      return convertView;
    }
  }

}
