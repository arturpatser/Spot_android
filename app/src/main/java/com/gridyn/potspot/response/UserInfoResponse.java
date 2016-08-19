package com.gridyn.potspot.response;

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
    public List<Message> message = new ArrayList<>();

    public static class Message {

        @SerializedName("_id")
        @Expose
        public Id id;
        @SerializedName("data")
        @Expose
        public Data data;
        @SerializedName("system")
        @Expose
        public System_ system;

        public static class Id {

            @SerializedName("$id")
            @Expose
            public String $id;

        }

        public static class Data {

            @SerializedName("name")
            @Expose
            public String name;
            @SerializedName("email")
            @Expose
            public String email;
            @SerializedName("country")
            @Expose
            public String country;
            @SerializedName("about")
            @Expose
            public String about;
            @SerializedName("phone")
            @Expose
            public String phone;
            @SerializedName("address")
            @Expose
            public String address;
            public String paypalID; //TODO: delete
            public String cardID; //TODO: delete
            @SerializedName("birthday")
            @Expose
            public String birthday;
            @SerializedName("gender")
            @Expose
            public String gender;
            @SerializedName("imgs")
            @Expose
            public List<String> imgs = new ArrayList<>();
            @SerializedName("spot")
            @Expose
            public List<Spot> spot = new ArrayList<>();
            @SerializedName("stripe")
            @Expose
            public Stripe stripe;

            public static class Spot {

                @SerializedName("_id")
                @Expose
                public Id_ id;
                @SerializedName("data")
                @Expose
                public Data_ data;
                @SerializedName("system")
                @Expose
                public System system;

                public static class Id_ {
                    @SerializedName("$id")
                    @Expose
                    public String $id;
                }

                public static class Data_ {
                    @SerializedName("name")
                    @Expose
                    public String name;
                    @SerializedName("address")
                    @Expose
                    public String address;
                    @SerializedName("price")
                    @Expose
                    public Integer price;
                    @SerializedName("maxGuests")
                    @Expose
                    public String maxGuests;
                    @SerializedName("type")
                    @Expose
                    public String type;
                    @SerializedName("about")
                    @Expose
                    public String about;
                    @SerializedName("time")
                    @Expose
                    public String time;
                    @SerializedName("days")
                    @Expose
                    public String days;
                    @SerializedName("badge")
                    @Expose
                    public String badge;
                    @SerializedName("country")
                    @Expose
                    public String country;
        /*            @SerializedName("googlemapsapi")
                    @Expose
                    public Googlemapsapi googlemapsapi;*/
                }

                public static class System {
                    @SerializedName("owner")
                    @Expose
                    public String owner;
                    @SerializedName("isVerified")
                    @Expose
                    public boolean isVerified;
                    @SerializedName("TimeVerified")
                    @Expose
                    public int timeVerified;
                    @SerializedName("waitingForVerify")
                    @Expose
                    public boolean waitingForVerify;
                    @SerializedName("isActive")
                    @Expose
                    public boolean isActive;
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


            public static class Stripe {

                @SerializedName("costumer_token")
                @Expose
                public boolean costumerToken;
                @SerializedName("host_token")
                @Expose
                public boolean hostToken;

            }
        }

        public static class System_ {
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
            @SerializedName("PhoneVerified")
            @Expose
            public boolean phoneVerified;
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
            @SerializedName("waitingForVerify")
            @Expose
            public boolean waitingForVerify;
            @SerializedName("android_id")
            @Expose
            public String androidId;
        }
    }
}