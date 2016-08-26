package com.gridyn.potspot.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dmytro_vodnik on 8/23/16.
 * working on potspot project
 */
public class Available implements Parcelable {

    @SerializedName("days")
    @Expose
    public List<String> days;

    @SerializedName("time")
    @Expose
    public String[] time = new String[2];

    public Available() {}


    protected Available(Parcel in) {
        days = in.createStringArrayList();
        time = in.createStringArray();
    }

    public static final Creator<Available> CREATOR = new Creator<Available>() {
        @Override
        public Available createFromParcel(Parcel in) {
            return new Available(in);
        }

        @Override
        public Available[] newArray(int size) {
            return new Available[size];
        }
    };

    @Override
    public String toString() {
        return "Available{" +
                "days=" + days +
                ", time=" + Arrays.toString(time) +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(days);
        dest.writeStringArray(time);
    }
}
