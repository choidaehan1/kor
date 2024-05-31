package com.maximum.koreaArtSchool.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Table(name = "USERACCT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
public class UserAcct implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_NO", updatable = false)
    private Integer userNo;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PSWD", nullable = false)
    private String pswd;

    @Column(name = "BIRTH_DATE", nullable = false)
    private LocalDate birthdate;

    @Column(name = "PHONE", nullable = false)
    private String phoneNumber;

    @Builder
    public UserAcct(String email, String pswd, LocalDate birthdate, String phoneNumber) {
        this.email = email;
        this.pswd = pswd;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
    }

    // UserDetails 인터페이스 메서드 구현
    // 권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한을 지정. 여기서는 단일 "ROLE_ADMIN" 권한을 부여.
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    // 사용자의 password 반환
    @Override
    public String getPassword() {
        return pswd;
    }

    // 사용자의 id 반환
    @Override
    public String getUsername() {
        return email;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // password 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // password가 만료되었는지 확인하는 로직
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}