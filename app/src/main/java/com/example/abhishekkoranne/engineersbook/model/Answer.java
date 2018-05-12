package com.example.abhishekkoranne.engineersbook.model;

import android.os.Parcel;
import android.os.Parcelable;



public class Answer implements Parcelable {
    private long time;
    private int doubtId;
    private int answerId;
    private int upVote = 0;
    private int downVote = 0;
    private String answer = "";
    private User user;

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

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Creator<Answer> getCREATOR() {
        return CREATOR;
    }

    public Answer(long time, int doubtId, int answerId, int upVote, int downVote, String answer, User user) {
        this.time = time;
        this.doubtId = doubtId;
        this.answerId = answerId;
        this.upVote = upVote;
        this.downVote = downVote;
        this.answer = answer;
        this.user = user;
    }

    protected Answer(Parcel in) {
        time = in.readLong();
        doubtId = in.readInt();
        answerId = in.readInt();
        upVote = in.readInt();
        downVote = in.readInt();
        answer = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(time);
        dest.writeInt(doubtId);
        dest.writeInt(answerId);
        dest.writeInt(upVote);
        dest.writeInt(downVote);
        dest.writeString(answer);
        dest.writeParcelable(user, flags);
    }
}
