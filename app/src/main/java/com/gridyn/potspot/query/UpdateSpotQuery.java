package com.gridyn.potspot.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UpdateSpotQuery {
    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("token")
    @Expose
    public String token;

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

    @SerializedName("about")
    @Expose
    public String about;

    @SerializedName("type")
    @Expose
    public String type;

    @SerializedName("badge")
    @Expose
    public List<String> badges = new ArrayList<>();
}
