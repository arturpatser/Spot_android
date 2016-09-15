package com.gridyn.potspot.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmytro_vodnik on 8/29/16.
 * working on potspot project
 */
public class AcceptRequestData implements Parcelable {

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

    @SerializedName("spot_price")
    @Expose
    int spotPrice;

    @SerializedName("party_size")
    @Expose
    String partySize;

    @SerializedName("amPmTimeFrom")
    @Expose
    String sAmPmFrom;

    @SerializedName("amPmTimeTo")
    @Expose
    String sAmPmTo;

    @SerializedName("bookTimeFrom")
    @Expose
    String sTimeFrom;

    @SerializedName("bookTimeTo")
    @Expose
    String sTimeTo;

    protected AcceptRequestData(Parcel in) {
        spotId = in.readString();
        spotName = in.readString();
        success = in.readByte() != 0;
        requestId = in.readString();
        spotPrice = in.readInt();
        partySize = in.readString();
        sAmPmFrom = in.readString();
        sAmPmTo = in.readString();
        sTimeFrom = in.readString();
        sTimeTo = in.readString();
    }

    public static final Creator<AcceptRequestData> CREATOR = new Creator<AcceptRequestData>() {
        @Override
        public AcceptRequestData createFromParcel(Parcel in) {
            return new AcceptRequestData(in);
        }

        @Override
        public AcceptRequestData[] newArray(int size) {
            return new AcceptRequestData[size];
        }
    };

    public String getsAmPmFrom() {
        return sAmPmFrom;
    }

    public void setsAmPmFrom(String sAmPmFrom) {
        this.sAmPmFrom = sAmPmFrom;
    }

    public String getsAmPmTo() {
        return sAmPmTo;
    }

    public void setsAmPmTo(String sAmPmTo) {
        this.sAmPmTo = sAmPmTo;
    }

    public String getsTimeFrom() {
        return sTimeFrom;
    }

    public void setsTimeFrom(String sTimeFrom) {
        this.sTimeFrom = sTimeFrom;
    }

    public String getsTimeTo() {
        return sTimeTo;
    }

    public void setsTimeTo(String sTimeTo) {
        this.sTimeTo = sTimeTo;
    }

    public int getSpotPrice() {
        return spotPrice;
    }

    public void setSpotPrice(int spotPrice) {
        this.spotPrice = spotPrice;
    }

    public String getPartySize() {
        return partySize;
    }

    public void setPartySize(String partySize) {
        this.partySize = partySize;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(spotId);
        dest.writeString(spotName);
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(requestId);
        dest.writeInt(spotPrice);
        dest.writeString(partySize);
        dest.writeString(sAmPmFrom);
        dest.writeString(sAmPmTo);
        dest.writeString(sTimeFrom);
        dest.writeString(sTimeTo);
    }
}
