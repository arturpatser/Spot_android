package com.gridyn.potspot.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MessageLastResponse {

    @SerializedName("success")
    @Expose
    public boolean success;
    @SerializedName("message")
    @Expose
    public List<Message> message = new ArrayList<>();

    public static class Message {

        @SerializedName("messages")
        @Expose
        public List<List<Message_>> messages = new ArrayList<>();

        public static class Message_ {

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

                @SerializedName("message")
                @Expose
                public String message;

            }

            public class System {

                @SerializedName("user")
                @Expose
                public String user;
                @SerializedName("owner")
                @Expose
                public String owner;
                @SerializedName("isRead")
                @Expose
                public boolean isRead;
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
