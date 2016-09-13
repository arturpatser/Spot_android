package com.gridyn.potspot.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserUpdateQuery {

    @SerializedName("token")
    @Expose
    public String token;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("about")
    @Expose
    public String about;

    @SerializedName("birthday")
    @Expose
    public String birthday;

    @SerializedName("gender")
    @Expose
    public String gender;

    @SerializedName("address")
    @Expose
    public String address;

    @SerializedName("phone")
    @Expose
    public String phone;

    @SerializedName("real_ID")
    @Expose
    public String realID;

    @SerializedName("card_ID")
    @Expose
    public String cardID;

    @SerializedName("paypal_ID")
    @Expose
    public String paypalID;

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("upload")
    @Expose
    public List<String> upload = new ArrayList<>();

    @Override
    public String toString() {
        return "UserUpdateQuery{" +
                "token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", about='" + about + '\'' +
                ", birthday='" + birthday + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", realID='" + realID + '\'' +
                ", cardID='" + cardID + '\'' +
                ", paypalID='" + paypalID + '\'' +
                ", email='" + email + '\'' +
                ", upload=" + upload +
                '}';
    }
}
