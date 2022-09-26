package com.cielicki.dominik.allergyapprestapi.db.repository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cielicki.dominik.allergyapprestapi.db.Voivodeship;
import com.cielicki.dominik.allergyapprestapi.db.VoivodeshipAllergen;
import com.cielicki.dominik.allergyapprestapi.db.model.VoivodeshipAllergenList;

/**
 * Serwis służący do komunikacji z tabelą voivodeship_allergen w bazie danych.
 */
@Service
public class VoivodeshipAllergenService {
	@Autowired
	private VoivodeshipAllergenRepository voivodeshipAllergenRepository;
	
	/**
	 * Zapisuje informację o alergenie w województwie.
	 * 
	 * @param voivodeshipAllergen Obiekt zawierający informację o alergenie w województwie. 
	 */
	public void save(VoivodeshipAllergen voivodeshipAllergen) {
		if (voivodeshipAllergen != null) {
			voivodeshipAllergenRepository.save(voivodeshipAllergen);
		}
	}
	
	/**
	 * Zwraca listę z informacjami o alergenach w województwach.
	 * 
	 * @return Zwraca listę z informacjami o alergenach w województwach.
	 */
	public VoivodeshipAllergenList findAll() {
		return new VoivodeshipAllergenList(voivodeshipAllergenRepository.findAll());
	}
	
	/**
	 * Zwraca listę z informacjami o alergenach w podanym województwie.
	 * 
	 * @param voivodeship Obiekt województwa.
	 * @return Zwraca listę z informacjami o alergenach w podanym województwie.
	 */
	public VoivodeshipAllergenList findAllByVoivodenship(Voivodeship voivodeship) {
		return new VoivodeshipAllergenList(voivodeshipAllergenRepository.findAllByIdVoivodeship(voivodeship));
	}
	
	/**
	 * Zwraca listę z informacjami o alergenach w podanym województwie.
	 * 
	 * @param voivodeship Obiekt województwa.
	 * @return Zwraca listę z informacjami o alergenach w podanym województwie.
	 */
	public VoivodeshipAllergenList findAllByVoivodenshipAndDate(Voivodeship voivodeship, Date date) {
		return new VoivodeshipAllergenList(voivodeshipAllergenRepository.findAllByIdVoivodeship(voivodeship.getId(), date));
	}
}
