package com.cielicki.dominik.allergyapprestapi.db.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cielicki.dominik.allergyapprestapi.db.Allergen;
import com.cielicki.dominik.allergyapprestapi.db.model.AllergenList;

/**
 * Serwis służący do komunikacji z tabelą allergen w bazie danych.
 */
@Service
public class AllergenService {
	@Autowired
	private AllergenRepository allergenRepository;
	
	/**
	 * Zapisuje alergen do bazy.
	 * 
	 * @param allergen Obiekt alergenu.
	 */
	public void save(Allergen allergen) {
		if (allergen != null) {
			allergenRepository.save(allergen);
		}
	}
	
	/**
	 * Zwraca listę alergenów.
	 * 
	 * @return Zwraca listę alergenów.
	 */
	public AllergenList findAll() {
		return new AllergenList(allergenRepository.findAll());
	}
}
