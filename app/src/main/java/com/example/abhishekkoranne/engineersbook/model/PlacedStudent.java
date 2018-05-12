package com.example.abhishekkoranne.engineersbook.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PlacedStudent implements Parcelable {
    int userId;
    String fna,lna,proPic;
    long enrollmentNumber;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFna() {
        return fna;
    }

    public void setFna(String fna) {
        this.fna = fna;
    }

    public String getLna() {
        return lna;
    }

    public void setLna(String lna) {
        this.lna = lna;
    }

    public String getProPic() {
        return proPic;
    }

    public void setProPic(String proPic) {
        this.proPic = proPic;
    }

    public long getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public void setEnrollmentNumber(long enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    public static Creator<PlacedStudent> getCREATOR() {
        return CREATOR;
    }

    public PlacedStudent(int userId, String fna, String lna, String proPic, long enrollmentNumber) {
        this.userId = userId;
        this.fna = fna;
        this.lna = lna;
        this.proPic = proPic;
        this.enrollmentNumber = enrollmentNumber;
    }

    protected PlacedStudent(Parcel in) {
    }

    public static final Creator<PlacedStudent> CREATOR = new Creator<PlacedStudent>() {
        @Override
        public PlacedStudent createFromParcel(Parcel in) {
            return new PlacedStudent(in);
        }

        @Override
        public PlacedStudent[] newArray(int size) {
            return new PlacedStudent[size];
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
