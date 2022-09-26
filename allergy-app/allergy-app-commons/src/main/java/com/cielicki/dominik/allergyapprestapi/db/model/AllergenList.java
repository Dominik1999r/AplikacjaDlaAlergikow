package com.cielicki.dominik.allergyapprestapi.db.model;

import java.util.ArrayList;
import java.util.List;

import com.cielicki.dominik.allergyapprestapi.db.Allergen;

/**
 * Klasa reprezentująca tablicę obiektów typu Allergen.
 * Została stworzona w celu umożliwienia wysyłania listy obiektów przez endpoint.
 */
public class AllergenList {
	private List<Allergen> allergenList;
	
	public AllergenList() {
		allergenList = new ArrayList<Allergen>();
	};
	
	public AllergenList(List<Allergen> allergenList) {
		this.allergenList = allergenList;
	}

	public List<Allergen> getAllergenList() {
		return allergenList;
	}

	public void setAllergenList(List<Allergen> allergenList) {
		this.allergenList = allergenList;
	}
}
