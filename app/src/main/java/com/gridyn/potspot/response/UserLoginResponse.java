package com.gridyn.potspot.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserLoginResponse {

    @SerializedName("success")
    @Expose
    public boolean success;

    @SerializedName("message")
    @Expose
    public List<MessageResponse> message = new ArrayList<>();

    public class MessageResponse {

        @SerializedName("token")
        @Expose
        public String token;

        @SerializedName("id")
        @Expose
        public String id;
    }

    @Override
    public String toString() {
        MessageResponse messageResponse = message.get(0);
        MessageResponse messageResponse1 = message.get(1);
        return "success" + success + "\n" + "message: token(" + messageResponse.token + "); id("
                + messageResponse1.id + ")";
    }
}
