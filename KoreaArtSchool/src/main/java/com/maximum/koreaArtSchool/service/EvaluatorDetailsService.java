package com.maximum.koreaArtSchool.service;

import com.maximum.koreaArtSchool.entity.Evaluator;
import com.maximum.koreaArtSchool.repository.EvaluatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class EvaluatorDetailsService implements UserDetailsService {

    @Autowired
    private final EvaluatorRepository evaluatorRepository;

    public EvaluatorDetailsService(EvaluatorRepository evaluatorRepository) {
        this.evaluatorRepository = evaluatorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String evl_eml) throws UsernameNotFoundException {
        Evaluator evaluator = evaluatorRepository.findByEvlEml(evl_eml)
                .orElseThrow(() -> new UsernameNotFoundException("Evaluator not found with email: " + evl_eml));
        return new User(evaluator.getEvlEml(), evaluator.getPwd(), Collections.singleton(new SimpleGrantedAuthority("ROLE_EVALUATOR")));
    }

}