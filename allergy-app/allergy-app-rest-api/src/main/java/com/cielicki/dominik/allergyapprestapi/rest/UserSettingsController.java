package com.cielicki.dominik.allergyapprestapi.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cielicki.dominik.allergyapprestapi.db.User;
import com.cielicki.dominik.allergyapprestapi.db.UserSettings;
import com.cielicki.dominik.allergyapprestapi.db.model.UserSettingsList;
import com.cielicki.dominik.allergyapprestapi.db.repository.UserSettingsService;

/**
 * Endpoint słuszący do pobierania i zapisywania ustawień użytkownika. 
 */
@Component
@RestController
@RequestMapping("/userSettings")
public class UserSettingsController {
	@Autowired
	UserSettingsService userSettingsService;
	
	/**
	 * Zapisuje ustawienia do bazy.
	 *  
	 * @param userSettings Ustawienia użytkownika.
	 */
	@PostMapping(path="/addUserSetting", consumes="application/json", produces="application/json")
	public void addUserSetting(@RequestBody UserSettings userSettings) {
		userSettingsService.save(userSettings);
	}
	
	/**
	 * Zwraca listę ustawień użytkownika.
	 * 
	 * @return Zwraca listę ustawień użytkownika.
	 */
	@PostMapping(path="/getUserSettings", produces = "application/json")
	public UserSettingsList getUserSettings(@RequestBody(required = true) User user) {
		return userSettingsService.findAllByIdUser(user);
	}
}
