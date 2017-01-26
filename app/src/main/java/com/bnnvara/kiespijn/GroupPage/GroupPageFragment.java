package com.bnnvara.kiespijn.GroupPage;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bnnvara.kiespijn.FriendList.Friend;
import com.bnnvara.kiespijn.R;
import com.bnnvara.kiespijn.User;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class GroupPageFragment extends Fragment {

    private List<Group> mGroupsList;
    private RecyclerView mPhotoRecylerView;
    private List<CheckBox> mCheckBoxList = new ArrayList<>();
    private List<Friend> mFriendList;
    private List<Friend> mNewGroupList = new ArrayList<>();
    private List<Group> mDeleteGroupList = new ArrayList<>();

    public static Fragment newInstance() {
        return new GroupPageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_page, container, false);

        final User user = User.getInstance();

        mFriendList = user.getFacebookFriendList();
        mGroupsList = user.getGroupsList();

        final EditText addGroupNameEditText = (EditText) view.findViewById(R.id.edittext_make_group_name);
        final TextView saveButton = (TextView) view.findViewById(R.id.text_view_save_group);
        final TextView deleteButton = (TextView) view.findViewById(R.id.text_view_delete_groups);
        final TextView title = (TextView) view.findViewById(R.id.textview_group_title);
        final Button groupsButton = (Button) view.findViewById(R.id.group_page_groups);
        final Button makeGroupButton = (Button) view.findViewById(R.id.group_page_new_group);

        mPhotoRecylerView = (RecyclerView) view.findViewById(R.id.group_list_recycler_view);
        mPhotoRecylerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // FONT setup
        Typeface source_sans_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Bold.ttf");
        title.setTypeface(source_sans_bold);
        groupsButton.setTypeface(source_sans_bold);
        makeGroupButton.setTypeface(source_sans_bold);
        saveButton.setTypeface(source_sans_bold);
        deleteButton.setTypeface(source_sans_bold);

        final FriendListAdapter friendAdapter = new FriendListAdapter(mFriendList);
        final GroupListAdapter groupAdapter = new GroupListAdapter(mGroupsList);
        groupAdapter.setupAdapter();

        addGroupNameEditText.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);
        deleteButton.setVisibility(View.VISIBLE);

        groupsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGroupNameEditText.setVisibility(View.GONE);
                saveButton.setVisibility(View.GONE);
                deleteButton.setVisibility(View.VISIBLE);
                groupsButton.setAlpha(1);
                makeGroupButton.setAlpha(0.65f);

                mGroupsList = user.getGroupsList();
                groupAdapter.setupAdapter();
            }
        });

        makeGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGroupNameEditText.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.GONE);
                makeGroupButton.setAlpha(1);
                groupsButton.setAlpha(0.65f);
                friendAdapter.setupAdapter();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!addGroupNameEditText.getText().toString().equals("") && mNewGroupList.size() != 0) {
                    if (mNewGroupList.size() != 0) {
                        String groupName = addGroupNameEditText.getText().toString();
                        Group newGroup = new Group(groupName, mNewGroupList);
                        user.addGroupToGroupsList(newGroup);
                        mGroupsList = user.getGroupsList();
                        groupsButton.performClick();
                    } else {
                        Toast.makeText(getActivity(), R.string.check_at_least_one_friend, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.fill_in_group_name, Toast.LENGTH_SHORT).show();
                }

                addGroupNameEditText.setText("");
                addGroupNameEditText.setHint(R.string.fill_in_group_name);

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.deleteGroupsFromGroupsList(mDeleteGroupList);

                mGroupsList = user.getGroupsList();

                groupAdapter.setupAdapter();
            }
        });

        return view;
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
                    if (checkBox.isChecked()) {
                        mNewGroupList.add(friend);
                    } else {
                        int i = mNewGroupList.indexOf(friend);
                        mNewGroupList.remove(i);
                    }
                }
            });
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
        TextView groupFriendsName;
        Group mGroup;
        Boolean mSeeWholeGroup;

        public GroupHolder(View itemView) {
            super(itemView);
            groupName = (TextView) itemView.findViewById(R.id.textview_group_name);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox_group);
            groupFriendsName = (TextView) itemView.findViewById(R.id.textview_groups_names);

            itemView.setOnClickListener(this);
        }

        public void bindGroupItem(final Group group) {

            mGroup = group;
            groupName.setText(group.getGroupName());
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkBox.isChecked()) {
                        mDeleteGroupList.add(group);
                    } else {
                        mDeleteGroupList.remove(group);
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {
            if (mSeeWholeGroup = true) {
                seeMembersOfGroup(mGroup);
                mSeeWholeGroup = false;
            } else {
                groupFriendsName.setText("");
                groupFriendsName.setText(R.string.tap_to_see_group_members);
                mSeeWholeGroup = true;
            }

        }

        private void seeMembersOfGroup(Group group) {
            int i = 0;

            while (i < group.getGroupMembers().size()) {
                if (i == 0) {
                    groupFriendsName.setText(group.getGroupMembers().get(i).getName());
                } else {
                    groupFriendsName.setText(groupFriendsName.getText() + ", " + group.getGroupMembers().get(i).getName());
                }

                i += 1;
            }

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
                mPhotoRecylerView.setAdapter(new GroupListAdapter(mGroupsList));
            }
        }
    }
}
