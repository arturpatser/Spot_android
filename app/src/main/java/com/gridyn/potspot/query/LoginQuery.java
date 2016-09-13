package com.gridyn.potspot.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginQuery {

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("password")
    @Expose
    public String password;

    @SerializedName("android_id")
    @Expose
    public String androidId;
}
