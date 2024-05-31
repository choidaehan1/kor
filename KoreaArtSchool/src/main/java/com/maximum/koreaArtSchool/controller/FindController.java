package com.maximum.koreaArtSchool.controller;

import com.maximum.koreaArtSchool.service.TemporaryPasswordService;
import com.maximum.koreaArtSchool.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/find")
public class FindController {

    private final UserService userService;
    private final TemporaryPasswordService temporaryPasswordService;

    @GetMapping("/id")
    public String showFindId() {
        return "find-id";
    }

    @PostMapping("/id")
    public String findId(@RequestParam("birthdate") LocalDate birthdate,
                         @RequestParam("phoneNumber") String phoneNumber,
                         Model model) {
        String foundId = userService.findId(birthdate, phoneNumber);
        if (foundId != null) {
            model.addAttribute("foundId", foundId);
        } else {
            model.addAttribute("errorMessage", "일치하는 사용자가 없습니다.");
        }
        return "find-id";
    }

    @GetMapping("/password")
    public String showFindPswd() {
        return "find-password";
    }

    @PostMapping("/password")
    public String findPswd(
            @RequestParam("email") String email,
            @RequestParam("birthdate") LocalDate birthdate,
            @RequestParam("phoneNumber") String phoneNumber,
            Model model) {
        System.out.println("Received email: " + email);
        System.out.println("Received birthdate: " + birthdate);
        System.out.println("Received phone number: " + phoneNumber);

        String foundPswd = userService.findPswd(email, birthdate, phoneNumber);
        if (foundPswd != null) {
            temporaryPasswordService.resetPassword(email);
            model.addAttribute("successMessage", "임시 비밀번호가 이메일로 전송되었습니다.");
        } else {
            model.addAttribute("errorMessage", "일치하는 사용자가 없습니다.");
        }
        return "find-password";
    }
}
