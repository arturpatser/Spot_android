
package com.gridyn.potspot.response.friendResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gridyn.potspot.model.FriendModel;

public class Message {

    @SerializedName("_id")
    @Expose
    private Id id;
    @SerializedName("data")
    @Expose
    private FriendModel data;
    @SerializedName("system")
    @Expose
    private System system;

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
    public FriendModel getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(FriendModel data) {
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

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", data=" + data +
                ", system=" + system +
                '}';
    }
}
