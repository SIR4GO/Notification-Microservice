package com.example.notification.models;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification_log")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String sender;
    private String message;
    private String receiver; // sender user name
    private String sendingState;

    @Column
    @CreationTimestamp
    private LocalDateTime createDateTime;

    @Column
    @UpdateTimestamp
    private LocalDateTime updateDateTime;



    public Notification(){}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendingState() {
        return sendingState;
    }

    public void setSendingState(String sendingState) {
        this.sendingState = sendingState;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
