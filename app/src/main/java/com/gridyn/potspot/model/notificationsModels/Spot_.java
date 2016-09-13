
package com.gridyn.potspot.model.notificationsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 
public class Spot_ {

    @SerializedName("_id")
    @Expose
    private Id___ id;
    @SerializedName("data")
    @Expose
    private Data___ data;
    @SerializedName("system")
    @Expose
    private System___ system;

    /**
     * 
     * @return
     *     The id
     */
    public Id___ getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The _id
     */
    public void setId(Id___ id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The data
     */
    public Data___ getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data___ data) {
        this.data = data;
    }

    /**
     * 
     * @return
     *     The system
     */
    public System___ getSystem() {
        return system;
    }

    /**
     * 
     * @param system
     *     The system
     */
    public void setSystem(System___ system) {
        this.system = system;
    }

}
