package com.cielicki.dominik.allergyapprestapi.db.model;

import java.util.ArrayList;
import java.util.List;

import com.cielicki.dominik.allergyapprestapi.db.Medicine;;

/**
 * Klasa reprezentująca tablicę obiektów typu Medicine.
 * Została stworzona w celu umożliwienia wysyłania listy obiektów przez endpoint.
 */
public class MedicineList {
	private List<Medicine> medicineList;
	
	public MedicineList() {
		medicineList = new ArrayList<Medicine>();
	};
	
	public MedicineList(List<Medicine> medicineList) {
		this.medicineList = medicineList;
	}

	public List<Medicine> getMedicineList() {
		return medicineList;
	}

	public void setMedicineList(List<Medicine> medicineList) {
		this.medicineList = medicineList;
	}
}
