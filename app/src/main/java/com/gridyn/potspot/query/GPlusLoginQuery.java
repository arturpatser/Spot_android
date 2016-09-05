package com.gridyn.potspot.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmytro_vodnik on 9/5/16.
 * working on potspot project
 */
public class GPlusLoginQuery {

    public GPlusLoginQuery(String token) {
        this.gplusToken = token;
    }

    public String getGplusToken() {
        return gplusToken;
    }

    public void setGplusToken(String gplusToken) {
        this.gplusToken = gplusToken;
    }

    @SerializedName("google_token")
    @Expose
    String gplusToken;

    @Override
    public String toString() {
        return "GPlusLoginQuery{" +
                "gplusToken='" + gplusToken + '\'' +
                '}';
    }
}
