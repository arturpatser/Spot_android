
package com.gridyn.potspot.model.notificationsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 
public class User {

    @SerializedName("_id")
    @Expose
    private Id_ id;
    @SerializedName("data")
    @Expose
    private Data_ data;
    @SerializedName("system")
    @Expose
    private System__ system;

    /**
     * 
     * @return
     *     The id
     */
    public Id_ getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The _id
     */
    public void setId(Id_ id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The data
     */
    public Data_ getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data_ data) {
        this.data = data;
    }

    /**
     * 
     * @return
     *     The system
     */
    public System__ getSystem() {
        return system;
    }

    /**
     * 
     * @param system
     *     The system
     */
    public void setSystem(System__ system) {
        this.system = system;
    }

}
