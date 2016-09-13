
package com.gridyn.potspot.model.notificationsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 
public class Message {

    @SerializedName("_id")
    @Expose
    private Id id;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("system")
    @Expose
    private System system;
    @SerializedName("request_type")
    @Expose
    private String requestType;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("spot")
    @Expose
    private Spot_ spot;

    /**
     * 
     * @return
     *     The id
     */
    public Id getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The _id
     */
    public void setId(Id id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The data
     */
    public Data getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data data) {
        this.data = data;
    }

    /**
     * 
     * @return
     *     The system
     */
    public System getSystem() {
        return system;
    }

    /**
     * 
     * @param system
     *     The system
     */
    public void setSystem(System system) {
        this.system = system;
    }

    /**
     * 
     * @return
     *     The requestType
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * 
     * @param requestType
     *     The request_type
     */
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    /**
     * 
     * @return
     *     The user
     */
    public User getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *     The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 
     * @return
     *     The spot
     */
    public Spot_ getSpot() {
        return spot;
    }

    /**
     * 
     * @param spot
     *     The spot
     */
    public void setSpot(Spot_ spot) {
        this.spot = spot;
    }

}
