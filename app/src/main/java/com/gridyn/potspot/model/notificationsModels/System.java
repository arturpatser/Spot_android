
package com.gridyn.potspot.model.notificationsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

 
public class System {

    @SerializedName("spot")
    @Expose
    private String spot;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("stage")
    @Expose
    private Integer stage;
    @SerializedName("cancelMessage")
    @Expose
    private String cancelMessage;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("full_price")
    @Expose
    private Integer fullPrice;
    @SerializedName("payed")
    @Expose
    private Boolean payed;
    @SerializedName("invited_friends")
    @Expose
    private List<Object> invitedFriends = new ArrayList<Object>();
    @SerializedName("TimeCreated")
    @Expose
    private long timeCreated;
    @SerializedName("TimeUpdated")
    @Expose
    private Integer timeUpdated;
    @SerializedName("IP_Created")
    @Expose
    private String iPCreated;
    @SerializedName("IP_Updated")
    @Expose
    private String iPUpdated;

    /**
     * 
     * @return
     *     The spot
     */
    public String getSpot() {
        return spot;
    }

    /**
     * 
     * @param spot
     *     The spot
     */
    public void setSpot(String spot) {
        this.spot = spot;
    }

    /**
     * 
     * @return
     *     The owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * 
     * @param owner
     *     The owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * 
     * @return
     *     The isActive
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * 
     * @param isActive
     *     The isActive
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * 
     * @return
     *     The stage
     */
    public Integer getStage() {
        return stage;
    }

    /**
     * 
     * @param stage
     *     The stage
     */
    public void setStage(Integer stage) {
        this.stage = stage;
    }

    /**
     * 
     * @return
     *     The cancelMessage
     */
    public String getCancelMessage() {
        return cancelMessage;
    }

    /**
     * 
     * @param cancelMessage
     *     The cancelMessage
     */
    public void setCancelMessage(String cancelMessage) {
        this.cancelMessage = cancelMessage;
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
     *     The fullPrice
     */
    public Integer getFullPrice() {
        return fullPrice;
    }

    /**
     * 
     * @param fullPrice
     *     The full_price
     */
    public void setFullPrice(Integer fullPrice) {
        this.fullPrice = fullPrice;
    }

    /**
     * 
     * @return
     *     The payed
     */
    public Boolean getPayed() {
        return payed;
    }

    /**
     * 
     * @param payed
     *     The payed
     */
    public void setPayed(Boolean payed) {
        this.payed = payed;
    }

    /**
     * 
     * @return
     *     The invitedFriends
     */
    public List<Object> getInvitedFriends() {
        return invitedFriends;
    }

    /**
     * 
     * @param invitedFriends
     *     The invited_friends
     */
    public void setInvitedFriends(List<Object> invitedFriends) {
        this.invitedFriends = invitedFriends;
    }

    /**
     * 
     * @return
     *     The timeCreated
     */
    public long getTimeCreated() {
        return timeCreated;
    }

    /**
     * 
     * @param timeCreated
     *     The TimeCreated
     */
    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    /**
     * 
     * @return
     *     The timeUpdated
     */
    public Integer getTimeUpdated() {
        return timeUpdated;
    }

    /**
     * 
     * @param timeUpdated
     *     The TimeUpdated
     */
    public void setTimeUpdated(Integer timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    /**
     * 
     * @return
     *     The iPCreated
     */
    public String getIPCreated() {
        return iPCreated;
    }

    /**
     * 
     * @param iPCreated
     *     The IP_Created
     */
    public void setIPCreated(String iPCreated) {
        this.iPCreated = iPCreated;
    }

    /**
     * 
     * @return
     *     The iPUpdated
     */
    public String getIPUpdated() {
        return iPUpdated;
    }

    /**
     * 
     * @param iPUpdated
     *     The IP_Updated
     */
    public void setIPUpdated(String iPUpdated) {
        this.iPUpdated = iPUpdated;
    }

}
