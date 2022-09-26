package com.cielicki.dominik.allergyapprestapi.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cielicki.dominik.allergyapprestapi.db.User;
import com.cielicki.dominik.allergyapprestapi.db.UserSettings;
import com.cielicki.dominik.allergyapprestapi.db.UserSettingsId;

/**
 * Interfejs służący do pracy z tabelą user_settings w bazie danych.
 */
public interface UserSettingsRepository extends JpaRepository<UserSettings, UserSettingsId> {
	List<UserSettings> findAllByIdUser(User user);
}
