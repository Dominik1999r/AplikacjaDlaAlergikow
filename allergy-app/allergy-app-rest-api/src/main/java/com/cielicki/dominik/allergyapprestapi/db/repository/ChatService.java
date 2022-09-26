package com.cielicki.dominik.allergyapprestapi.db.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cielicki.dominik.allergyapprestapi.db.Chat;
import com.cielicki.dominik.allergyapprestapi.db.User;
import com.cielicki.dominik.allergyapprestapi.db.model.ChatList;

/**
 * Serwis służący do komunikacji z tabelą chat w bazie danych.
 */
@Service
public class ChatService {
	@Autowired
	private ChatRepository chatRepository;
	
	/**
	 * Zapisuje chat do bazy.
	 * 
	 * @param chat Obiekt chatu.
	 */
	public Chat save(Chat chat) {
		if (chat != null) {
			return chatRepository.save(chat);
		}
		
		return null;
	}
	
	/**
	 * Zwraca listę chatów.
	 * 
	 * @return Zwraca listę chatów.
	 */
	public ChatList findAll() {
		return new ChatList(chatRepository.findAll());
	}
	
	/**
	 * Zwraca listę chatów dla podanego użytkownika.
	 * 
	 * @param user Obiekt użytkownika.
	 * @return Zwraca listę chatów dla podanego użytkownika.
	 */
	public ChatList findAllByUser(User user) {
		return new ChatList(chatRepository.findAllByUserIdOrUser2Id(user.getId(), user.getId()));
	}
	
	/**
	 * Zwraca listę chatów globalnych.
	 * 
	 * @return Zwraca listę chatów globalnych.
	 */
	public ChatList findGlobalChats() {
		return new ChatList(chatRepository.findAllByUser2Id(User.GLOBAL_CHAT_USER.getId()));
	}
}
