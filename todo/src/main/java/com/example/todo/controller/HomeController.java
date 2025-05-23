package com.example.todo.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.todo.service.TodoService;

@Log4j2
@Controller
public class HomeController {
    @GetMapping("/")
    public String getHome() {
        return "home";
    }

}
