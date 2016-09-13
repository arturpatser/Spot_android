package com.gridyn.potspot.model;

public class Message {
    private String fromName, spotName, message, id, imgUser, imgSpot, date;
    private boolean isSelf;

    public Message() {/*NOP*/}

    public Message(String id, String message, String date) {
        this.id = id;
        this.message = message;
        this.date = date;
    }

    public Message(String fromName, String message, boolean isSelf) {
        this.fromName = fromName;
        this.message = message;
        this.isSelf = isSelf;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean isSelf) {
        this.isSelf = isSelf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUser() {
        return imgUser;
    }

    public void setImgUser(String imgUser) {
        this.imgUser = imgUser;
    }

    public String getImgSpot() {
        return imgSpot;
    }

    public void setImgSpot(String imgSpot) {
        this.imgSpot = imgSpot;
    }

    public String getDate() {
        return date;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public void setDate(String date) {
        this.date = date;
    }
}