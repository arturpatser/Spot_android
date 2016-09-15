package com.gridyn.potspot.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmytro_vodnik on 8/29/16.
 * working on potspot project
 */
public class AcceptRequestModel implements Parcelable {

    @SerializedName("type")
    @Expose
    String type;

    @SerializedName("data")
    @Expose
    AcceptRequestData acceptRequestData;

    protected AcceptRequestModel(Parcel in) {
        type = in.readString();
        acceptRequestData = in.readParcelable(AcceptRequestData.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeParcelable(acceptRequestData, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AcceptRequestModel> CREATOR = new Creator<AcceptRequestModel>() {
        @Override
        public AcceptRequestModel createFromParcel(Parcel in) {
            return new AcceptRequestModel(in);
        }

        @Override
        public AcceptRequestModel[] newArray(int size) {
            return new AcceptRequestModel[size];
        }
    };

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
