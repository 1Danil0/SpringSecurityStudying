package com.danilermolenko.boot.controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class BrowserController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/successLogin")
    public String log(){
        return "successLogin";
    }

}
