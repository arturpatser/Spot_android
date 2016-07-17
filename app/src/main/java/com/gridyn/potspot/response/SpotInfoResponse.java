package com.gridyn.potspot.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SpotInfoResponse {
    @SerializedName("success")
    @Expose
    public boolean success;
    @SerializedName("message")
    @Expose
    public List<Message> message = new ArrayList<>();

    public static class Message {

        @SerializedName("spots")
        @Expose
        public List<Spot> spots = new ArrayList<>();

    }

    public static class Spot {

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
        /*@SerializedName("googlemapsapi")
        @Expose
        public Googlemapsapi googlemapsapi;*/
        @SerializedName("imgs")
        @Expose
        public List<String> imgs = new ArrayList<>();
        @SerializedName("user_imgs")
        @Expose
        public List<String> userImgs = new ArrayList<>();
        @SerializedName("username")
        @Expose
        public String username;
/*            public class Googlemapsapi {

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

            public class AddressComponent {

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

            public class Bounds {

                @SerializedName("northeast")
                @Expose
                public Northeast northeast;
                @SerializedName("southwest")
                @Expose
                public Southwest southwest;

            }

            public class Geometry {

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

            public class Location {

                @SerializedName("lat")
                @Expose
                public float lat;
                @SerializedName("lng")
                @Expose
                public float lng;

            }

            public class Northeast {

                @SerializedName("lat")
                @Expose
                public float lat;
                @SerializedName("lng")
                @Expose
                public float lng;

            }

            public class Northeast_ {

                @SerializedName("lat")
                @Expose
                public float lat;
                @SerializedName("lng")
                @Expose
                public float lng;

            }

            public class Southwest {

                @SerializedName("lat")
                @Expose
                public float lat;
                @SerializedName("lng")
                @Expose
                public float lng;

            }

            public class Southwest_ {

                @SerializedName("lat")
                @Expose
                public float lat;
                @SerializedName("lng")
                @Expose
                public float lng;

            }

            public class Viewport {

                @SerializedName("northeast")
                @Expose
                public Northeast_ northeast;
                @SerializedName("southwest")
                @Expose
                public Southwest_ southwest;

            }*/
    }
}
