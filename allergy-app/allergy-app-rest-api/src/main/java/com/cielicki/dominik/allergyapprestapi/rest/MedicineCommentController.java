package com.cielicki.dominik.allergyapprestapi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cielicki.dominik.allergyapprestapi.db.Medicine;
import com.cielicki.dominik.allergyapprestapi.db.MedicineComment;
import com.cielicki.dominik.allergyapprestapi.db.model.MedicineCommentList;
import com.cielicki.dominik.allergyapprestapi.db.repository.MedicineCommentService;

/**
 * Endpoint służący do zapisywania i pobierania komentarzy do lekarstw.
 */
@Component
@RestController
@RequestMapping("/medicineComment")
public class MedicineCommentController {
	@Autowired
	MedicineCommentService medicineCommentService;
	
	/**
	 * Dodaje komentarz o lekarstwie do bazy.
	 * 
	 * @param medicineComment Komentarz o lekarstwie.
	 */
	@PostMapping(path="/addMedicineComment", consumes="application/json", produces="application/json")
	public String addMedicineComment(@RequestBody MedicineComment medicineComment) {
		medicineCommentService.save(medicineComment);
		return "{\"success\":1}";
	}
	
	/**
	 * Zwraca listę komentarzy o lekartwie.
	 * 
	 * @param medicine Obiekt lekarstwa.
	 * @return Zwraca listę komentarzy o lekarstwie.
	 */
	@PostMapping(path="/getMedicineComments", consumes="application/json", produces = "application/json")
	public MedicineCommentList getMedicineComments(@RequestBody(required = false) Medicine medicine) { 
		return medicineCommentService.findByMedicine(medicine);
	}
}
