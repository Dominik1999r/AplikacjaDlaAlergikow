package com.cielicki.dominik.allergyapprestapi.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cielicki.dominik.allergyapprestapi.db.SettingCategory;

/**
 * Interfejs służący do pracy z tabelą setting_category w bazie danych.
 */
public interface SettingCategoryRepository extends JpaRepository<SettingCategory, Long> {

}
