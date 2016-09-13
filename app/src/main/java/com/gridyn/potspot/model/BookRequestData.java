package com.gridyn.potspot.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmytro_vodnik on 8/30/16.
 * working on potspot project
 */
public class BookRequestData {

    @SerializedName("date")
    @Expose
    String date;

    @SerializedName("spot_id")
    @Expose
    String spotId;

    @SerializedName("spot_name")
    @Expose
    String spotName;

    @SerializedName("request_id")
    @Expose
    String requestId;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSpotId() {
        return spotId;
    }

    public void setSpotId(String spotId) {
        this.spotId = spotId;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "BookRequestData{" +
                "date='" + date + '\'' +
                ", spotId='" + spotId + '\'' +
                ", spotName='" + spotName + '\'' +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}
