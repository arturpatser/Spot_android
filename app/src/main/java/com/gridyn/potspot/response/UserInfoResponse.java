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
            /*     @SerializedName("googlemapsapi")
                 @Expose
                 public Googlemapsapi googlemapsapi;*/
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
                    public int maxGuests;
                    @SerializedName("type")
                    @Expose
                    public String type;
                    @SerializedName("about")
                    @Expose
                    public String about;
                    @SerializedName("time")
                    @Expose
                    public List<Integer> time = new ArrayList<Integer>();
                    @SerializedName("days")
                    @Expose
                    public List<String> days = new ArrayList<String>();
                    @SerializedName("badge")
                    @Expose
                    public String badge;
                    @SerializedName("imgs")
                    @Expose
                    public List<String> imgs = new ArrayList<>();
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

        public class System_ {

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
            @SerializedName("android_id")
            @Expose
            public String androidId;

        }
    }
}