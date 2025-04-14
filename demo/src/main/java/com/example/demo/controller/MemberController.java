package com.example.demo.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
@RequestMapping("/member")

public class MemberController {
    // 회원가입 : /member/register
    // 로그인 : /member/login
    // 로그아웃 : /member/logoout
    // 비밀번호 변경: /member/change

    // http://localhost:8080/mamber/register
    // void : templater/member/register.html

    @GetMapping("/register")
    public void getRegister() {
        log.info("회원가입");
    }

    @GetMapping("/login")
    public void getLogin() {
        log.info("로그인");
    }

    @GetMapping("/logout")
    public void getLogout() {
        log.info("로그아웃");
    }

    @GetMapping("/change")
    public void getChange() {
        log.info("비밀번호변경");
    }

}
