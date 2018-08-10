package edu.cnm.deepdive.emojipets;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.cnm.deepdive.emojipets.pojo.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This is the FollowersFragment subclass.
 * This is the page where the user can see his or her followers
 * and click on their followers name to go to their follower's page.
 *
 */
public class FollowersFragment extends Fragment {

  ListView followersListView;
  List<Player> followers = new ArrayList<>();
  private Map<String,String> mapOfNamesToIds = new HashMap<>();
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
    getActivity().setTitle(getString(R.string.followers));
    // Get followers
    followers = EmojiPetApplication.getInstance().getPlayer().getFollowers();

    // Add names to ids in map
    for (Player follower : followers) {
      if (!mapOfNamesToIds.containsKey(follower.getDisplay_name())) {
        mapOfNamesToIds.put(follower.getDisplay_name(), follower.getOauthId());
      }
    }
    // create adapters
    followersAdapter = new FollowAdapter(followers);
    // Set List Views for followers and followers
    followersListView = (ListView) view.findViewById(R.id.followers_list_view);
    // Set adapters
    followersListView.setAdapter(followersAdapter);

    followersListView.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView displayName = view.findViewById(R.id.player_name_for_dropdown);
        String friendId = mapOfNamesToIds.get(displayName.getText().toString());
        Intent intent = new Intent(getActivity(), FriendActivity.class);
        intent.putExtra("id", friendId);
        intent.putExtra("display name", displayName.getText().toString());
        startActivity(intent);
      }
    });

    // Notify both adapters data set changed
    updateLists();

    return view;

  }

  private void updateLists() {
    followersAdapter.notifyDataSetChanged();
  }

  private class FollowAdapter extends BaseAdapter {

    List<Player> followList;

    /**
     *
     * @param followList This is an adapter to generate the list of who the user is following.
     */
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
        convertView = getLayoutInflater().inflate(R.layout.friends_dropdown_item, null);
      }
      TextView friendsName = convertView.findViewById(R.id.player_name_for_dropdown);
      TextView friendsPetName = convertView.findViewById(R.id.drop_down_pet_name);
      TextView friendsEmoji = convertView.findViewById(R.id.emoji_character_for_dropdown);
      TextView friendsStatus = convertView.findViewById(R.id.drop_down_pet_status);

      friendsName.setText(followList.get(position).getDisplay_name());
      friendsEmoji.setText(followList.get(position).getPet_emoji());
      friendsStatus.setText(String.format("%s%s", getString(R.string.pet_name), followList.get(position).getPet_name()));
      friendsStatus.setText(String.format("%s%s", getString(R.string.pet_status), followList.get(position).getStatus()));

      return convertView;
    }
  }
}
