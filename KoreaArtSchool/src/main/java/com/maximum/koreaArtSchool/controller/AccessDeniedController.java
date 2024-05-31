package com.maximum.koreaArtSchool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDeniedController {

    // 사용자가 접근 권한이 없는 페이지에 접근할 때 호출됨
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}