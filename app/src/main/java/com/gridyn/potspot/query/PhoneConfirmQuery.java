package com.gridyn.potspot.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmytro_vodnik on 8/17/16.
 * working on potspot project
 */
public class PhoneConfirmQuery {

    @SerializedName("phone")
    @Expose
    String phone;

    @SerializedName("token")
    @Expose
    String token;

    @SerializedName("code")
    @Expose
    String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "PhoneConfirmQuery{" +
                "phone='" + phone + '\'' +
                ", token='" + token + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
