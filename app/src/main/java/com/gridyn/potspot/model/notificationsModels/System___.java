
package com.gridyn.potspot.model.notificationsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 
public class System___ {

    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("isVerified")
    @Expose
    private Boolean isVerified;
    @SerializedName("TimeVerified")
    @Expose
    private Integer timeVerified;
    @SerializedName("waitingForVerify")
    @Expose
    private Boolean waitingForVerify;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("TimeCreated")
    @Expose
    private Integer timeCreated;
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
     *     The isVerified
     */
    public Boolean getIsVerified() {
        return isVerified;
    }

    /**
     * 
     * @param isVerified
     *     The isVerified
     */
    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    /**
     * 
     * @return
     *     The timeVerified
     */
    public Integer getTimeVerified() {
        return timeVerified;
    }

    /**
     * 
     * @param timeVerified
     *     The TimeVerified
     */
    public void setTimeVerified(Integer timeVerified) {
        this.timeVerified = timeVerified;
    }

    /**
     * 
     * @return
     *     The waitingForVerify
     */
    public Boolean getWaitingForVerify() {
        return waitingForVerify;
    }

    /**
     * 
     * @param waitingForVerify
     *     The waitingForVerify
     */
    public void setWaitingForVerify(Boolean waitingForVerify) {
        this.waitingForVerify = waitingForVerify;
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
     *     The timeCreated
     */
    public Integer getTimeCreated() {
        return timeCreated;
    }

    /**
     * 
     * @param timeCreated
     *     The TimeCreated
     */
    public void setTimeCreated(Integer timeCreated) {
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
