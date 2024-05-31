package com.maximum.koreaArtSchool.service;

import com.maximum.koreaArtSchool.entity.UserAcct;
import com.maximum.koreaArtSchool.repository.UserAcctRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AdminDetailsService implements UserDetailsService {
    private final UserAcctRepository userAcctRepository;

    // AdminId로 사용자 정보를 가져오는 메서드
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAcct user = userAcctRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new User(user.getEmail(), user.getPswd(), getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(UserAcct user) {
        return user.getEmail().equals("admin")
                ? Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
                : Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }
}