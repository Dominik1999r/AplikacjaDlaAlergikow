package com.cielicki.dominik.allergyapprestapi.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cielicki.dominik.allergyapprestapi.db.Messages;

/**
 * Interfejs służący do pracy z tabelą messages w bazie danych.
 */
public interface MessagesRepository extends JpaRepository<Messages, Long> {
	List<Messages> findAllByChatId(Long id);
	List<Messages> findAllByChatIdAndIdGreaterThan(Long id, Long messageId);
	
	@Query("SELECT m FROM Messages m WHERE (m.chat.user.id = ?1 OR m.chat.user2.id = ?1) AND m.id > ?2 ORDER BY id DESC")
	List<Messages> findAllByUserIdAndLastMessageId(Long userId, Long messageId);
}
