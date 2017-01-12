package com.bnnvara.kiespijn.FriendList;

import java.util.List;

public class Groups {

    private List<Group> mGroupList;

    public List<Group> getGroupList() {
        return mGroupList;
    }

    public void setGroupList(List<Group> groupList) {
        mGroupList = groupList;
    }

    public void addGroupToGroupList(Group group) {
        mGroupList.add(group);
    }
}
