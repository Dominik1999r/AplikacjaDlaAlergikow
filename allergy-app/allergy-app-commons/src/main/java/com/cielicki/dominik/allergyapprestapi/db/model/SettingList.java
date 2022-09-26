package com.cielicki.dominik.allergyapprestapi.db.model;

import java.util.ArrayList;
import java.util.List;

import com.cielicki.dominik.allergyapprestapi.db.Setting;

/**
 * Klasa reprezentująca tablicę obiektów typu Setting.
 * Została stworzona w celu umożliwienia wysyłania listy obiektów przez endpoint.
 */
public class SettingList {
	private List<Setting> settingList;
	
	public SettingList() {
		settingList = new ArrayList<Setting>();
	};
	
	public SettingList(List<Setting> settingList) {
		this.settingList = settingList;
	}

	public List<Setting> getSettingList() {
		return settingList;
	}

	public void setSettingList(List<Setting> settingList) {
		this.settingList = settingList;
	}
}
