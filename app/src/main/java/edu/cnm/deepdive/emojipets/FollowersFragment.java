package edu.cnm.deepdive.emojipets;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.cnm.deepdive.emojipets.pojo.Follower;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FollowersFragment extends Fragment {

  ListView followersListView;
  List<Follower> followers;
  FollowAdapter followersAdapter;


  public FollowersFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.followers, container, false);
    getActivity().setTitle("Followers");
    // Query all folling
    List<Follower> followers = new ArrayList<>();
    followers.add(new Follower("Blake", "lil boss", "\uD83D\uDE4A",
        "Just chillin at the lake RN crystal AF"));
    followers.add(new Follower("Karol", "Baby", "\uD83D\uDECD", "I found my phone!"));
    followers.add(new Follower("Husain", "Elvis", "\uD83C\uDF54",
        "Hanging with friends, see you all next week"));

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
}
