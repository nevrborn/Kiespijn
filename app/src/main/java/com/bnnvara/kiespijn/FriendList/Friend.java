package com.bnnvara.kiespijn.FriendList;

public class Friend {

    private String mName;
    private String mFacebookID;
    private String mProfilePicURL;

    public Friend(String name, String facebookID, String profilePicURL) {
        mName = name;
        mFacebookID = facebookID;
        mProfilePicURL = profilePicURL;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getFacebookID() {
        return mFacebookID;
    }

    public void setFacebookID(String facebookID) {
        mFacebookID = facebookID;
    }

    public String getProfilePicURL() {
        return mProfilePicURL;
    }

    public void setProfilePicURL(String profilePicURL) {
        mProfilePicURL = profilePicURL;
    }


}
