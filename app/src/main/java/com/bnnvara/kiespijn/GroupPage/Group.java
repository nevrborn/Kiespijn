package com.bnnvara.kiespijn.GroupPage;

import com.bnnvara.kiespijn.FriendList.Friend;

import java.util.List;

public class Group {

    private String mGroupName;
    private List<Friend> mGroupMembers;

    public Group(String groupName, List<Friend> groupMembers) {
        mGroupName = groupName;
        mGroupMembers = groupMembers;
    }

    public String getGroupName() {
        return mGroupName;
    }

    public void setGroupName(String groupName) {
        mGroupName = groupName;
    }

    public List<Friend> getGroupMembers() {
        return mGroupMembers;
    }

    public void setGroupMembers(List<Friend> groupMembers) {
        mGroupMembers = groupMembers;
    }

    public void addFriendToGroup(Friend friend) {
        mGroupMembers.add(friend);
    }


}