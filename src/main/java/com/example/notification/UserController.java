package com.example.notification;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class UserController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @GetMapping(path = "/customers")
    public String customers(Principal principal) {

        return "Hello " + principal.getName()+"<br><br> "+"<a href='/index2.html'>Chat room </a> "+"<br><br> " + "<a href='/logout'>Logout</a>";

    }

    @RequestMapping(value = "/fire" , method = RequestMethod.GET)
    public String sendMessage(@RequestParam("message") String message , @RequestParam("SSID") String SSID)
    {
//        System.out.println(principal.getName());
//        messagingTemplate.convertAndSend("/queue/reply-"+SSID, message);

        messagingTemplate.convertAndSendToUser(SSID,"/queue/notify",message);

        return "Message had sent";
    }


    @RequestMapping(value = "/broadcast" , method = RequestMethod.GET)
    public String broadcastMessage(@RequestParam("message") String message)
    {
        //System.out.println(principal.getName());
        //String sessionId = readFromFile();
        messagingTemplate.convertAndSend("/queue/role", message);

        return "Message had sent To All";
    }

}
