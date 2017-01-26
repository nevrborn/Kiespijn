package com.bnnvara.kiespijn;

import com.bnnvara.kiespijn.Dilemma.DilemmaApiResponse;
import com.bnnvara.kiespijn.FriendList.Friend;
import com.bnnvara.kiespijn.GroupPage.Group;

import java.util.ArrayList;
import java.util.List;

public class User {

    // Singleton
    private static User sCurrentUser;

    private String mUserKey = "";
    private String mName;
    private String mEmail;
    private String mSex;
    private String mAge;
    private String mProfilePictureURL;
    private String mHometown;
    private DilemmaApiResponse mUserCreatedDilemmas;
    private DilemmaApiResponse mUserAnsweredDilemmas;
    private List<Friend> mFacebookFriendList = new ArrayList<>();
    private List<Group> mGroupsList = new ArrayList<>();
    private Boolean hasCreatedDilemma = false;


    private User() {

    }

    public static User getInstance() {
        if (sCurrentUser == null) {
            User user = new User();
            User.setInstance(user);
        }

        return sCurrentUser;
    }

    private static void setInstance(User user) {
        sCurrentUser = user;
    }

    public String getUserKey() {
        return mUserKey;
    }

    public void setUserKey(String userKey) {
        mUserKey = userKey;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public DilemmaApiResponse getUserCreatedDilemmas() {
        return mUserCreatedDilemmas;
    }

    public void setUserCreatedDilemmas(DilemmaApiResponse userCreatedDilemmas) {
        mUserCreatedDilemmas = userCreatedDilemmas;
    }

    public DilemmaApiResponse getUserAnsweredDilemmas() {
        return mUserAnsweredDilemmas;
    }

    public void setUserAnsweredDilemmas(DilemmaApiResponse userAnsweredDilemmas) {
        mUserAnsweredDilemmas = userAnsweredDilemmas;
    }

    public String getSex() {
        return mSex;
    }

    public void setSex(String sex) {
        mSex = sex;
    }

    public String getAge() {
        return mAge;
    }

    public void setAge(String age) {
        mAge = age;
    }

    public String getProfilePictureURL() {
        return mProfilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        mProfilePictureURL = profilePictureURL;
    }

    public List<Friend> getFacebookFriendList() {
        return mFacebookFriendList;
    }

    public void setFacebookFriendList(List<Friend> facebookFriendList) {
        mFacebookFriendList = facebookFriendList;
    }

    public void addFriendToFacebookFriendList(Friend friend) {
        mFacebookFriendList.add(friend);
    }

    public String getHometown() {
        return mHometown;
    }

    public void setHometown(String hometown) {
        mHometown = hometown;
    }

    public Boolean getHasCreatedDilemma() {
        return hasCreatedDilemma;
    }

    public void setHasCreatedDilemma(Boolean hasCreatedDilemma) {
        this.hasCreatedDilemma = hasCreatedDilemma;
    }

    public List<Group> getGroupsList() {
        return mGroupsList;
    }

    public void setGroupsList(List<Group> groupsList) {
        mGroupsList = groupsList;
    }

    public void addGroupToGroupsList(Group group) {
        mGroupsList.add(group);
    }

    public void deleteGroupsFromGroupsList(List<Group> groupList) {
        int i = 0;

        while (i < groupList.size()) {
            if (mGroupsList.contains(groupList.get(i))) {
                int groupIndex = mGroupsList.indexOf(groupList.get(i));
                mGroupsList.remove(groupIndex);
            }
            i += 1;
        }

    }

}
