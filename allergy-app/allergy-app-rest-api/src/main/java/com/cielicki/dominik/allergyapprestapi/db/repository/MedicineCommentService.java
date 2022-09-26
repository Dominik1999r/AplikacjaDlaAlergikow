package com.cielicki.dominik.allergyapprestapi.db.repository;

import java.util.Collections;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cielicki.dominik.allergyapprestapi.db.Medicine;
import com.cielicki.dominik.allergyapprestapi.db.MedicineComment;
import com.cielicki.dominik.allergyapprestapi.db.model.MedicineCommentList;

/**
 * Serwis służący do komunikacji z tabelą medicine_comment w bazie danych.
 */
@Service
public class MedicineCommentService {
	@Autowired
	private MedicineCommentRepository medicineCommentRepository;
	
	/**
	 * Zapisuje komentarz o lekarstwie do bazy.
	 * 
	 * @param medicineComment Obiekt komentarza lekarstwa.
	 */
	public void save(MedicineComment medicineComment) {
		if (medicineComment != null) {
			medicineCommentRepository.save(medicineComment);
		}
	}
	
	/**
	 * Zwraca listę komentarzy o lekarstwach.
	 * 
	 * @return Lista komentarzy o lekarstwach.
	 */
	public MedicineCommentList findAll() {
		return new MedicineCommentList(medicineCommentRepository.findAll());
	}
	
	
	/**
	 * Zwraca listę komentarzy o podanym lekarstwie.
	 * 
	 * @param medicine Obiekt lekarstwa.
	 * @return Lista komentarzy o podanym lekarstwie. 
	 */
	public MedicineCommentList findByMedicine(Medicine medicine) {
		MedicineCommentList medicineCommentsList = new MedicineCommentList(medicineCommentRepository.findByIdMedicine(medicine));
		
		Collections.sort(medicineCommentsList.getMedicineCommentList(), new Comparator<MedicineComment>() {
			@Override
			public int compare(MedicineComment o1, MedicineComment o2) {
				return o2.getId().getDate().compareTo(o1.getId().getDate());
			}
		});
		
		return medicineCommentsList; 
	}
}
