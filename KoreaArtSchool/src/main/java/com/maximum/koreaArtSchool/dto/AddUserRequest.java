package com.maximum.koreaArtSchool.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AddUserRequest {
    private String email;
    private String pswd;
    private LocalDate birthdate;
    private String phoneNumber;
}
