package com.example.jpa.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome() {
        log.info("home 요청");
        return "home";
    }

    @GetMapping("/main")
    public void getMain() {
        log.info("main 요청");
    }

}
