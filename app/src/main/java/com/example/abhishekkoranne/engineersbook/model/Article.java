package com.example.abhishekkoranne.engineersbook.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.jar.Pack200;

/**
 * Created by Abhishek Koranne on 24-12-2017.
 */

public class Article implements Parcelable {
    private long time;
    private int articleid;
    private String article_text_post = "";
    private String articleImageUrl = "";
    private int likes;
    private int shares;
    private User user;
    //ArrayList<Comment> commentList= new ArrayList<>();
    private int noOfComments = 0;


    protected Article(Parcel in) {
        time = in.readLong();
        articleid = in.readInt();
        article_text_post = in.readString();
        articleImageUrl = in.readString();
        likes = in.readInt();
        shares = in.readInt();
        user = in.readParcelable(User.class.getClassLoader());
        noOfComments = in.readInt();
    }

    public Article(long time, int articleid, String article_text_post, String articleImageUrl, int likes, int shares, User user, int noOfComments) {
        this.time = time;
        this.articleid = articleid;
        this.article_text_post = article_text_post;
        this.articleImageUrl = articleImageUrl;
        this.likes = likes;
        this.shares = shares;
        this.user = user;
        this.noOfComments = noOfComments;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(time);
        dest.writeInt(articleid);
        dest.writeString(article_text_post);
        dest.writeString(articleImageUrl);
        dest.writeInt(likes);
        dest.writeInt(shares);
        dest.writeParcelable(user, flags);
        dest.writeInt(noOfComments);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getArticleid() {
        return articleid;
    }

    public void setArticleid(int articleid) {
        this.articleid = articleid;
    }

    public String getArticle_text_post() {
        return article_text_post;
    }

    public void setArticle_text_post(String article_text_post) {
        this.article_text_post = article_text_post;
    }

    public String getArticleImageUrl() {
        return articleImageUrl;
    }

    public void setArticleImageUrl(String articleImageUrl) {
        this.articleImageUrl = articleImageUrl;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNoOfComments() {
        return noOfComments;
    }

    public void setNoOfComments(int noOfComments) {
        this.noOfComments = noOfComments;
    }

    public static Creator<Article> getCREATOR() {
        return CREATOR;
    }
}