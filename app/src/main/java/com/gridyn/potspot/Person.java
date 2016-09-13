package com.gridyn.potspot;

import java.util.HashMap;
import java.util.Map;

public final class Person {
    private static Person instance;
    private static boolean host;
    private static String token;
    private static String id;
    private static String androidId;

    private static String name;
    private static String email;
    private static String avatar;

    public static String getName() {
        return name;
    }

    public static String getEmail() {
        return email;
    }

    public static String getAvatar() {
        return avatar;
    }

    private Person() {
    }

    public static Person getInstance() {
        if (instance == null) {
            synchronized (Person.class) {
                if (instance == null) {
                    instance = new Person();
                }
            }
        }
        return instance;
    }

    public static boolean isHost() {
        return host;
    }

    public static void setHost(boolean host) {
        Person.host = host;
    }

    public static String getToken() {
        return token;
    }

    public static Map<String, String> getTokenMap() {

        Map<String, String> token = new HashMap<>();

        token.put("token", Person.getToken());

        return token;
    }

    public static void setToken(String token) {
        Person.token = token;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        Person.id = id;
    }

    public static String getAndroidId() {
        return androidId;
    }

    public static void setAndroidId(String androidId) {
        Person.androidId = androidId;
    }

    public static void setName(String name) {
        Person.name = name;
    }

    public static void setEmail(String email) {
        Person.email = email;
    }

    public static void setAvatar(String avatar) {
        Person.avatar = avatar;
    }
}
