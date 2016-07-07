package com.example.gridyn.potspot.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserInfoResponse {

    @SerializedName("success")
    @Expose
    public boolean success;

    @SerializedName("message")
    @Expose
    public List<Message> message = new ArrayList<Message>();

    public class Message {

        @SerializedName("_id")
        @Expose
        public Id id;
        @SerializedName("data")
        @Expose
        public Data data;
        @SerializedName("system")
        @Expose
        public System system;

        public Message() {
        }

        public class Id {

            @SerializedName("$id")
            @Expose
            public String id;
        }

        public class Data{
            @SerializedName("name")
            @Expose
            public String name;

            @SerializedName("email")
            @Expose
            public String email;

            @SerializedName("password")
            @Expose
            public String password;

            @SerializedName("about")
            @Expose
            public String about;

            @SerializedName("birthday")
            @Expose
            public String birthday;

            @SerializedName("gender")
            @Expose
            public String gender;

            @SerializedName("address")
            @Expose
            public String address;

            @SerializedName("phone")
            @Expose
            public String phone;

            @SerializedName("real_ID")
            @Expose
            public String realID;

            @SerializedName("card_ID")
            @Expose
            public String cardID;

            @SerializedName("paypal_ID")
            @Expose
            public String paypalID;

            @SerializedName("googlemapsapi")
            @Expose
            public String googlemapsapi;
        }

        public class System {
            @SerializedName("access")
            @Expose
            public String access;
            @SerializedName("isConfirmed")
            @Expose
            public boolean isConfirmed;
            @SerializedName("TimeConfirmed")
            @Expose
            public int timeConfirmed;
            @SerializedName("isVerified")
            @Expose
            public boolean isVerified;
            @SerializedName("TimeVerified")
            @Expose
            public int timeVerified;
            @SerializedName("token")
            @Expose
            public String token;
            @SerializedName("TimeCreated")
            @Expose
            public int timeCreated;
            @SerializedName("TimeUpdated")
            @Expose
            public int timeUpdated;
            @SerializedName("IP_Created")
            @Expose
            public String iPCreated;
            @SerializedName("IP_Updated")
            @Expose
            public String iPUpdated;
        }
    }
}
