package com.example.gridyn.potspot;

public interface Constant {
    int BLUR_RADIUS = 40;

    String FONT_ROBOTO_REGULAR = "fonts/Roboto-Regular.ttf";
    String FONT_ROBOTO_MEDIUM = "fonts/Roboto-Medium.ttf";
    String FONT_ROBOTO_LIGHT = "fonts/Roboto-Light.ttf";
    String BASE_URL = "http://potspot.podkolpakom.net/API/";
    String URL_IMAGE = "http://potspot.podkolpakom.net/imgs/";
    String URL_BASE64 = "data:image/jpeg;base64,";
    String CONNECTION_ERROR = "Connection error";
    String SP = "settings";
    String SP_IS_LOGIN = "isLogin";
    int CAMERA = 0;
    int GALLERY = 1;
    String LOG = "log";
    long UPDATE_LOCATION_SECONDS = 10000;
    float UPDATE_LOCATION_DISTANCE = 10;
}
