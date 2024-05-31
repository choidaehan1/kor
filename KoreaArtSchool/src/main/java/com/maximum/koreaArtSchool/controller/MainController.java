package com.maximum.koreaArtSchool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // "/" URL에 대한 GET 요청을 처리하는 메서드
    // 기본 홈 페이지로 이동함
    @GetMapping("/")
    public String home() {
        return "main";
    }

}