package com.gridyn.potspot.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmytro_vodnik on 8/30/16.
 * working on potspot project
 */
public class BookQuery {

    @SerializedName("date")
    @Expose
    String date;

    @SerializedName("timeStay")
    @Expose
    int timeStay;

    @SerializedName("guests")
    @Expose
    int guests;

    @SerializedName("token")
    @Expose
    String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTimeStay() {
        return timeStay;
    }

    public void setTimeStay(int timeStay) {
        this.timeStay = timeStay;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }
}
