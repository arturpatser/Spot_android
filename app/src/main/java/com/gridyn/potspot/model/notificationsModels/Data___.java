
package com.gridyn.potspot.model.notificationsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

 
public class Data___ {

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
    @SerializedName("time")
    @Expose
    private List<Integer> time = new ArrayList<Integer>();
    @SerializedName("days")
    @Expose
    private List<String> days = new ArrayList<String>();
    @SerializedName("badge")
    @Expose
    private List<String> badge = new ArrayList<String>();
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("googlemapsapi")
    @Expose
    private Googlemapsapi_ googlemapsapi;
    @SerializedName("available")
    @Expose
    private List<Available_> available = new ArrayList<Available_>();
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("imgs")
    @Expose
    private List<String> imgs = new ArrayList<String>();
    @SerializedName("user_imgs")
    @Expose
    private List<Object> userImgs = new ArrayList<Object>();
    @SerializedName("inFavorites")
    @Expose
    private Boolean inFavorites;

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
    public Googlemapsapi_ getGooglemapsapi() {
        return googlemapsapi;
    }

    /**
     * 
     * @param googlemapsapi
     *     The googlemapsapi
     */
    public void setGooglemapsapi(Googlemapsapi_ googlemapsapi) {
        this.googlemapsapi = googlemapsapi;
    }

    /**
     * 
     * @return
     *     The available
     */
    public List<Available_> getAvailable() {
        return available;
    }

    /**
     * 
     * @param available
     *     The available
     */
    public void setAvailable(List<Available_> available) {
        this.available = available;
    }

    /**
     * 
     * @return
     *     The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     * @param username
     *     The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 
     * @return
     *     The imgs
     */
    public List<String> getImgs() {
        return imgs;
    }

    /**
     * 
     * @param imgs
     *     The imgs
     */
    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    /**
     * 
     * @return
     *     The userImgs
     */
    public List<Object> getUserImgs() {
        return userImgs;
    }

    /**
     * 
     * @param userImgs
     *     The user_imgs
     */
    public void setUserImgs(List<Object> userImgs) {
        this.userImgs = userImgs;
    }

    /**
     * 
     * @return
     *     The inFavorites
     */
    public Boolean getInFavorites() {
        return inFavorites;
    }

    /**
     * 
     * @param inFavorites
     *     The inFavorites
     */
    public void setInFavorites(Boolean inFavorites) {
        this.inFavorites = inFavorites;
    }

}
