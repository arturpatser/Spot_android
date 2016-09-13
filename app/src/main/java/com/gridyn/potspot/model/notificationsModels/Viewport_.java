
package com.gridyn.potspot.model.notificationsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 
public class Viewport_ {

    @SerializedName("northeast")
    @Expose
    private Northeast__ northeast;
    @SerializedName("southwest")
    @Expose
    private Southwest__ southwest;

    /**
     * 
     * @return
     *     The northeast
     */
    public Northeast__ getNortheast() {
        return northeast;
    }

    /**
     * 
     * @param northeast
     *     The northeast
     */
    public void setNortheast(Northeast__ northeast) {
        this.northeast = northeast;
    }

    /**
     * 
     * @return
     *     The southwest
     */
    public Southwest__ getSouthwest() {
        return southwest;
    }

    /**
     * 
     * @param southwest
     *     The southwest
     */
    public void setSouthwest(Southwest__ southwest) {
        this.southwest = southwest;
    }

}
