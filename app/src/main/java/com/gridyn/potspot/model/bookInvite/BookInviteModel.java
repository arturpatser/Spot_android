
package com.gridyn.potspot.model.bookInvite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookInviteModel {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("type")
    @Expose
    private String type;

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
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

}
