package com.gridyn.potspot.model.events;

/**
 * Created by dmytro_vodnik on 8/17/16.
 * working on potspot project
 */
public class VerifyPhoneEvent {

    public VerifyPhoneEvent(boolean verified, String concat) {
        this.verified = verified;
        this.phone = concat;
    }

    public boolean verified;
    public String phone;
}
