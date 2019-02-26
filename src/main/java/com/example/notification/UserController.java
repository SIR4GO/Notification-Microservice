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



}
