package com.cielicki.dominik.allergyapprestapi.db.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cielicki.dominik.allergyapprestapi.db.Setting;
import com.cielicki.dominik.allergyapprestapi.db.model.SettingList;

/**
 * Serwis służący do komunikacji z tabelą setting w bazie danych.
 */
@Service
public class SettingService {
	@Autowired
	private SettingRepository settingRepository;
	
	/**
	 * Zapisuje ustawienie do bazy.
	 * 
	 * @param setting Obiekt ustawienia.
	 */
	public void save(Setting setting) {
		if (setting != null) {
			settingRepository.save(setting);
		}
	}
	
	/**
	 * Zwraca listę ustawień.
	 * 
	 * @return Zwraca listę ustawień.
	 */
	public SettingList findAll() {
		return new SettingList(settingRepository.findAll());
	}
}
