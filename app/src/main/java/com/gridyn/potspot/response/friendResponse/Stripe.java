
package com.gridyn.potspot.response.friendResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Stripe {

    @SerializedName("customer_token")
    @Expose
    private Boolean customerToken;
    @SerializedName("host_token")
    @Expose
    private Boolean hostToken;

    /**
     * 
     * @return
     *     The customerToken
     */
    public Boolean getCustomerToken() {
        return customerToken;
    }

    /**
     * 
     * @param customerToken
     *     The customer_token
     */
    public void setCustomerToken(Boolean customerToken) {
        this.customerToken = customerToken;
    }

    /**
     * 
     * @return
     *     The hostToken
     */
    public Boolean getHostToken() {
        return hostToken;
    }

    /**
     * 
     * @param hostToken
     *     The host_token
     */
    public void setHostToken(Boolean hostToken) {
        this.hostToken = hostToken;
    }
}
