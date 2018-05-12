package com.example.abhishekkoranne.engineersbook.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;



//alt-shift-ins for getter nd setter

public class Doubt implements Parcelable {
    private long time;
    private int doubtId;
    private String doubt = "";
    private String doubtHeading="";
    private String doubtImageUrl = "";
    private int upVote = 0;
    private int downVote = 0;
    private User user;
    private int numberOfAnswers = 0;
    private String tag="";

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getDoubtId() {
        return doubtId;
    }

    public void setDoubtId(int doubtId) {
        this.doubtId = doubtId;
    }

    public String getDoubt() {
        return doubt;
    }

    public void setDoubt(String doubt) {
        this.doubt = doubt;
    }

    public String getDoubtHeading() {
        return doubtHeading;
    }

    public void setDoubtHeading(String doubtHeading) {
        this.doubtHeading = doubtHeading;
    }

    public String getDoubtImageUrl() {
        return doubtImageUrl;
    }

    public void setDoubtImageUrl(String doubtImageUrl) {
        this.doubtImageUrl = doubtImageUrl;
    }

    public int getUpVote() {
        return upVote;
    }

    public void setUpVote(int upVote) {
        this.upVote = upVote;
    }

    public int getDownVote() {
        return downVote;
    }

    public void setDownVote(int downVote) {
        this.downVote = downVote;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNumberOfAnswers() {
        return numberOfAnswers;
    }

    public void setNumberOfAnswers(int numberOfAnswers) {
        this.numberOfAnswers = numberOfAnswers;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public static Creator<Doubt> getCREATOR() {
        return CREATOR;
    }

    public Doubt(long time, int doubtId, String doubt, String doubtHeading, String doubtImageUrl, int upVote, int downVote, User user, int numberOfAnswers, String tag) {
        this.time = time;
        this.doubtId = doubtId;
        this.doubt = doubt;
        this.doubtHeading = doubtHeading;
        this.doubtImageUrl = doubtImageUrl;
        this.upVote = upVote;
        this.downVote = downVote;
        this.user = user;
        this.numberOfAnswers = numberOfAnswers;
        this.tag = tag;
    }

    protected Doubt(Parcel in) {
        time = in.readLong();
        doubtId = in.readInt();
        doubt = in.readString();
        doubtHeading = in.readString();
        doubtImageUrl = in.readString();
        upVote = in.readInt();
        downVote = in.readInt();
        user = in.readParcelable(User.class.getClassLoader());
        numberOfAnswers = in.readInt();
        tag = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(time);
        dest.writeInt(doubtId);
        dest.writeString(doubt);
        dest.writeString(doubtHeading);
        dest.writeString(doubtImageUrl);
        dest.writeInt(upVote);
        dest.writeInt(downVote);
        dest.writeParcelable(user, flags);
        dest.writeInt(numberOfAnswers);
        dest.writeString(tag);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Doubt> CREATOR = new Creator<Doubt>() {
        @Override
        public Doubt createFromParcel(Parcel in) {
            return new Doubt(in);
        }

        @Override
        public Doubt[] newArray(int size) {
            return new Doubt[size];
        }
    };
}
