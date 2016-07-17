package com.gridyn.potspot.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedbackQuery {
    @SerializedName("token")
    @Expose
    public String token;

    @SerializedName("subject")
    @Expose
    public String subject;

    @SerializedName("message")
    @Expose
    public String message;
}
