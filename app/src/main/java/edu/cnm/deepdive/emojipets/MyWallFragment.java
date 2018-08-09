package edu.cnm.deepdive.emojipets;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * This fragment is the wall of the user. This is where the user can interact with
 * their friends.
 */
public class MyWallFragment extends Fragment {

  TextView wall;

  public MyWallFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_my_wall, container, false);
    getActivity().setTitle(R.string.my_wall);

    wall = view.findViewById(R.id.my_wall);
    wall.setText(EmojiPetApplication.getInstance().getPlayer().getWall());

    return view;
  }

}
