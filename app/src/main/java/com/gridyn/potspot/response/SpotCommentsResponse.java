package com.gridyn.potspot.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SpotCommentsResponse {

    @SerializedName("success")
    @Expose
    public boolean success;
    @SerializedName("message")
    @Expose
    public List<Message> message = new ArrayList<>();

    public static class Message {

        @SerializedName("comments")
        @Expose
        public List<Comment> comments = new ArrayList<>();

    }

    public static class Comment {

        @SerializedName("_id")
        @Expose
        public Id id;
        @SerializedName("data")
        @Expose
        public Data data;

        public class Id {

            @SerializedName("$id")
            @Expose
            public String $id;

        }

        public class Data {

            @SerializedName("comment")
            @Expose
            public String comment;

        }
    }

}
