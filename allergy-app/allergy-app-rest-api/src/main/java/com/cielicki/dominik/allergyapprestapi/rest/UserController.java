package com.cielicki.dominik.allergyapprestapi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cielicki.dominik.allergyapprestapi.db.User;
import com.cielicki.dominik.allergyapprestapi.db.model.UserList;
import com.cielicki.dominik.allergyapprestapi.db.repository.UserService;

/**
 * Endpoint służący do zapisywania i pobierania użytkowników.
 */
@Component
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;

	/**
	 * Zapisuje użytkownika do bazy.
	 * 
	 * @param user Obiekt użytkownika.
	 */
	@PostMapping(path="/addUser", consumes="application/json", produces="application/json")
	public User addUser(@RequestBody User user) {
		return userService.save(user);
	}
	
	/**
	 * Zwraca listę użytkowników.
	 * 
	 * @return Zwraca listę użytkowników.
	 */
	@GetMapping(path="/getUsers", produces = "application/json")
	public UserList getUsers() {
		return userService.findAll();
	}
	
	/**
	 * Zwraca użytkownika na podstawie podanego emaila.
	 * 
	 * @param user Obiekt użytkownika.
	 * @return Zwraca listę z jednym użytkownikiem, o tym samym adresie email lub pustą listę.
	 */
	@PostMapping(path="/getUser", consumes = "application/json", produces = "application/json")
	public UserList getUser(@RequestBody User user) {
		return userService.findAllByEmail(user.getEmail());
	}
}
