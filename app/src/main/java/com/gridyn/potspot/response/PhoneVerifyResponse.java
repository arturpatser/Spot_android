package com.gridyn.potspot.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmytro_vodnik on 8/16/16.
 * working on potspot project
 */
public class PhoneVerifyResponse {

    @SerializedName("success")
    @Expose
    public boolean success;
    @SerializedName("message")
    @Expose
    PhoneVerifyMessage message;

    @Override
    public String toString() {
        return "PhoneVerifyResponse{" +
                "success=" + success +
                ", message=" + message +
                '}';
    }
}
