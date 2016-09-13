package com.gridyn.potspot.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmytro_vodnik on 8/29/16.
 * working on potspot project
 */
public class AcceptRequestModel {

    @SerializedName("type")
    @Expose
    String type;

    @SerializedName("data")
    @Expose
    AcceptRequestData acceptRequestData;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AcceptRequestData getAcceptRequestData() {
        return acceptRequestData;
    }

    public void setAcceptRequestData(AcceptRequestData acceptRequestData) {
        this.acceptRequestData = acceptRequestData;
    }

    @Override
    public String toString() {
        return "AcceptRequestModel{" +
                "type='" + type + '\'' +
                ", acceptRequestData=" + acceptRequestData +
                '}';
    }
}
