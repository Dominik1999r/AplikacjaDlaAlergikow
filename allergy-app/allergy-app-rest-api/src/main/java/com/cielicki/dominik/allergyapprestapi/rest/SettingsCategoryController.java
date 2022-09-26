package com.cielicki.dominik.allergyapprestapi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cielicki.dominik.allergyapprestapi.db.SettingCategory;
import com.cielicki.dominik.allergyapprestapi.db.model.SettingCategoryList;
import com.cielicki.dominik.allergyapprestapi.db.repository.SettingCategoryService;

/**
 * Endpoint służący do zapisywania i pobierania kategorii ustawień.
 */
@Component
@RestController
@RequestMapping("/settingCategory")
public class SettingsCategoryController {
	@Autowired
	SettingCategoryService settingCategoryService;
	
	/**
	 * Zapisuje kategorię ustawień do bazy.
	 * 
	 * @param settingCategory
	 */
	@PostMapping(path="/addSettingCategory", consumes="application/json", produces="application/json")
	public void addSettingCategory(@RequestBody SettingCategory settingCategory) {
		settingCategoryService.save(settingCategory);
	}
	
	/**
	 * Zwraca listę kategorii ustawień.
	 * @return Zwraca listę kategorii ustawień.
	 */
	@GetMapping(path="/getSettingCategory", produces = "application/json")
	public SettingCategoryList getSettingCategory() {
		return settingCategoryService.findAll();
	}
}
