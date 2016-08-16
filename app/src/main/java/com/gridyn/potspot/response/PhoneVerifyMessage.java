package com.gridyn.potspot.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmytro_vodnik on 8/16/16.
 * working on potspot project
 */
public class PhoneVerifyMessage {

    @SerializedName("phone")
    @Expose
    String message;
}
