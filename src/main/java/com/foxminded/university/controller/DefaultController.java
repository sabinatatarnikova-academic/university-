package com.foxminded.university.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class DefaultController {

    @GetMapping("/")
    public String getWelcomePage(){
        return "index";
    }
}
