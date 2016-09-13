
package com.gridyn.potspot.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class NotificationModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private List<List<com.gridyn.potspot.model.notificationsModels.Message>> message = new ArrayList<List<com.gridyn.potspot.model.notificationsModels.Message>>();

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
     *     The message
     */
    public List<List<com.gridyn.potspot.model.notificationsModels.Message>> getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(List<List<com.gridyn.potspot.model.notificationsModels.Message>> message) {
        this.message = message;
    }

}
