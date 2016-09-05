package com.gridyn.potspot.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmytro_vodnik on 9/3/16.
 * working on potspot project
 */
public class FacebookLoginQuery {

    @SerializedName("facebook_id")
    @Expose
    String facebookId;

    @SerializedName("facebook_token")
    @Expose
    String facebookToken;

    public FacebookLoginQuery(String id, String token) {
        this.facebookId = id;
        this.facebookToken = token;
    }

    public String getFacebookToken() {
        return facebookToken;
    }

    public void setFacebookToken(String facebookToken) {
        this.facebookToken = facebookToken;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    @Override
    public String toString() {
        return "FacebookLoginQuery{" +
                "facebookId='" + facebookId + '\'' +
                ", facebookToken='" + facebookToken + '\'' +
                '}';
    }
}
