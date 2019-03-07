package com.example.notification.controllers;


import com.example.notification.helpers.NotificationValidation;
import com.example.notification.helpers.ResponseMessage;
import com.example.notification.models.ChatMessage;
import com.example.notification.models.Notification;
import com.example.notification.models.NotificationStructure;
import com.example.notification.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;
import java.security.Principal;


@SuppressWarnings("ALL")
@RestController
public class NotificationController {


    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private NotificationService notificationService;



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
    public Page<Notification> getNotifications(@PathVariable() String content,
                                               @PathVariable()  String state ,
                                               @RequestParam @NotNull  Integer page ,
                                               @RequestParam @NotNull  Integer pageSize ,
                                               @RequestParam @NotNull  String sortingAttrib,
                                               @RequestParam(defaultValue = "", required = false) String from,
                                               @RequestParam(defaultValue = "", required = false) String to,
                                               Principal principal) {


        //user name
        System.out.println(principal.getName());

        if(!from.isEmpty() && to.isEmpty())
            return notificationService.getByContentAndStateFromDateAndTime(content , state , page , pageSize ,sortingAttrib,from);
        else if(from.isEmpty() && !to.isEmpty())
            return notificationService.getByContentAndStateToDateAndTime(content ,state , page , pageSize ,sortingAttrib,to);
        else if(!from.isEmpty() && !to.isEmpty())
            return notificationService.getByContentAndStateFromAndToDateAndTime(content ,state , page , pageSize ,sortingAttrib,from,to);


        return notificationService.getByContentAndState(content ,state , page , pageSize ,sortingAttrib);

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
