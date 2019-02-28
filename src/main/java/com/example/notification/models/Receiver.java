package com.example.notification.models;


public class Receiver {



    private String username;


    public Receiver(){}

    public Receiver(String userName) {
        this.username = userName;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public String toString() {
        return "Receiver{" +
                "username=" + username +
                '}';
    }
}
