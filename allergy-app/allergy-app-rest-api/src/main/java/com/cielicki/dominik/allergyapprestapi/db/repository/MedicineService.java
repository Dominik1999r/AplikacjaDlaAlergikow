package com.cielicki.dominik.allergyapprestapi.db.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cielicki.dominik.allergyapprestapi.db.Medicine;
import com.cielicki.dominik.allergyapprestapi.db.model.MedicineList;

/**
 * Serwis służący do komunikacji z tabelą medicine w bazie danych.
 */
@Service
public class MedicineService {
	@Autowired
	private MedicineRepository medicineRepository;
	
	/**
	 * Zapisuje lek do bazy danych.
	 * 
	 * @param medicine Obiekt leku.
	 */
	public void save(Medicine medicine) {
		if (medicine != null) {
			medicineRepository.save(medicine);
		}
	}
	
	/**
	 * Zwraca listę leków.
	 * 
	 * @return Zwraca listę leków.
	 */
	public MedicineList findAll() {
		List<Medicine> medicineList = medicineRepository.findAll();
		
		for (Medicine medicine: medicineList) {
			medicine.setAverageScore(medicineRepository.getAverageScore(medicine.getId()));
		}
		
		return new MedicineList(medicineList);
	}
	
	/**
	 * Zwraca średnią ocen dla podanego leku.
	 * 
	 * @param medicine Obiekt leku.
	 * @return Zwraca średnią ocen dla podanego leku.
	 */
	public BigDecimal getMedicineRating(Medicine medicine) {
		return medicineRepository.getAverageScore(medicine.getId());
	}
}
