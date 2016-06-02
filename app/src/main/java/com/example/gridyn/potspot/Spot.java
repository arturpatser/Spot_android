package com.example.gridyn.potspot;

public class Spot {

    private String title;
    private Integer price;
    private String typeListing;
    private String image;

    public Spot(String title, Integer price, String typeListing, String image) {
        this.title = title;
        this.price = price;
        this.typeListing = typeListing;
        this.image = image;
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
}
