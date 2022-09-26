package com.cielicki.dominik.allergyapprestapi.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cielicki.dominik.allergyapprestapi.db.Medicine;
import com.cielicki.dominik.allergyapprestapi.db.MedicineComment;
import com.cielicki.dominik.allergyapprestapi.db.MedicineCommentId;

/**
 * Interfejs służący do pracy z tabelą medicine_comment w bazie danych.
 */
public interface MedicineCommentRepository extends JpaRepository<MedicineComment, MedicineCommentId> {
	List<MedicineComment> findByIdMedicine(Medicine medicine);
}
