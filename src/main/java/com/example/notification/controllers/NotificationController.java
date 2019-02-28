package com.example.notification.controllers;


import com.example.notification.models.ChatMessage;
import com.example.notification.models.Notification;
import com.example.notification.models.NotificationStructure;
import com.example.notification.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;

@RestController
public class NotificationController {


    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private NotificationService notificationService;


    @CrossOrigin(origins = "*")   //Substitute with system domain
    @RequestMapping(value = "/sendNotification" , method = RequestMethod.POST)
    public void receiveNotification (@RequestBody NotificationStructure notificationStructure) {

          notificationService.sendAndSaveNotification(notificationStructure);
    }



    @CrossOrigin(origins = "*")
    @MessageMapping("/join")
    public void joinMapping(@Payload ChatMessage chatMessage, @Header("simpSessionId") String sessionId , Principal principal) {

       // messagingTemplate.convertAndSendToUser(principal.getName(),"/queue/notify","hello ");

    }

    @CrossOrigin(origins = "*")
    @MessageMapping("/ack")
    public void receiveAcknowledge(@Payload Notification notification, @Header("simpSessionId") String sessionId , Principal principal) {
        //  acknowledge Received if notification had sent successfully to user
        notificationService.updateNotificationState(notification);
    }


    @RequestMapping(value = "/sendToUser" , method = RequestMethod.GET)
    public String sendMessage(@RequestParam("message") String message , @RequestParam("UID") String UID)
    {
        messagingTemplate.convertAndSendToUser(UID,"/queue/notify",message);
        return "Message had sent";
    }


    @RequestMapping(value = "/broadcast" , method = RequestMethod.GET)
    public String broadcastMessage(@RequestParam("message") String message)
    {
        messagingTemplate.convertAndSend("/queue/broadcast", message);
        return "Message had sent To All";
    }


}
