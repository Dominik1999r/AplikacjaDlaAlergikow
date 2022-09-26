package com.cielicki.dominik.allergyapprestapi.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cielicki.dominik.allergyapprestapi.db.Chat;

/**
 * Interfejs służący do pracy z tabelą chat w bazie danych.
 */
public interface ChatRepository extends JpaRepository<Chat, Long> {
	List<Chat> findAllByUserIdOrUser2Id(Long userId, Long receiverId);
	List<Chat> findAllByUser2Id(Long userId);
}
