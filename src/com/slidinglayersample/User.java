package com.slidinglayersample;

/**
 * @author bamboo
 * @since 3/22/14 7:54 AM
 */
public class User {

    private String mId = "1";

    private String mName = "Andriy Bas";

    private String mNickname;

    private String mEmail = "bamboo@oyster.com";

    private String mPassword = "super_password";

    private int mRating = 0;

    private String mPhoneNumber = "+380500648305";

    private String mDateOfBirth = "05 / 12 / 1994";

    private static User mCurrentUser = null;

    public static User getCurrentUser() {
        return mCurrentUser;
    }

    public static boolean authorizeUser(User user) {
        if (mCurrentUser == null) {
            mCurrentUser = user;
            return true;
        }
        return false;
    }


    public static void signOutUser() {
        mCurrentUser = null;
    }


    public User() {

    }

    public User(String id, String name, String nickname, String email, String password) {
        mId = id;
        mName = name;
        mNickname = nickname;
        mEmail = email;
        mPassword = password;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getNickname() {
        return mNickname;
    }

    public void setNickname(String nickname) {
        mNickname = nickname;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        mRating = rating;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return mDateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        mDateOfBirth = dateOfBirth;
    }
}
