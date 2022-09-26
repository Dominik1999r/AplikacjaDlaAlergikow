package com.cielicki.dominik.allergyapprestapi.db.model;

import java.util.ArrayList;
import java.util.List;

import com.cielicki.dominik.allergyapprestapi.db.User;

/**
 * Klasa reprezentująca tablicę obiektów typu User.
 * Została stworzona w celu umożliwienia wysyłania listy obiektów przez endpoint.
 */
public class UserList {
	private List<User> userList;
	
	public UserList() {
		userList = new ArrayList<User>();
	};
	
	public UserList(List<User> userList) {
		this.userList = userList;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
}
