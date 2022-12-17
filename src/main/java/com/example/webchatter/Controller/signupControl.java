package com.example.webchatter.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping
public class signupControl {
    @GetMapping("singin")
    public String singin(){
        return "singin.html";
    }
    @GetMapping("signup")
    public String ds(){
        return "signup12.html";
    }
}
