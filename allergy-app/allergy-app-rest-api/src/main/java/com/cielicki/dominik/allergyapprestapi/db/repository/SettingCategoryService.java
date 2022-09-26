package com.cielicki.dominik.allergyapprestapi.db.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cielicki.dominik.allergyapprestapi.db.SettingCategory;
import com.cielicki.dominik.allergyapprestapi.db.model.SettingCategoryList;

/**
 * Serwis służący do komunikacji z tabelą setting_category w bazie danych.
 */
@Service
public class SettingCategoryService {
	@Autowired
	private SettingCategoryRepository settingCategoryRepository;
	
	/**
	 * Dodaje kategorię ustawień do bazy danych.
	 * 
	 * @param settingCategory Obiekt kategorii ustawień.
	 */
	public void save(SettingCategory settingCategory) {
		if (settingCategory != null) {
			settingCategoryRepository.save(settingCategory);
		}
	}
	
	/**
	 * Zwraca listę kategorii ustawień.
	 * 
	 * @return Zwraca listę kategorii ustawień.
	 */
	public SettingCategoryList findAll() {
		return new SettingCategoryList(settingCategoryRepository.findAll());
	}
}
