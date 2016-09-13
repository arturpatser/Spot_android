package com.gridyn.potspot.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dmytro_vodnik on 8/17/16.
 * working on potspot project
 */
public class PhoneConfirmResponse {

    @SerializedName("success")
    @Expose
    public boolean success;
    @SerializedName("message")
    @Expose
    List<PhoneVerifyMessage> message;

    @Override
    public String toString() {
        return "PhoneVerifyResponse{" +
                "success=" + success +
                ", message=" + message +
                '}';
    }
}
