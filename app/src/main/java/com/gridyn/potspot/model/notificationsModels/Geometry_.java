
package com.gridyn.potspot.model.notificationsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 
public class Geometry_ {

    @SerializedName("bounds")
    @Expose
    private Bounds bounds;
    @SerializedName("location")
    @Expose
    private Location_ location;
    @SerializedName("location_type")
    @Expose
    private String locationType;
    @SerializedName("viewport")
    @Expose
    private Viewport_ viewport;

    /**
     * 
     * @return
     *     The bounds
     */
    public Bounds getBounds() {
        return bounds;
    }

    /**
     * 
     * @param bounds
     *     The bounds
     */
    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    /**
     * 
     * @return
     *     The location
     */
    public Location_ getLocation() {
        return location;
    }

    /**
     * 
     * @param location
     *     The location
     */
    public void setLocation(Location_ location) {
        this.location = location;
    }

    /**
     * 
     * @return
     *     The locationType
     */
    public String getLocationType() {
        return locationType;
    }

    /**
     * 
     * @param locationType
     *     The location_type
     */
    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    /**
     * 
     * @return
     *     The viewport
     */
    public Viewport_ getViewport() {
        return viewport;
    }

    /**
     * 
     * @param viewport
     *     The viewport
     */
    public void setViewport(Viewport_ viewport) {
        this.viewport = viewport;
    }

}
