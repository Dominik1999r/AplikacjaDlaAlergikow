package com.cielicki.dominik.allergyapprestapi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cielicki.dominik.allergyapprestapi.db.Voivodeship;
import com.cielicki.dominik.allergyapprestapi.db.model.VoivodeshipList;
import com.cielicki.dominik.allergyapprestapi.db.repository.VoivodeshipService;

/**
 * Endpoint służący do zapisywania i pobierania województw.
 */
@Component
@RestController
@RequestMapping("/voivodeship")
public class VoivodeshipController {
	@Autowired
	VoivodeshipService voivodeshipService;

	/**
	 * Zapisuje województwo do bazy.
	 * 
	 * @param user Obiekt województwa.
	 */
	@PostMapping(path="/addVoivodeship", consumes="application/json", produces="application/json")
	public void addVoivodeship(@RequestBody Voivodeship voivodeship) {
		voivodeshipService.save(voivodeship);
	}
	
	/**
	 * Zwraca listę województw.
	 * 
	 * @return Zwraca listę województw.
	 */
	@GetMapping(path="/getVoivodeships", produces = "application/json")
	public VoivodeshipList getVoivodeships() {
		return voivodeshipService.findAll();
	}
}
