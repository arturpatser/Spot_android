
package com.gridyn.potspot.response.friendResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Data {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("imgs")
    @Expose
    private List<Object> imgs = new ArrayList<Object>();
    @SerializedName("spot")
    @Expose
    private List<Object> spot = new ArrayList<Object>();
    @SerializedName("stripe")
    @Expose
    private Stripe stripe;

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
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
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
     *     The birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * 
     * @param birthday
     *     The birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * 
     * @return
     *     The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * 
     * @param gender
     *     The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 
     * @return
     *     The imgs
     */
    public List<Object> getImgs() {
        return imgs;
    }

    /**
     * 
     * @param imgs
     *     The imgs
     */
    public void setImgs(List<Object> imgs) {
        this.imgs = imgs;
    }

    /**
     * 
     * @return
     *     The spot
     */
    public List<Object> getSpot() {
        return spot;
    }

    /**
     * 
     * @param spot
     *     The spot
     */
    public void setSpot(List<Object> spot) {
        this.spot = spot;
    }

    /**
     * 
     * @return
     *     The stripe
     */
    public Stripe getStripe() {
        return stripe;
    }

    /**
     * 
     * @param stripe
     *     The stripe
     */
    public void setStripe(Stripe stripe) {
        this.stripe = stripe;
    }

}
