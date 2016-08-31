
package com.gridyn.potspot.model.bookInvite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("spot_id")
    @Expose
    private String spotId;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("spot_name")
    @Expose
    private String spotName;
    @SerializedName("request_id")
    @Expose
    private String requestId;

    /**
     * 
     * @return
     *     The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId
     *     The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 
     * @return
     *     The spotId
     */
    public String getSpotId() {
        return spotId;
    }

    /**
     * 
     * @param spotId
     *     The spot_id
     */
    public void setSpotId(String spotId) {
        this.spotId = spotId;
    }

    /**
     * 
     * @return
     *     The success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * 
     * @param success
     *     The success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * 
     * @return
     *     The userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 
     * @param userName
     *     The user_name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 
     * @return
     *     The spotName
     */
    public String getSpotName() {
        return spotName;
    }

    /**
     * 
     * @param spotName
     *     The spot_name
     */
    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    /**
     * 
     * @return
     *     The requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * 
     * @param requestId
     *     The request_id
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "Data{" +
                "userId='" + userId + '\'' +
                ", spotId='" + spotId + '\'' +
                ", success=" + success +
                ", userName='" + userName + '\'' +
                ", spotName='" + spotName + '\'' +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}
