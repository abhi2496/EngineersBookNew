package com.example.abhishekkoranne.engineersbook.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Profile implements Parcelable {
    int doubtAsked;
    int articlesAdded;
    int point;
    ArrayList<Article> articles=new ArrayList<>();
    ArrayList<Doubt> doubts=new ArrayList<>();

    public int getDoubtAsked() {
        return doubtAsked;
    }

    public void setDoubtAsked(int doubtAsked) {
        this.doubtAsked = doubtAsked;
    }

    public int getArticlesAdded() {
        return articlesAdded;
    }

    public void setArticlesAdded(int articlesAdded) {
        this.articlesAdded = articlesAdded;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public ArrayList<Doubt> getDoubts() {
        return doubts;
    }

    public void setDoubts(ArrayList<Doubt> doubts) {
        this.doubts = doubts;
    }

    public static Creator<Profile> getCREATOR() {
        return CREATOR;
    }

    public Profile(int doubtAsked, int articlesAdded, int point, ArrayList<Article> articles, ArrayList<Doubt> doubts) {
        this.doubtAsked = doubtAsked;
        this.articlesAdded = articlesAdded;
        this.point = point;
        this.articles = articles;
        this.doubts = doubts;
    }

    protected Profile(Parcel in) {
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
