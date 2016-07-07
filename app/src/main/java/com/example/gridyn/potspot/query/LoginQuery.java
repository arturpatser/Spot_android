package com.example.gridyn.potspot.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginQuery {

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("password")
    @Expose
    public String password;

    public LoginQuery(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
