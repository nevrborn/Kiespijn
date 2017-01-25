package com.bnnvara.kiespijn;

import com.bnnvara.kiespijn.Dilemma.DilemmaApiResponse;
import com.bnnvara.kiespijn.FriendList.Friend;
import com.bnnvara.kiespijn.GroupPage.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        addTempGroups();

        return mGroupsList;
    }

    public void setGroupsList(List<Group> groupsList) {
        mGroupsList = groupsList;
    }

    public void addGroupToGroupsList(Group group) {
        mGroupsList.add(group);
    }

    public void addTempGroups() {
        if (mGroupsList == null || (mGroupsList != null && mGroupsList.size() == 0)) {

            Friend paul = new Friend("Paul van Cappellen", "1272797916114496", "https://scontent.xx.fbcdn.net/v/t1.0-1/p200x200/14054015_1154486661278956_2640202812254557417_n.jpg?oh=6b2f0af784e478e8debf70221af2a05c&oe=591F024B");
            Friend eliza = new Friend("Eliza Cambre", "10210277093237768", "https://scontent.xx.fbcdn.net/v/t1.0-1/c90.41.576.576/s320x320/15337588_10209957675732530_6982606016405059815_n.jpg?oh=af0857e17e686184f7a2355abc5e6b5e&oe=591EC9B3");
            Friend dummy = new Friend("Dummy", "dummydata", "dummydata");

            List<Friend> friendlist1 = new ArrayList<>();
            List<Friend> friendlist2 = new ArrayList<>();

            friendlist1.add(paul);
            friendlist1.add(eliza);
            friendlist2.add(dummy);

            addGroupToGroupsList(new Group("BNN peeps", friendlist1));
            addGroupToGroupsList(new Group("Familie", friendlist2));
            addGroupToGroupsList(new Group("UNI buddies", friendlist2));
            addGroupToGroupsList(new Group("Utrecht crew", friendlist2));
        }
    }

}
