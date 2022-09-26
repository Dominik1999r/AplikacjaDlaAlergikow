package com.cielicki.dominik.allergyapprestapi.db.model;

import java.util.ArrayList;
import java.util.List;

import com.cielicki.dominik.allergyapprestapi.db.VoivodeshipAllergen;

/**
 * Klasa reprezentująca tablicę obiektów typu voivodeshipAllergen.
 * Została stworzona w celu umożliwienia wysyłania listy obiektów przez endpoint.
 */
public class VoivodeshipAllergenList {
	private List<VoivodeshipAllergen> voivodeshipAllergenList;
	
	public VoivodeshipAllergenList() {
		voivodeshipAllergenList = new ArrayList<VoivodeshipAllergen>();
	};
	
	public VoivodeshipAllergenList(List<VoivodeshipAllergen> voivodeshipAllergenList) {
		this.voivodeshipAllergenList = voivodeshipAllergenList;
	}

	public List<VoivodeshipAllergen> getVoivodeshipAllergenList() {
		return voivodeshipAllergenList;
	}

	public void setVoivodeshipAllergenList(List<VoivodeshipAllergen> voivodeshipAllergenList) {
		this.voivodeshipAllergenList = voivodeshipAllergenList;
	}
}
