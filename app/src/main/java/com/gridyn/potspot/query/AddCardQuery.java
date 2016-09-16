package com.gridyn.potspot.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmytro_vodnik on 9/16/16.
 * working on PotSpotGitLab project
 */
public class AddCardQuery {

    @SerializedName("token")
    @Expose
    String token;

    @SerializedName("card_token")
    @Expose
    String cardToken;

    public AddCardQuery(String token, String id) {
        this.token = token;
        this.cardToken = id;
    }

    public String getCardToken() {
        return cardToken;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
