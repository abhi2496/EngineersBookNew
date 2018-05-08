package com.example.abhishekkoranne.engineersbook.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Abhishek Koranne on 28-03-2018.
 */

public class Student implements Parcelable {
    String enrollmentNumber;
    int departmentId;
    int collegeId;
    int universityId;
    int yearOfPassing;
    int points;
    String interest = "";
    User user;

    public Student(String enrollmentNumber, int departmentId, int collegeId, int universityId, int yearOfPassing, int points, String interest, User user) {
        this.enrollmentNumber = enrollmentNumber;
        this.departmentId = departmentId;
        this.collegeId = collegeId;
        this.universityId = universityId;
        this.yearOfPassing = yearOfPassing;
        this.points = points;
        this.interest = interest;
        this.user = user;
    }

    protected Student(Parcel in) {
        enrollmentNumber = in.readString();
        departmentId = in.readInt();
        collegeId = in.readInt();
        universityId = in.readInt();
        yearOfPassing = in.readInt();
        points = in.readInt();
        interest = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(enrollmentNumber);
        dest.writeInt(departmentId);
        dest.writeInt(collegeId);
        dest.writeInt(universityId);
        dest.writeInt(yearOfPassing);
        dest.writeInt(points);
        dest.writeString(interest);
        dest.writeParcelable(user, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public String getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public void setEnrollmentNumber(String enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }

    public int getUniversityId() {
        return universityId;
    }

    public void setUniversityId(int universityId) {
        this.universityId = universityId;
    }

    public int getYearOfPassing() {
        return yearOfPassing;
    }

    public void setYearOfPassing(int yearOfPassing) {
        this.yearOfPassing = yearOfPassing;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
