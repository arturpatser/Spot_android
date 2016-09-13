package com.gridyn.potspot.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpotDeleteResponse {

    @SerializedName("success")
    @Expose
    public boolean success;
}
