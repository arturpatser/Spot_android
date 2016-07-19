package com.gridyn.potspot;

import com.google.android.gms.maps.model.LatLng;

public class Spot {

    private String id;
    private String title;
    private Integer price;
    private String typeListing;
    private String image;
    private String name;
    private String description;
    private String address;
    private LatLng latLng;

    public Spot(String image) {
        this.image = image;
    }

    public Spot(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Spot(String title, Integer price, String typeListing, String image) {
        this.title = title;
        this.price = price;
        this.typeListing = typeListing;
        this.image = image;
    }

    public Spot(String image, Integer price, String description, String typeListing, String address) {
        this.image = image;
        this.price = price;
        this.typeListing = description;
        this.title = typeListing;
        this.address = address;
    }

    public Spot(String title, Integer price, String typeListing, String image, float lat, float lng) {
        this.title = title;
        this.price = price;
        this.typeListing = typeListing;
        this.image = image;
        latLng = new LatLng(lat, lng);

    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTypeListing() {
        return typeListing;
    }

    public void setTypeListing(String typeListing) {
        this.typeListing = typeListing;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public String getId() {
        return id;
    }
}
