package com.example.gridyn.potspot;

public final class Person {
    private static Person instance;
    private static boolean host;
    private static String token;
    private static String id;
    private static String memberSince;

    private Person() {}

    public static Person getInstance() {
        if(instance == null) {
            synchronized (Person.class) {
                if(instance == null) {
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

    public static void setToken(String token) {
        Person.token = token;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        Person.id = id;
    }

    public static void setInstance(Person instance) {
        Person.instance = instance;
    }

    public static String getMemberSince() {
        return memberSince;
    }

    public static void setMemberSince(String memberSince) {
        Person.memberSince = memberSince;
    }
}
