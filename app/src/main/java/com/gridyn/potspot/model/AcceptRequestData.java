package com.gridyn.potspot.model;

import com.google.firebase.database.Exclude;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmytro_vodnik on 8/29/16.
 * working on potspot project
 */
public class AcceptRequestData {

    @SerializedName("spotID")
    @Expose
    String spotId;

    @SerializedName("spotName")
    @Exclude
    String spotName;

    @SerializedName("success")
    @Expose
    boolean success;

    public String getSpotId() {
        return spotId;
    }

    public void setSpotId(String spotId) {
        this.spotId = spotId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    @Override
    public String toString() {
        return "AcceptRequestData{" +
                "spotId='" + spotId + '\'' +
                ", success=" + success +
                '}';
    }
}
