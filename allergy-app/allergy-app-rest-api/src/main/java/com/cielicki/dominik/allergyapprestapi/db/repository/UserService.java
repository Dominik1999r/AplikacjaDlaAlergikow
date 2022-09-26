package com.cielicki.dominik.allergyapprestapi.db.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cielicki.dominik.allergyapprestapi.db.User;
import com.cielicki.dominik.allergyapprestapi.db.model.UserList;

/**
 * Serwis służący do komunikacji z tabelą user w bazie danych.
 */
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Zapisuje użytkownika od bazy i zwraca wstawiony obiekt uzupełniony o ID.
	 * 
	 * @param user Obiekt użytkownika.
	 * @return Obiekt użytkownika uzupełniony o ID.
	 */
	public User save(User user) {
		return userRepository.save(user);
	}
	
	/**
	 * Zwraca listę użytkowników.
	 * 
	 * @return Zwraca listę użytkowników.
	 */
	public UserList findAll() {
		return new UserList(userRepository.findAll());
	}
	
	/**
	 * Zwraca listę użytkowników o podanym emailu.
	 * 
	 * @param email Email użytkownika.
	 * @return Zwraca listę użytkowników o podanym emailu.
	 */
	public UserList findAllByEmail(String email) {
		return new UserList(userRepository.findAllByEmail(email));
	}
}
