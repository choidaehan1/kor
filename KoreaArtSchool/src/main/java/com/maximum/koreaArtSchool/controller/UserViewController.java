package com.maximum.koreaArtSchool.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserViewController {

    // 로그인 페이지를 보여주는 GET 요청 핸들러
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model, HttpServletResponse response) {
        if (error != null) {
            model.addAttribute("errorMessage", "아이디 또는 비밀번호가 잘못되었습니다.");
        }
        // 캐시 제어 헤더 추가
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        return "login";
    }

    // 회원 가입 페이지를 보여주는 GET 요청 핸들러
    @GetMapping("/signup")
    public String signup(HttpServletResponse response) {
        // 캐시 제어 헤더 추가
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        return "signup";
    }
}