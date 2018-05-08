package com.example.abhishekkoranne.engineersbook.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Abhishek Koranne on 22-03-2018.
 */

public class Login implements Parcelable
{

    protected Login(Parcel in) {
    }

    public static final Creator<Login> CREATOR = new Creator<Login>() {
        @Override
        public Login createFromParcel(Parcel in) {
            return new Login(in);
        }

        @Override
        public Login[] newArray(int size) {
            return new Login[size];
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
