package com.gridyn.potspot.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmytro_vodnik on 8/29/16.
 * working on potspot project
 */
public class AcceptRequestData {

    @SerializedName("spot_id")
    @Expose
    String spotId;

    @SerializedName("spot_name")
    @Expose
    String spotName;

    @SerializedName("success")
    @Expose
    boolean success;

    @SerializedName("request_id")
    @Expose
    String requestId;

    public String getPartySize() {
        return partySize;
    }

    public void setPartySize(String partySize) {
        this.partySize = partySize;
    }

    @SerializedName("party_size")
    @Expose
    String partySize;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

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
