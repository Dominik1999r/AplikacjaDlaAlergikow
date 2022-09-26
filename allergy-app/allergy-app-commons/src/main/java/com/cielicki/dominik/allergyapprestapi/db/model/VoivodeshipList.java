package com.cielicki.dominik.allergyapprestapi.db.model;

import java.util.ArrayList;
import java.util.List;

import com.cielicki.dominik.allergyapprestapi.db.Voivodeship;

/**
 * Klasa reprezentująca tablicę obiektów typu Voivodeship.
 * Została stworzona w celu umożliwienia wysyłania listy obiektów przez endpoint.
 */
public class VoivodeshipList {
	private List<Voivodeship> voivodeshipList;
	
	public VoivodeshipList() {
		voivodeshipList = new ArrayList<Voivodeship>();
	};
	
	public VoivodeshipList(List<Voivodeship> userList) {
		this.voivodeshipList = userList;
	}

	public List<Voivodeship> getVoivodeshipList() {
		return voivodeshipList;
	}

	public void setVoivodeshipList(List<Voivodeship> userList) {
		this.voivodeshipList = userList;
	}
}
