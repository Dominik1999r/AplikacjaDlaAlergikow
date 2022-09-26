package com.cielicki.dominik.allergyapprestapi.db.model;

import java.util.ArrayList;
import java.util.List;

import com.cielicki.dominik.allergyapprestapi.db.SettingCategory;

/**
 * Klasa reprezentująca tablicę obiektów typu SettingCategory.
 * Została stworzona w celu umożliwienia wysyłania listy obiektów przez endpoint.
 */
public class SettingCategoryList {
	private List<SettingCategory> settingCategoryList;
	
	public SettingCategoryList() {
		settingCategoryList = new ArrayList<SettingCategory>();
	};
	
	public SettingCategoryList(List<SettingCategory> settingCategoryList) {
		this.settingCategoryList = settingCategoryList;
	}

	public List<SettingCategory> getSettingCategoryList() {
		return settingCategoryList;
	}

	public void setSettingCategoryList(List<SettingCategory> settingCategoryList) {
		this.settingCategoryList = settingCategoryList;
	}
}
