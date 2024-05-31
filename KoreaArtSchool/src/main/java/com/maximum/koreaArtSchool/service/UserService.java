package com.maximum.koreaArtSchool.service;

import com.maximum.koreaArtSchool.dto.AddUserRequest;
import com.maximum.koreaArtSchool.entity.UserAcct;
import com.maximum.koreaArtSchool.repository.UserAcctRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserAcctRepository userAcctRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<UserAcct> findByEmail(String email) {
        return userAcctRepository.findByEmail(email);
    }

    public void updateUser(UserAcct user) {
        userAcctRepository.save(user);
    }

    public Integer save(AddUserRequest dto) {
        return userAcctRepository.save(UserAcct.builder()
                .email(dto.getEmail())
                // Password 암호화
                .pswd(bCryptPasswordEncoder.encode(dto.getPswd()))
                .birthdate(dto.getBirthdate())
                .phoneNumber(dto.getPhoneNumber())
                .build()).getUserNo();
    }

    public String findId(LocalDate birthdate, String phoneNumber) {
        UserAcct user = userAcctRepository.findByBirthdateAndPhoneNumber(birthdate, phoneNumber);
        if (user != null) {
            return user.getEmail();
        } else {
            return null; // 일치하는 사용자가 없을 경우
        }
    }

    public String findPswd(String email, LocalDate birthdate, String phoneNumber) {
        UserAcct user = userAcctRepository.findByEmailAndBirthdateAndPhoneNumber(email, birthdate, phoneNumber);
        if (user != null) {
            return user.getEmail();
        } else {
            return null; // 일치하는 사용자가 없을 경우
        }
    }

    // 비밀번호 암호화 메서드 (사용하지 않더라도 참고로 작성)
    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}