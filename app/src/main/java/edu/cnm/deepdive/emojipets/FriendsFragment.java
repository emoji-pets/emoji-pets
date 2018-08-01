package edu.cnm.deepdive.emojipets;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.cnm.deepdive.emojipets.pojo.Follower;
import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {

  ListView followersListView;
  ListView followingListView;
  List<Follower> followers;
  List<Follower> following;
  FollowAdapter followersAdapter;
  FollowAdapter followingAdapter;

  public FriendsFragment() {
    // Required empty public constructor
  }

  public static FriendsFragment newInstance() {
    FriendsFragment fragment = new FriendsFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_friends, container, false);
    getActivity().setTitle("My Friends");

    // Query all folling
    List<Follower> following = new ArrayList<>();
    following.add(new Follower("Blake", "lil boss", "\uD83D\uDE4A", "Just chillin at the lake RN crystal AF"));
    following.add(new Follower("Karol", "Baby", "\uD83D\uDECD", "I found my phone!"));
    following.add(new Follower("Husain", "Elvis", "\uD83C\uDF54", "Hanging with friends, see you all next week"));
    // Query all followers
    List<Follower> followers = new ArrayList<>();
    followers.add(new Follower("Moe", "lil boss", "\uD83D\uDE4A", "Just chillin at the lake RN crystal AF"));
    followers.add(new Follower("Jenna", "Baby", "\uD83D\uDE0D", "I found my phone!"));
    followers.add(new Follower("Barry", "Elvis", "\uD83C\uDF54", "Hanging with friends, see you all next week"));

    // create adapters
    followingAdapter = new FollowAdapter(following);
    followersAdapter = new FollowAdapter(followers);
    // Set List Views for followers and following
    followingListView = (ListView) view.findViewById(R.id.following_list_view);
    followersListView = (ListView) view.findViewById(R.id.followers_list_view);
    // Set adapters
    followingListView.setAdapter(followingAdapter);
    followersListView.setAdapter(followersAdapter);

    // Notify both adapters data set changed
    updateLists();

    return view;
  }

  private void updateLists() {
    followingAdapter.notifyDataSetChanged();
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
