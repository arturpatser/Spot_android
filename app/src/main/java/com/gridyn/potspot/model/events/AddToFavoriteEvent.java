package com.gridyn.potspot.model.events;

/**
 * Created by dmytro_vodnik on 9/11/16.
 * working on potspot project
 */
public class AddToFavoriteEvent {
    public boolean isFavorite() {
        return isFavorite;
    }

    public String getSpotId() {
        return spotId;
    }

    private final boolean isFavorite;
    private final String spotId;

    public AddToFavoriteEvent(boolean b, String $id) {

        this.isFavorite = b;
        this.spotId = $id;
    }

    @Override
    public String toString() {
        return "AddToFavoriteEvent{" +
                "isFavorite=" + isFavorite +
                ", spotId='" + spotId + '\'' +
                '}';
    }
}
