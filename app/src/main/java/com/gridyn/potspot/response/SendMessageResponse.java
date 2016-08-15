package com.gridyn.potspot.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendMessageResponse {

    @SerializedName("success")
    @Expose
    public boolean success;
}
