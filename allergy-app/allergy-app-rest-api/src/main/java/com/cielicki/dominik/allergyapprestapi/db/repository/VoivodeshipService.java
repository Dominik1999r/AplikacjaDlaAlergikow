package com.cielicki.dominik.allergyapprestapi.db.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cielicki.dominik.allergyapprestapi.db.Voivodeship;
import com.cielicki.dominik.allergyapprestapi.db.model.VoivodeshipList;

/**
 * Serwis służący do komunikacji z tabelą voivodeship w bazie danych.
 */
@Service
public class VoivodeshipService {
	@Autowired
	private VoivodeshipRepository voivodeshipRepository;
	
	/**
	 * Zapisuje województwo do bazy.
	 * 
	 * @param Voivodeship Obiekt województwa.
	 */
	public void save(Voivodeship Voivodeship) {
		if (Voivodeship != null) {
			voivodeshipRepository.save(Voivodeship);
		}
	}
	
	/**
	 * Zwraca listę województw.
	 * 
	 * @return Zwraca listę województw.
	 */
	public VoivodeshipList findAll() {
		return new VoivodeshipList(voivodeshipRepository.findAll());
	}
}
