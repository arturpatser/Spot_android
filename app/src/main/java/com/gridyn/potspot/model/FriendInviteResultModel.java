package com.gridyn.potspot.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmytro_vodnik on 8/31/16.
 * working on potspot project
 */
public class FriendInviteResultModel {

    @SerializedName("data")
    @Expose
    FriendInviteData data;

    @SerializedName("type")
    @Expose
    String type;

    public FriendInviteData getData() {
        return data;
    }

    public void setData(FriendInviteData data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "FriendInviteResultModel{" +
                "data=" + data +
                ", type='" + type + '\'' +
                '}';
    }

    public class FriendInviteData {

        @SerializedName("success")
        @Expose
        public boolean success;

        @SerializedName("user_name")
        @Expose
        public String userName;

        @SerializedName("request_id")
        @Expose
        public String requestId;

        @SerializedName("user_id")
        @Expose
        String userId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        @Override
        public String toString() {
            return "FriendInviteData{" +
                    "success=" + success +
                    ", userName='" + userName + '\'' +
                    ", requestId='" + requestId + '\'' +
                    ", userId='" + userId + '\'' +
                    '}';
        }
    }
}
