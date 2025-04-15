package com.example.demo.controller;

import java.lang.reflect.Member;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping("/register")
    public String postRegister(@ModelAttribute("mDTO") MemberDTO memberDTO, RedirectAttributes rttr) {

        log.info("회원가입 요청 {}", memberDTO);

        // 로그인 페이지로 이동
        // redirect 방식으로 가면서 값을 보내고 싶다면?
        rttr.addAttribute("userid", memberDTO.getUserid());
        rttr.addFlashAttribute("password", memberDTO.getPassword());
        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public void getLogin() {
        log.info("로그인");
        // template 찾기
    }

    // @PostMapping("/login")
    // public void postLogin(String userid, String password) {

    // log.info("로그인요청 {}, {}}", userid, password);
    // // template 찾기
    // }

    // @PostMapping("/login")
    // public void postLogin(LoginDTO loginDTO) {

    // log.info("로그인요청 {}, {}}", loginDTO.getUserid(), loginDTO.getPassword());
    // // template 찾기
    // }

    @PostMapping("/login")
    public void postLogin(HttpServletRequest request) {
        // HttpServletRequest : Request객체 - 사용자의 정보 및 서버 쪽 정보 추출

        String userid = request.getParameter("userid");
        String password = request.getParameter("password");
        String remote = request.getRemoteAddr();
        String local = request.getLocalAddr();

        log.info("로그인요청 {}, {}}", userid, password);
        log.info("클라이언트 정보 {}, {}}", remote, local);
        // template 찾기
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
