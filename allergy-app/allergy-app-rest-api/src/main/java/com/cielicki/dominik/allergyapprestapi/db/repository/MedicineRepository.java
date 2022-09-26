package com.cielicki.dominik.allergyapprestapi.db.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cielicki.dominik.allergyapprestapi.db.Medicine;;

/**
 * Interfejs służący do pracy z tabelą medicine w bazie danych.
 */
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
	@Query("SELECT avg(mc.rating) FROM MedicineComment mc WHERE mc.id.medicine.id = ?1")
	BigDecimal getAverageScore(Long id);
}
