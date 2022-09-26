package com.cielicki.dominik.allergyapprestapi.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cielicki.dominik.allergyapprestapi.db.Setting;

/**
 * Interfejs służący do pracy z tabelą setting w bazie danych.
 */
public interface SettingRepository extends JpaRepository<Setting, Long> {

}
