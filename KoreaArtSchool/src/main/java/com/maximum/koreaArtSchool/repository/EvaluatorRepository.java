package com.maximum.koreaArtSchool.repository;

import com.maximum.koreaArtSchool.entity.Evaluator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluatorRepository extends JpaRepository<Evaluator, Integer> {
    Optional<Evaluator> findByEvlEml(String evlEml);

    List<Evaluator> findAll ();
}