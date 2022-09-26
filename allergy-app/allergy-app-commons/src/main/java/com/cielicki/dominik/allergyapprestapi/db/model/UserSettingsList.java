package com.cielicki.dominik.allergyapprestapi.db.model;

import java.util.ArrayList;
import java.util.List;

import com.cielicki.dominik.allergyapprestapi.db.UserSettings;

/**
 * Klasa reprezentująca tablicę obiektów typu UserSetting.
 * Została stworzona w celu umożliwienia wysyłania listy obiektów przez endpoint.
 */
public class UserSettingsList {
	private List<UserSettings> userSettingsList;
	
	public UserSettingsList() {
		userSettingsList = new ArrayList<UserSettings>();
	};
	
	public UserSettingsList(List<UserSettings> userSettingsList) {
		this.userSettingsList = userSettingsList;
	}

	public List<UserSettings> getUserSettingsList() {
		return userSettingsList;
	}

	public void setUserSettingsList(List<UserSettings> userSettingsList) {
		this.userSettingsList = userSettingsList;
	}
}
