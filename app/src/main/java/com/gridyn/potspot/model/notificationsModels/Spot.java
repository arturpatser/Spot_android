
package com.gridyn.potspot.model.notificationsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 
public class Spot {

    @SerializedName("_id")
    @Expose
    private Id__ id;
    @SerializedName("data")
    @Expose
    private Data__ data;
    @SerializedName("system")
    @Expose
    private System_ system;

    /**
     * 
     * @return
     *     The id
     */
    public Id__ getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The _id
     */
    public void setId(Id__ id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The data
     */
    public Data__ getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data__ data) {
        this.data = data;
    }

    /**
     * 
     * @return
     *     The system
     */
    public System_ getSystem() {
        return system;
    }

    /**
     * 
     * @param system
     *     The system
     */
    public void setSystem(System_ system) {
        this.system = system;
    }

}
