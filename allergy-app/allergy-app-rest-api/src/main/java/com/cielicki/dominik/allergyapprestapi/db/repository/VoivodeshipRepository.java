package com.cielicki.dominik.allergyapprestapi.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cielicki.dominik.allergyapprestapi.db.Voivodeship;

/**
 * Interfejs służący do pracy z tabelą voivodeship w bazie danych.
 */
public interface VoivodeshipRepository extends JpaRepository<Voivodeship, Long> {

}
