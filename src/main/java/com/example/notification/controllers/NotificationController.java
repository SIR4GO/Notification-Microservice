package com.example.notification.controllers;


import com.example.notification.models.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class NotificationController {


    @Autowired
    private SimpMessageSendingOperations messagingTemplate;


    @CrossOrigin(origins = "*")
    @MessageMapping("/join") // for send  => /app/chat
    public void joinMapping(@Payload ChatMessage chatMessage, @Header("simpSessionId") String sessionId , Principal principal) {

        messagingTemplate.convertAndSendToUser(principal.getName(),"/queue/notify","hello ");

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
