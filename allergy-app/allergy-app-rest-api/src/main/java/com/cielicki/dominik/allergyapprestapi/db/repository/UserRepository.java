package com.cielicki.dominik.allergyapprestapi.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cielicki.dominik.allergyapprestapi.db.User;

/**
 * Interfejs służący do pracy z tabelą user w bazie danych.
 */
public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findAllByEmail(String email);
}
