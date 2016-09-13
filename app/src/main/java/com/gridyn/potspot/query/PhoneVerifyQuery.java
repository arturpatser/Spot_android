package com.gridyn.potspot.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmytro_vodnik on 8/16/16.
 * working on potspot project
 */
public class PhoneVerifyQuery {

    @SerializedName("phone")
    @Expose
    String phone;

    @SerializedName("token")
    @Expose
    String token;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
