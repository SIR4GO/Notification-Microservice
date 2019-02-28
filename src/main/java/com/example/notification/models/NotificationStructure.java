package com.example.notification.models;


import java.util.ArrayList;
import java.util.List;

public class NotificationStructure {


    private String message;
    private String from;
    private List<Receiver> receivers = new ArrayList<Receiver>();


    public NotificationStructure(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<Receiver> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<Receiver> receivers) {
        this.receivers = receivers;
    }

    @Override
    public String toString() {
        return "NotificationStructure{" +
                "message='" + message + '\'' +
                ", from='" + from + '\'' +
                ", receivers=" + receivers +
                '}';
    }

}
