package com.gridyn.potspot.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MySpotResponse {

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

            }
        }
    }
}
