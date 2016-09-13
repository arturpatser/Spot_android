
package com.gridyn.potspot.model.notificationsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

 
public class Available_ {

    @SerializedName("days")
    @Expose
    private List<String> days = new ArrayList<String>();
    @SerializedName("time")
    @Expose
    private List<Integer> time = new ArrayList<Integer>();

    /**
     * 
     * @return
     *     The days
     */
    public List<String> getDays() {
        return days;
    }

    /**
     * 
     * @param days
     *     The days
     */
    public void setDays(List<String> days) {
        this.days = days;
    }

    /**
     * 
     * @return
     *     The time
     */
    public List<Integer> getTime() {
        return time;
    }

    /**
     * 
     * @param time
     *     The time
     */
    public void setTime(List<Integer> time) {
        this.time = time;
    }

}
