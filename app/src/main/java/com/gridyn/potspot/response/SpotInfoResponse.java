package com.gridyn.potspot.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SpotInfoResponse {
    @SerializedName("success")
    @Expose
    public boolean success;
    @SerializedName("message")
    @Expose
    public List<Message> message = new ArrayList<>();

    public static class Message {

        @SerializedName("spots")
        @Expose
        public List<Spot> spots = new ArrayList<>();

        public static class Spot {

            @SerializedName("_id")
            @Expose
            public Id id;
            @SerializedName("data")
            @Expose
            public Data data;
            @SerializedName("system")
            @Expose
            public System system;

            public static class Id {

                @SerializedName("$id")
                @Expose
                public String $id;

            }

            public static class Data {

                @SerializedName("name")
                @Expose
                public String name;
                @SerializedName("address")
                @Expose
                public String address;
                @SerializedName("price")
                @Expose
                public int price;
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
                public List<Integer> time = new ArrayList<>();
                @SerializedName("days")
                @Expose
                public List<String> days = new ArrayList<>();
                @SerializedName("badge")
                @Expose
                public List<String> badge = new ArrayList<>();
                @SerializedName("country")
                @Expose
                public String country;
                //                @SerializedName("googlemapsapi")
//                @Expose
//                public Googlemapsapi googlemapsapi;
                @SerializedName("TimeOpened")
                @Expose
                public String timeOpened;
                @SerializedName("TimeClosed")
                @Expose
                public String timeClosed;
                @SerializedName("username")
                @Expose
                public String username;
                @SerializedName("imgs")
                @Expose
                public List<String> imgs = new ArrayList<>();
                @SerializedName("user_imgs")
                @Expose
                public List<String> userImgs = new ArrayList<>();

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
    }
}