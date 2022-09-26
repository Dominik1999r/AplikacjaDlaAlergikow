package com.cielicki.dominik.allergyapprestapi.db.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cielicki.dominik.allergyapprestapi.db.Voivodeship;
import com.cielicki.dominik.allergyapprestapi.db.VoivodeshipAllergen;
import com.cielicki.dominik.allergyapprestapi.db.VoivodeshipAllergenId;

/**
 * Interfejs służący do pracy z tabelą voivodeship_allergen w bazie danych.
 */
public interface VoivodeshipAllergenRepository extends JpaRepository<VoivodeshipAllergen, VoivodeshipAllergenId> {
	List<VoivodeshipAllergen> findAllByIdVoivodeship(Voivodeship voivodeship);
	
	@Query("SELECT va FROM VoivodeshipAllergen va where va.id.voivodeship.id = :id AND :date BETWEEN va.id.startDate AND va.id.endDate")
	List<VoivodeshipAllergen> findAllByIdVoivodeship(@Param("id") Long voivodeshipId, @Param("date") Date date);
}
