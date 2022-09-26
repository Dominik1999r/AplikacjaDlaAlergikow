package com.cielicki.dominik.allergyapprestapi.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cielicki.dominik.allergyapprestapi.db.Question;

/**
 * Interfejs służący do pracy z tabelą question w bazie danych.
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
