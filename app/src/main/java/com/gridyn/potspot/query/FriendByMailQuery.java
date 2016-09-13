package com.gridyn.potspot.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmytro_vodnik on 8/31/16.
 * working on potspot project
 */
public class FriendByMailQuery {

    @SerializedName("token")
    @Expose
    String token;

    @SerializedName("email")
    @Expose
    String email;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "FriendByMailQuery{" +
                "token='" + token + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
