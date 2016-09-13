
package com.gridyn.potspot.model.notificationsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

 
public class Data__ {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("maxGuests")
    @Expose
    private Integer maxGuests;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("badge")
    @Expose
    private List<String> badge = new ArrayList<String>();
    @SerializedName("available")
    @Expose
    private List<Available> available = new ArrayList<Available>();
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("googlemapsapi")
    @Expose
    private Googlemapsapi googlemapsapi;

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * @return
     *     The price
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * 
     * @return
     *     The maxGuests
     */
    public Integer getMaxGuests() {
        return maxGuests;
    }

    /**
     * 
     * @param maxGuests
     *     The maxGuests
     */
    public void setMaxGuests(Integer maxGuests) {
        this.maxGuests = maxGuests;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The about
     */
    public String getAbout() {
        return about;
    }

    /**
     * 
     * @param about
     *     The about
     */
    public void setAbout(String about) {
        this.about = about;
    }

    /**
     * 
     * @return
     *     The badge
     */
    public List<String> getBadge() {
        return badge;
    }

    /**
     * 
     * @param badge
     *     The badge
     */
    public void setBadge(List<String> badge) {
        this.badge = badge;
    }

    /**
     * 
     * @return
     *     The available
     */
    public List<Available> getAvailable() {
        return available;
    }

    /**
     * 
     * @param available
     *     The available
     */
    public void setAvailable(List<Available> available) {
        this.available = available;
    }

    /**
     * 
     * @return
     *     The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * 
     * @param country
     *     The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 
     * @return
     *     The googlemapsapi
     */
    public Googlemapsapi getGooglemapsapi() {
        return googlemapsapi;
    }

    /**
     * 
     * @param googlemapsapi
     *     The googlemapsapi
     */
    public void setGooglemapsapi(Googlemapsapi googlemapsapi) {
        this.googlemapsapi = googlemapsapi;
    }

}
