package com.bnnvara.kiespijn.FriendList;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.DilemmaFromWho.DilemmaFromWhoActivity;
import com.bnnvara.kiespijn.GroupPage.Group;
import com.bnnvara.kiespijn.R;
import com.bnnvara.kiespijn.TargetGroup.TargetGroupActivity;
import com.bnnvara.kiespijn.User;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class FriendListFragment extends Fragment {

    private static final String TAG = "TargetGroupFragment";
    static final String DILEMMA_OBJECT = "dilemma_object";

    private static Dilemma mDilemma;
    private List<Friend> mFriendList;
    private List<Group> mGroupsList;
    private RecyclerView mPhotoRecylerView;
    private List<String> mTargetIDList = new ArrayList<>();
    private Boolean mCheckTargetList = false;

    private Boolean showGroups = false;

    private CheckBox mCheckBoxAllFriends;
    private List<CheckBox> mCheckBoxList = new ArrayList<>();
    private SearchView mSearchView;

    public static FriendListFragment newInstance(Dilemma dilemma) {
        mDilemma = dilemma;
        return new FriendListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_friend_list, menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_friend_list, container, false);

        Button nextButton = (Button) view.findViewById(R.id.button_next_friend_list);
        Button prevButton = (Button) view.findViewById(R.id.button_previous_friend_list);
        final Button friendsButton = (Button) view.findViewById(R.id.friend_list_all);
        final Button groupButton = (Button) view.findViewById(R.id.friend_list_groups);
        mSearchView = (SearchView) view.findViewById(R.id.searchview_friends);
        mCheckBoxAllFriends = (CheckBox) view.findViewById(R.id.checkbox_all_friends);
        final TextView everyoneTextView = (TextView) view.findViewById(R.id.textview_friendlist_everyone);

        // FONT setup
        Typeface source_sans_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Bold.ttf");
        groupButton.setTypeface(source_sans_bold);
        friendsButton.setTypeface(source_sans_bold);

        if (mDilemma.getTargetIDList() != null) {
            mCheckTargetList = true;
            mTargetIDList = mDilemma.getTargetIDList();
        } else {
            checkOffAllFriends(true);
        }

        mPhotoRecylerView = (RecyclerView) view.findViewById(R.id.friend_list_recycler_view);
        mPhotoRecylerView.setLayoutManager(new LinearLayoutManager(getContext()));

        User user = User.getInstance();
        mFriendList = user.getFacebookFriendList();
        mGroupsList = user.getGroupsList();

        final FriendListAdapter friendAdapter = new FriendListAdapter(mFriendList);
        friendAdapter.setupAdapter();
        final GroupListAdapter groupAdapter = new GroupListAdapter(mGroupsList);

        mCheckBoxAllFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckBoxAllFriends.isChecked()) {
                    checkOffAllFriends(true);
                } else {
                    checkOffAllFriends(false);
                }
            }
        });

        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendsButton.setAlpha(1);
                groupButton.setAlpha(0.65f);
                friendAdapter.setupAdapter();
                mCheckBoxAllFriends.setVisibility(View.VISIBLE);
                everyoneTextView.setVisibility(View.VISIBLE);
                mCheckTargetList = true;
            }
        });

        groupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendsButton.setAlpha(0.65f);
                groupButton.setAlpha(1);
                groupAdapter.setupAdapter();
                mCheckBoxAllFriends.setVisibility(View.GONE);
                everyoneTextView.setVisibility(View.GONE);
                mCheckTargetList = true;

            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mTargetIDList.size() == 0) {
                    Toast.makeText(getActivity(), R.string.not_all_fields_filled, Toast.LENGTH_SHORT).show();
                } else {
                    mDilemma.setTargetIDList(mTargetIDList);
                    Intent i = DilemmaFromWhoActivity.newIntent(getActivity());
                    i.putExtra(DILEMMA_OBJECT, mDilemma);
                    startActivity(i);
                    getActivity().finish();
                }
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = TargetGroupActivity.newIntent(getActivity());
                i.putExtra(DILEMMA_OBJECT, mDilemma);
                startActivity(i);
                getActivity().finish();
            }
        });



        return view;
    }

    private void checkOffAllFriends(Boolean needToCheck) {
        int i = 0;

        mTargetIDList.clear();

        while (i < mCheckBoxList.size()) {

            if (needToCheck) {
                mCheckBoxList.get(i).setChecked(true);
                mTargetIDList.add(mFriendList.get(i).getFacebookID());
            } else {
                mCheckBoxList.get(i).setChecked(false);
            }

            i += 1;
        }
    }

    private Boolean checkTargetList(String ID) {

        if (mTargetIDList.contains(ID)) {
            return true;
        }
        return false;
    }

    public class FriendHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView profilePicture;
        TextView friendName;
        CheckBox checkBox;

        public FriendHolder(View itemView) {
            super(itemView);
            profilePicture = (ImageView) itemView.findViewById(R.id.imageview_profile_picture);
            friendName = (TextView) itemView.findViewById(R.id.textview_friend_name);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox_friend);

            itemView.setOnClickListener(this);
        }

        public void bindFriendItem(final Friend friend) {

            mCheckBoxList.add(checkBox);

            Glide.with(getActivity())
                    .load(friend.getProfilePicURL())
                    .centerCrop()
                    .placeholder(R.drawable.ic_action_sand_timer)
                    .into(profilePicture);

            friendName.setText(friend.getName());

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String friendID = friend.getFacebookID();
                    if (checkBox.isChecked()) {
                        mTargetIDList.add(friendID);
                    } else {
                        int i = mTargetIDList.indexOf(friendID);
                        mTargetIDList.remove(i);
                        mCheckBoxAllFriends.setChecked(false);
                    }
                }
            });

            if (mCheckTargetList) {
                if (checkTargetList(friend.getFacebookID())) {
                    checkBox.setChecked(true);
                }
            }
        }

        @Override
        public void onClick(View view) {

        }

    }

    private class FriendListAdapter extends RecyclerView.Adapter<FriendHolder> {

        private List<Friend> mFriendList;

        public FriendListAdapter(List<Friend> friendList) {
            mFriendList = friendList;
        }

        @Override
        public FriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_row_friend_list, parent, false);
            return new FriendHolder(view);
        }

        @Override
        public void onBindViewHolder(FriendHolder holder, int position) {

            if (mSearchView.getQuery() != null) {
                if (!searchForFriend(mSearchView.getQuery().toString())) {
                    return;
                }
            }

            Friend mFriend = mFriendList.get(position);
            holder.bindFriendItem(mFriend);

        }

        @Override
        public int getItemCount() {
            return mFriendList.size();
        }

        public void setupAdapter() {
            if (isAdded()) {
                mPhotoRecylerView.setAdapter(new FriendListAdapter(mFriendList));
            }
        }
    }

    private Boolean searchForFriend(String query) {

        int i = 0;

        while (i < mFriendList.size()) {
            if (mFriendList.get(i).getName().contains(query)) {
                return true;
            }
        }

        return false;
    }

    public class GroupHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView groupName;
        CheckBox checkBox;

        public GroupHolder(View itemView) {
            super(itemView);
            groupName = (TextView) itemView.findViewById(R.id.textview_group_name);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox_group);

            itemView.setOnClickListener(this);
        }

        public void bindGroupItem(final Group group) {

            groupName.setText(group.getGroupName());

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (checkBox.isChecked()) {
                        int i = 0;

                        while (i < group.getGroupMembers().size()) {
                            String facebookID = group.getGroupMembers().get(i).getFacebookID();
                            if (!checkTargetList(facebookID)) {
                                mTargetIDList.add(group.getGroupMembers().get(i).getFacebookID());
                            }

                            i += 1;
                        }

                    } else {

                        int i = 0;

                        while (i < group.getGroupMembers().size()) {
                            String facebookID = group.getGroupMembers().get(i).getFacebookID();
                            if (checkTargetList(facebookID)) {
                                int index = mTargetIDList.indexOf(facebookID);
                                mTargetIDList.remove(index);
                            }

                            i += 1;
                        }

                        mCheckBoxAllFriends.setChecked(false);
                    }
                }
            });

            if (mCheckTargetList) {

                int i = 0;

                while (i < group.getGroupMembers().size()) {

                    if (checkTargetList(group.getGroupMembers().get(i).getFacebookID())) {
                        checkBox.setChecked(true);
                    } else {
                        checkBox.setChecked(false);
                        return;
                    }

                    i += 1;
                }
            }

        }

        @Override
        public void onClick(View view) {

        }

    }

    private class GroupListAdapter extends RecyclerView.Adapter<GroupHolder> {

        private List<Group> mGroupsList;

        public GroupListAdapter(List<Group> groupsList) {
            mGroupsList = groupsList;
        }

        @Override
        public GroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_row_groups, parent, false);
            return new GroupHolder(view);
        }

        @Override
        public void onBindViewHolder(GroupHolder holder, int position) {

            Group mGroup = mGroupsList.get(position);
            holder.bindGroupItem(mGroup);

        }

        @Override
        public int getItemCount() {
            return mGroupsList.size();
        }

        public void setupAdapter() {
            if (isAdded()) {
                mPhotoRecylerView.setAdapter(new FriendListFragment.GroupListAdapter(mGroupsList));
            }
        }
    }

}
