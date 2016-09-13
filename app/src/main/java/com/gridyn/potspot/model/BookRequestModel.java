package com.gridyn.potspot.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmytro_vodnik on 8/30/16.
 * working on potspot project
 */
public class BookRequestModel {

    @SerializedName("data")
    @Expose
    BookRequestData bookRequestData;

    @SerializedName("type")
    @Expose
    String type;

    public BookRequestData getBookRequestData() {
        return bookRequestData;
    }

    public void setBookRequestData(BookRequestData bookRequestData) {
        this.bookRequestData = bookRequestData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BookRequestModel{" +
                "bookRequestData=" + bookRequestData +
                ", type='" + type + '\'' +
                '}';
    }
}
