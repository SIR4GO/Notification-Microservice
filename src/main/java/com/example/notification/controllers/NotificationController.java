package com.example.notification.controllers;


import com.example.notification.Repositories.NotificationCriteria;
import com.example.notification.helpers.NotificationValidation;
import com.example.notification.helpers.ResponseMessage;
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
import java.security.Principal;
import java.util.List;


@RestController
public class NotificationController {


    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationCriteria notificationCriteria;


    @CrossOrigin(origins = "*")   //Substitute with system domain
    @RequestMapping(value = "/sendNotification" , method = RequestMethod.POST)
    public ResponseMessage receiveNotification (@RequestBody NotificationStructure notificationStructure) {

        if(NotificationValidation.validateNotificationStructure(notificationStructure))
             notificationService.sendAndSaveNotification(notificationStructure);

        return new ResponseMessage("Notification received successfully");
    }


    @CrossOrigin(origins = "*")
    @MessageMapping("/ack")
    public void receiveAcknowledge(@Payload Notification notification) {
        //  acknowledge would be  Received if notification had sent successfully to user
        notificationService.updateNotificationState(notification);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/getNotifications/{content}/{state}")
    public List<Notification> getNotifications(@PathVariable String content, @PathVariable String state) {

        // notificationCriteria.getNotificationByContent(content , state);
        return notificationService.getByContentAndState(content,state);
    }





    @CrossOrigin(origins = "*")
    @MessageMapping("/join")
    public void joinMapping(@Payload ChatMessage chatMessage, @Header("simpSessionId") String sessionId , Principal principal) {

       // messagingTemplate.convertAndSendToUser(principal.getName(),"/queue/notify","hello ");

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
