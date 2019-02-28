package com.example.notification.helpers;


import org.springframework.web.bind.annotation.ResponseStatus;

public class NotificationException extends RuntimeException {


    public NotificationException(String message)
    {
        super(message);
    }
}
