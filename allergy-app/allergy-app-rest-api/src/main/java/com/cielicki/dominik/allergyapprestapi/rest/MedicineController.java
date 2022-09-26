package com.cielicki.dominik.allergyapprestapi.rest;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cielicki.dominik.allergyapprestapi.db.Medicine;
import com.cielicki.dominik.allergyapprestapi.db.model.MedicineList;
import com.cielicki.dominik.allergyapprestapi.db.repository.MedicineService;

/**
 * Endpoint służący do zapisywania i pobierania lekarstw.
 */
@Component
@RestController
@RequestMapping("/medicine")
public class MedicineController {
	@Autowired
	MedicineService medicineService;
	
	/**
	 * Dodaje lekarstwo do bazy.
	 * 
	 * @param medicine Lekarstwo.
	 */
	@PostMapping(path="/addMedicine", consumes="application/json", produces="application/json")
	public void addMedicine(@RequestBody Medicine medicine) {
		medicineService.save(medicine);
	}
	
	/**
	 * Zwraca listę lekarstw.
	 * 
	 * @return Zwraca listę lekarstw.
	 */
	@GetMapping(path="/getMedicines", produces = "application/json")
	public MedicineList getMedicines() {
		return medicineService.findAll();
	}
	
	/**
	 * Zwraca ocenę lekarstw.
	 * 
	 * @param medicine Obiekt leku.
	 * @return Zwraca ocenę lekarstw.
	 */
	@PostMapping(path="/getMedicineRating", consumes = "application/json", produces = "application/json")
	public String getMedicines(@RequestBody(required = false) Medicine medicine) {
		BigDecimal rating = medicineService.getMedicineRating(medicine);
		
		if (rating != null) {
			return "{ \"rating\": " + rating.toString() + "}";			
			
		} else {
			return "{}";
		}
	}
}
