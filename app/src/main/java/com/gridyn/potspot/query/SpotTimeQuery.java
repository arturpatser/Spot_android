package com.gridyn.potspot.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gridyn.potspot.model.Available;

import java.util.List;

/**
 * Created by dmytro_vodnik on 8/23/16.
 * working on potspot project
 */
public class SpotTimeQuery {

    @SerializedName("available")
    @Expose
    List<Available> available;
}
