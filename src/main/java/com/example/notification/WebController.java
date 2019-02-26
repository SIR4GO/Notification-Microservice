package com.example.notification;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class WebController {


    @GetMapping(path = "/")
    public String index() {
        return "external";
    }


    @GetMapping(path = "/logout")
    public void logout(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
         request.logout();
         response.sendRedirect("/");
    }


}
