package com.maximum.koreaArtSchool.repository;

import com.maximum.koreaArtSchool.entity.UserAcct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserAcctRepository extends JpaRepository<UserAcct, Integer> {
    Optional<UserAcct> findByEmail(String email);
    UserAcct findByBirthdateAndPhoneNumber(LocalDate birthdate, String phoneNumber);
    UserAcct findByEmailAndBirthdateAndPhoneNumber(String email, LocalDate birthdate, String phoneNumber);
}
