
package com.gridyn.potspot.model.notificationsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

 
public class Data {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("timeStay")
    @Expose
    private Integer timeStay;
    @SerializedName("guests")
    @Expose
    private Integer guests;
    @SerializedName("friends")
    @Expose
    private List<Object> friends = new ArrayList<Object>();

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The timeStay
     */
    public Integer getTimeStay() {
        return timeStay;
    }

    /**
     * 
     * @param timeStay
     *     The timeStay
     */
    public void setTimeStay(Integer timeStay) {
        this.timeStay = timeStay;
    }

    /**
     * 
     * @return
     *     The guests
     */
    public Integer getGuests() {
        return guests;
    }

    /**
     * 
     * @param guests
     *     The guests
     */
    public void setGuests(Integer guests) {
        this.guests = guests;
    }

    /**
     * 
     * @return
     *     The friends
     */
    public List<Object> getFriends() {
        return friends;
    }

    /**
     * 
     * @param friends
     *     The friends
     */
    public void setFriends(List<Object> friends) {
        this.friends = friends;
    }

}
