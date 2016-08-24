package com.gridyn.potspot.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dmytro_vodnik on 8/23/16.
 * working on potspot project
 */
public class Available {

    @SerializedName("days")
    @Expose
    public List<String> days;

    @SerializedName("time")
    @Expose
    public Integer[] time = new Integer[2];
}
