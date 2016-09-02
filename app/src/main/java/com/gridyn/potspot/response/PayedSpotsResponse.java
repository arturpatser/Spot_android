package com.gridyn.potspot.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dmytro_vodnik on 9/2/16.
 * working on potspot project
 */
public class PayedSpotsResponse {

    @SerializedName("success")
    @Expose
    boolean success;

    @SerializedName("message")
    @Expose
    List<UserInfoResponse.Message.Data.Spot> message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<UserInfoResponse.Message.Data.Spot> getMessage() {
        return message;
    }

    public void setMessage(List<UserInfoResponse.Message.Data.Spot> message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PayedSpotsResponse{" +
                "success=" + success +
                ", message=" + message +
                '}';
    }
}
