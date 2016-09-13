package com.gridyn.potspot.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EnableHostQuery {

    @SerializedName("token")
    @Expose
    public String token;

    @SerializedName("upload")
    @Expose
    public String[] upload = new String[1];
}
