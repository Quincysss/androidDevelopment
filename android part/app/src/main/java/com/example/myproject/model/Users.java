package com.example.myproject.model;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Users implements Parcelable {
    private Integer userid;
    private String firstname;
    private String surname;
    private String EMail;
    private Date dob;
    private double height;
    private double weight;
    private String gender;
    private String address;
    private String postcode;
    private int actlevel;
    private double steps;

    public Users(Integer userid) { this.userid = userid; }

    public Users() { }

    public Users(String firstname, String surname, Date dob, double height, double weight, String gender, String EMail, String address, String postcode, int actlevel, double steps) {
        this.firstname = firstname;
        this.surname = surname;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.actlevel = actlevel;
        this.steps = steps;
        this.address = address;
        this.EMail = EMail;
        this.postcode = postcode;
    }
    protected Users (Parcel in)
    {
        this.userid = in.readInt();
        this.firstname = in.readString();
        this.surname = in.readString();
        this.dob = (Date) in.readSerializable();
        this.EMail = in.readString();
        this.height = in.readDouble();
        this.weight = in.readDouble();
        this.gender = in.readString();
        this.address = in.readString();
        this.postcode = in.readString();
        this.actlevel = in.readInt();
        this.steps = in.readDouble();
    }
    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userid);
        dest.writeString(firstname);
        dest.writeString(surname);
        dest.writeSerializable(dob);
        dest.writeString(EMail);
        dest.writeDouble(height);
        dest.writeDouble(weight);
        dest.writeString(gender);
        dest.writeString(address);
        dest.writeString(postcode);
        dest.writeInt(actlevel);
        dest.writeDouble(steps);
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public Date getDob() { return dob; }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public int getActlevel() {
        return actlevel;
    }

    public void setActlevel(int actlevel) {
        this.actlevel = actlevel;
    }

    public double getSteps() {
        return steps;
    }

    public void setSteps(double steps) {
        this.steps = steps;
    }
}

