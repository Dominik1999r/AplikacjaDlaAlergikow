package com.cielicki.dominik.allergyapprestapi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cielicki.dominik.allergyapprestapi.db.Allergen;
import com.cielicki.dominik.allergyapprestapi.db.model.AllergenList;
import com.cielicki.dominik.allergyapprestapi.db.repository.AllergenService;

/**
 * Endpoint służący do zapisywania i pobierania alergenów.
 */
@Component
@RestController
@RequestMapping("/allergen")
public class AllergenController {
	@Autowired
	AllergenService allergenService;
	
	/**
	 * Dodaje alergen do bazy.
	 * 
	 * @param allergen Alergen.
	 */
	@PostMapping(path="/addAllergen", consumes="application/json", produces="application/json")
	public void addAllergen(@RequestBody Allergen allergen) {
		allergenService.save(allergen);
	}
	
	/**
	 * Zwraca listę alergenów.
	 * 
	 * @return Zwraca listę alergenów
	 */
	@GetMapping(path="/getAllergens", produces = "application/json")
	public AllergenList getAllergens() {
		return allergenService.findAll();
	}
}
