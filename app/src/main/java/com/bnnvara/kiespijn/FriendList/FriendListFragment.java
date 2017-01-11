package com.bnnvara.kiespijn.FriendList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.R;
import com.bnnvara.kiespijn.User;
import com.bumptech.glide.Glide;

import java.util.List;

public class FriendListFragment extends Fragment {

    private static final String TAG = "TargetGroupFragment";
    static final String DILEMMA_OBJECT = "dilemma_object";

    private static Dilemma mDilemma;
    private List<Friend> mFriendList;

    public static FriendListFragment newInstance(Dilemma dilemma) {
        mDilemma = dilemma;
        return new FriendListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_friend_list, container, false);

        User user = User.getInstance();
        mFriendList = user.mFacebookFriendList;

        ListView friendList = (ListView) view.findViewById(R.id.listview_friend_list);

        ListAdapter adapter = new ListAdapter();
        friendList.setAdapter(adapter);

        return view;
    }


    private class ListAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return mFriendList.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = LayoutInflater.from(getContext()).inflate(R.layout.listview_row_friend_list, null);

            ImageView profilePicture = (ImageView) view.findViewById(R.id.imageview_profile_picture);
            TextView friendName = (TextView) view.findViewById(R.id.textview_friend_name);
            final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox_friend);

            Glide.with(getActivity())
                    .load(mFriendList.get(i).getProfilePicURL())
                    .centerCrop()
                    .placeholder(R.drawable.ic_action_sand_timer)
                    .into(profilePicture);

            friendName.setText(mFriendList.get(i).getName());

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkBox.isChecked()) {

                    } else {

                    }
                }
            });

            return view;
        }
    }
}
