package com.cielicki.dominik.allergyapprestapi.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cielicki.dominik.allergyapprestapi.db.Setting;
import com.cielicki.dominik.allergyapprestapi.db.model.SettingList;
import com.cielicki.dominik.allergyapprestapi.db.repository.SettingService;

/**
 * Endpoint do pobierania ustawień.
 */
@Component
@RestController
@RequestMapping("/setting")
public class SettingController {
	@Autowired
	SettingService settingService;
	
	/**
	 * Dodaje ustawienie do bazy.
	 * 
	 * @param setting Ustawienie.
	 */
	@PostMapping(path="/addSetting", consumes="application/json", produces="application/json")
	public void addSetting(@RequestBody Setting setting) {
		settingService.save(setting);
	}
	
	/**
	 * Zwraca listę ustawień.
	 * 
	 * @return Zwraca listę ustawień.
	 */
	@GetMapping(path="/getSettings", produces = "application/json")
	public SettingList getSettings() {
		return settingService.findAll();
	}
}
