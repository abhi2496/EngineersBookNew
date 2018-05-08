package com.example.abhishekkoranne.engineersbook.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Abhishek Koranne on 24-12-2017.
 */

public class User implements Parcelable {
    private int userId;
    private String userImageUrl;
    private String email = "";
    private String firstName = "";
    private String lastName = "";
    private String imageUrl = "";

    public User(int userId, String userImageUrl, String email, String firstName, String lastName, String imageUrl) {
        this.userId = userId;
        this.userImageUrl = userImageUrl;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
    }

    protected User(Parcel in) {
        userId = in.readInt();
        userImageUrl = in.readString();
        email = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        imageUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(userImageUrl);
        dest.writeString(email);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(imageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
