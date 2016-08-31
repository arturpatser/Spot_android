
package com.gridyn.potspot.response.friendResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FriendsResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private List<List<Message>> message = new ArrayList<List<Message>>();

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
    public List<List<Message>> getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(List<List<Message>> message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "FriendsResponse{" +
                "success=" + success +
                ", message=" + message +
                '}';
    }
}
