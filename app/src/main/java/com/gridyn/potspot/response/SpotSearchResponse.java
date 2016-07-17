package com.gridyn.potspot.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SpotSearchResponse {

    @SerializedName("success")
    @Expose
    public boolean success;

    @SerializedName("message")
    @Expose
    public List<Message> message = new ArrayList<>();

    public class Message {

        @SerializedName("spots")
        @Expose
        public List<Spots> spots = new ArrayList<>();
    }

    public static class Spots {

        @SerializedName("_id")
        @Expose
        public Id id;
        @SerializedName("data")
        @Expose
        public Data data;

/*        @SerializedName("system")
        @Expose
        public System system;*/
    }

    public static class Id {

        @SerializedName("$id")
        @Expose
        public String $id;

    }

    public static class Data {

        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("price")
        @Expose
        public int price;
        @SerializedName("maxGuests")
        @Expose
        public String maxGuests;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("about")
        @Expose
        public String about;
        @SerializedName("time")
        @Expose
        public String time;
        @SerializedName("days")
        @Expose
        public String days;
        @SerializedName("badge")
        @Expose
        public String badge;
        @SerializedName("imgs")
        @Expose
        public List<String> imgs = new ArrayList<>();
        @SerializedName("user_imgs")
        @Expose
        public List<String> userImgs = new ArrayList<>();

        @SerializedName("googlemapsapi")
        @Expose
        public Googlemapsapi googlemapsapi;
    }

    public static class System {

        @SerializedName("owner")
        @Expose
        public String owner;
        @SerializedName("isVerified")
        @Expose
        public boolean isVerified;
        @SerializedName("TimeVerified")
        @Expose
        public int timeVerified;
        @SerializedName("TimeCreated")
        @Expose
        public int timeCreated;
        @SerializedName("TimeUpdated")
        @Expose
        public int timeUpdated;
        @SerializedName("IP_Created")
        @Expose
        public String iPCreated;
        @SerializedName("IP_Updated")
        @Expose
        public String iPUpdated;

    }

    public static class AddressComponent {

        @SerializedName("long_name")
        @Expose
        public String longName;
        @SerializedName("short_name")
        @Expose
        public String shortName;
        @SerializedName("types")
        @Expose
        public List<String> types = new ArrayList<String>();

    }

    public static class Bounds {

        @SerializedName("northeast")
        @Expose
        public Northeast northeast;
        @SerializedName("southwest")
        @Expose
        public Southwest southwest;

    }

    public static class Geometry {

        @SerializedName("bounds")
        @Expose
        public Bounds bounds;
        @SerializedName("location")
        @Expose
        public Location location;
        @SerializedName("location_type")
        @Expose
        public String locationType;
        @SerializedName("viewport")
        @Expose
        public Viewport viewport;

    }

    public static class Googlemapsapi {

        @SerializedName("address_components")
        @Expose
        public List<AddressComponent> addressComponents = new ArrayList<AddressComponent>();
        @SerializedName("formatted_address")
        @Expose
        public String formattedAddress;
        @SerializedName("geometry")
        @Expose
        public Geometry geometry;
        @SerializedName("partial_match")
        @Expose
        public boolean partialMatch;
        @SerializedName("place_id")
        @Expose
        public String placeId;
        @SerializedName("types")
        @Expose
        public List<String> types = new ArrayList<String>();

    }

    public static class Location {
        @SerializedName("lat")
        @Expose
        public float lat;
        @SerializedName("lng")
        @Expose
        public float lng;
    }

    public static class Northeast {

        @SerializedName("lat")
        @Expose
        public float lat;
        @SerializedName("lng")
        @Expose
        public float lng;
    }

    public static class Northeast_ {

        @SerializedName("lat")
        @Expose
        public float lat;
        @SerializedName("lng")
        @Expose
        public float lng;
    }

    public static class Southwest {

        @SerializedName("lat")
        @Expose
        public float lat;
        @SerializedName("lng")
        @Expose
        public float lng;
    }

    public static class Southwest_ {

        @SerializedName("lat")
        @Expose
        public float lat;
        @SerializedName("lng")
        @Expose
        public float lng;
    }

    public static class Viewport {

        @SerializedName("northeast")
        @Expose
        public Northeast_ northeast;
        @SerializedName("southwest")
        @Expose
        public Southwest_ southwest;

    }
}

