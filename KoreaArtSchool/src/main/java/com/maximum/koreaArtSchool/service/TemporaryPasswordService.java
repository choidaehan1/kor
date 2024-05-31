package com.maximum.koreaArtSchool.service;

import com.maximum.koreaArtSchool.entity.UserAcct;
import com.maximum.koreaArtSchool.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TemporaryPasswordService {

    private final UserService userService;
    private final JavaMailSender javaMailSender;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;

    public void resetPassword(String email) {
        Optional<UserAcct> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            UserAcct user = userOptional.get();
            // 임시 비밀번호 생성
            String temporaryPassword = generateTemporaryPassword();
            // 사용자의 비밀번호를 임시 비밀번호로 업데이트 (해싱)
            user.setPswd(bCryptPasswordEncoder.encode(temporaryPassword));
            userService.updateUser(user);
            // 사용자를 로그인 상태로 만들기
            loginWithTemporaryPassword(user.getEmail(), temporaryPassword);
            // 이메일로 임시 비밀번호 전송
            sendTemporaryPasswordByEmail(email, temporaryPassword);
        } else {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
    }

    // 임시 비밀번호 생성 메서드 (10자 이상의 무작위 비밀번호 생성)
    private String generateTemporaryPassword() {
        int length = 12; // 임시 비밀번호 길이 설정
        String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_";
        StringBuilder temporaryPassword = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(charset.length());
            temporaryPassword.append(charset.charAt(randomIndex));
        }
        return temporaryPassword.toString();
    }

    // 임시 비밀번호를 사용자에게 이메일로 전송하는 메서드
    private void sendTemporaryPasswordByEmail(String email, String temporaryPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Temporary Password for Password Reset");
        message.setText("Your temporary password is: " + temporaryPassword);
        try {
            javaMailSender.send(message);
            System.out.println("Email sent successfully to " + email);
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }

    // 사용자를 임시 비밀번호로 로그인 상태로 만드는 메서드
    private void loginWithTemporaryPassword(String email, String temporaryPassword) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, temporaryPassword);
        authentication = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}