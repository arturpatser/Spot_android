package com.example.gridyn.potspot.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserUpdateQuery {

    @SerializedName("Photo")
    @Expose
    public String image;

    @SerializedName("AboutMe")
    @Expose
    public String aboutMe;

    @SerializedName("Birthday")
    @Expose
    public String birthday;

    @SerializedName("Gender")
    @Expose
    public String gender;

    @SerializedName("phone")
    @Expose
    public String phone;

    @SerializedName("Real ID")
    @Expose
    public String realId;

    @SerializedName("Card")
    @Expose
    public String card;

    @SerializedName("Paypal")
    @Expose
    public String paypal;
}
