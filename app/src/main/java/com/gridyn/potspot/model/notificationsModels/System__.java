
package com.gridyn.potspot.model.notificationsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 
public class System__ {

    @SerializedName("access")
    @Expose
    private String access;
    @SerializedName("isConfirmed")
    @Expose
    private Boolean isConfirmed;
    @SerializedName("TimeConfirmed")
    @Expose
    private Integer timeConfirmed;
    @SerializedName("isVerified")
    @Expose
    private Boolean isVerified;
    @SerializedName("TimeVerified")
    @Expose
    private Integer timeVerified;
    @SerializedName("PhoneVerified")
    @Expose
    private Boolean phoneVerified;
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
    @SerializedName("android_id")
    @Expose
    private String androidId;
    @SerializedName("waitingForVerify")
    @Expose
    private Boolean waitingForVerify;

    /**
     * 
     * @return
     *     The access
     */
    public String getAccess() {
        return access;
    }

    /**
     * 
     * @param access
     *     The access
     */
    public void setAccess(String access) {
        this.access = access;
    }

    /**
     * 
     * @return
     *     The isConfirmed
     */
    public Boolean getIsConfirmed() {
        return isConfirmed;
    }

    /**
     * 
     * @param isConfirmed
     *     The isConfirmed
     */
    public void setIsConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    /**
     * 
     * @return
     *     The timeConfirmed
     */
    public Integer getTimeConfirmed() {
        return timeConfirmed;
    }

    /**
     * 
     * @param timeConfirmed
     *     The TimeConfirmed
     */
    public void setTimeConfirmed(Integer timeConfirmed) {
        this.timeConfirmed = timeConfirmed;
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
     *     The phoneVerified
     */
    public Boolean getPhoneVerified() {
        return phoneVerified;
    }

    /**
     * 
     * @param phoneVerified
     *     The PhoneVerified
     */
    public void setPhoneVerified(Boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
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

    /**
     * 
     * @return
     *     The androidId
     */
    public String getAndroidId() {
        return androidId;
    }

    /**
     * 
     * @param androidId
     *     The android_id
     */
    public void setAndroidId(String androidId) {
        this.androidId = androidId;
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

}
