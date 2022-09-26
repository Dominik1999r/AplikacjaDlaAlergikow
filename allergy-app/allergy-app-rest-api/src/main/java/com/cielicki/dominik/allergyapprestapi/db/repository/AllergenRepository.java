package com.cielicki.dominik.allergyapprestapi.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cielicki.dominik.allergyapprestapi.db.Allergen;

/**
 * Interfejs służący do pracy z tabelą allergen w bazie danych.
 */
public interface AllergenRepository extends JpaRepository<Allergen, Long> {

}
