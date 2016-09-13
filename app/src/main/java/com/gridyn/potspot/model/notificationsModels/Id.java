
package com.gridyn.potspot.model.notificationsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 
public class Id {

    @SerializedName("$id")
    @Expose
    private String $id;

    /**
     * 
     * @return
     *     The $id
     */
    public String get$id() {
        return $id;
    }

    /**
     * 
     * @param $id
     *     The $id
     */
    public void set$id(String $id) {
        this.$id = $id;
    }

}
