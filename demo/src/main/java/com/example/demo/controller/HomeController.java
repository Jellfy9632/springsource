package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
public class HomeController {

    // http://localhost:8080/
    @GetMapping("/")
    public String getHome() {
        log.info("home 요청");
        return "home";
    }

    @GetMapping("/basic")
    public String getinfo() {
        return "info";
    }

    @PostMapping("/basic")
    public String postadd(@ModelAttribute("num1") int num1, @ModelAttribute("num2") int num2, Model model) {
        log.info("덧셈요청{},{}", num1, num2);
        // 덧셈 결과를 info 로 전송
        int result = num1 + num2;
        // name : 화면단에서 불러서 사용
        // model.addAttribute ("name", value);
        // model.addAttribute("num1", num1);
        // model.addAttribute("num2", num2);
        model.addAttribute("result", result);

        return "info";
    }

}
