package com.cielicki.dominik.allergyapprestapi.db.model;

import java.util.ArrayList;
import java.util.List;

import com.cielicki.dominik.allergyapprestapi.db.MedicineComment;;

/**
 * Klasa reprezentująca tablicę obiektów typu MedicineComment.
 * Została stworzona w celu umożliwienia wysyłania listy obiektów przez endpoint.
 */
public class MedicineCommentList {
	private List<MedicineComment> medicineCommentList;
	
	public MedicineCommentList() {
		medicineCommentList = new ArrayList<MedicineComment>();
	};
	
	public MedicineCommentList(List<MedicineComment> medicineCommentList) {
		this.medicineCommentList = medicineCommentList;
	}

	public List<MedicineComment> getMedicineCommentList() {
		return medicineCommentList;
	}

	public void setMedicineCommentList(List<MedicineComment> medicineCommentList) {
		this.medicineCommentList = medicineCommentList;
	}
}
