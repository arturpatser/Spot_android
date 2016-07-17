package com.gridyn.potspot.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SearchQuery {

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("address")
    @Expose
    public String address;

    @SerializedName("price")
    @Expose
    public List<PriceResponse> price = new ArrayList<>();

    @SerializedName("maxGuests")
    @Expose
    public List<MaxGuestsResponse> maxGuests = new ArrayList<>();

    @SerializedName("distance")
    @Expose
    public List<DistanceResponse> distance = new ArrayList<>();

    @SerializedName("time")
    @Expose
    public List<TimeResponse> time = new ArrayList<>();

    @SerializedName("type")
    @Expose
    public List<String> type = new ArrayList<>();

    @SerializedName("days")
    @Expose
    public List<String> days = new ArrayList<>();

    private static class PriceResponse {

        @SerializedName("from")
        @Expose
        public int from;

        @SerializedName("to")
        @Expose
        public int to;
    }

    private static class MaxGuestsResponse {

        @SerializedName("from")
        @Expose
        public int from;

        @SerializedName("to")
        @Expose
        public int to;
    }

    private static class DistanceResponse {

        @SerializedName("lat")
        @Expose
        public int lat;

        @SerializedName("lng")
        @Expose
        public int lng;

        @SerializedName("radius")
        @Expose
        public int radius;
    }

    private static class TimeResponse {

        @SerializedName("from")
        @Expose
        public int from;

        @SerializedName("to")
        @Expose
        public int to;
    }
}
