package com.gridyn.potspot.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendMessageQuery {

    @SerializedName("token")
    @Expose
    public String token;

    @SerializedName("message")
    @Expose
    public String message;
}
