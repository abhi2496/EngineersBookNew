package com.example.abhishekkoranne.engineersbook.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {
    private long time;
    private int commentId;
    private int articleID;
    private String comment = "";
    private User user;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getArticleID() {
        return articleID;
    }

    public void setArticleID(int articleID) {
        this.articleID = articleID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Creator<Comment> getCREATOR() {
        return CREATOR;
    }

    public Comment(long time, int commentId, int articleID, String comment, User user) {
        this.time = time;
        this.commentId = commentId;
        this.articleID = articleID;
        this.comment = comment;
        this.user = user;
    }

    protected Comment(Parcel in) {
        time = in.readLong();
        commentId = in.readInt();
        articleID = in.readInt();
        comment = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(time);
        dest.writeInt(commentId);
        dest.writeInt(articleID);
        dest.writeString(comment);
        dest.writeParcelable(user, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
