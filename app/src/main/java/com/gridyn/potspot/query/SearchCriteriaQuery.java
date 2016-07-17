package com.gridyn.potspot.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SearchCriteriaQuery {

    @SerializedName("price")
    @Expose
    public int[] price = new int[2];

    @SerializedName("distance")
    @Expose
    public Distance distance;

    @SerializedName("maxGuest")
    @Expose
    public int[] maxGuest = new int[2];

    @SerializedName("badges")
    @Expose
    public List<String> badges = new ArrayList<>();

    @SerializedName("type")
    @Expose
    public List<String> type = new ArrayList<>();

    public static class Distance {

        @SerializedName("lat")
        @Expose
        public double lat;

        @SerializedName("lng")
        @Expose
        public double lng;

        @SerializedName("radius")
        @Expose
        public int radius;

    }
}
