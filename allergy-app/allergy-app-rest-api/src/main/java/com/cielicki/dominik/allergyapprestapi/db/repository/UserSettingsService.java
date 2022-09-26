package com.cielicki.dominik.allergyapprestapi.db.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cielicki.dominik.allergyapprestapi.db.User;
import com.cielicki.dominik.allergyapprestapi.db.UserSettings;
import com.cielicki.dominik.allergyapprestapi.db.model.UserSettingsList;

/**
 * Serwis służący do komunikacji z tabelą user_settings w bazie danych.
 */
@Service
public class UserSettingsService {
	@Autowired
	private UserSettingsRepository userSettingsRepository;
	
	/**
	 * Zapisuje ustawienie użytkownika do bazy.
	 * 
	 * @param userSettings Ustawienia użytkownika.
	 */
	public void save(UserSettings userSettings) {
		if (userSettings != null) {
			userSettingsRepository.save(userSettings);
		}
	}
	
	/**
	 * Zwraca listę ustawień użytkowników.
	 * 
	 * @return Zwraca listę ustawień użytkowników.
	 */
	public UserSettingsList findAllByIdUser(User user) {
		return new UserSettingsList(userSettingsRepository.findAllByIdUser(user));
	}
}
