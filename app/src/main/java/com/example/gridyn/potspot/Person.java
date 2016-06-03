package com.example.gridyn.potspot;

public class Person {
    private static Person instance;
    private static boolean host;

    private String name;

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

    public static void setHost() {
        host = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
