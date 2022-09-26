package com.cielicki.dominik.allergyapprestapi.db.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cielicki.dominik.allergyapprestapi.db.Chat;
import com.cielicki.dominik.allergyapprestapi.db.Messages;
import com.cielicki.dominik.allergyapprestapi.db.User;
import com.cielicki.dominik.allergyapprestapi.db.model.MessagesList;

/**
 * Serwis służący do komunikacji z tabelą messages w bazie danych.
 */
@Service
public class MessagesService {
	@Autowired
	private MessagesRepository messagesRepository;
	
	/**
	 * Zapisuje wiadomość do bazy.
	 * 
	 * @param messages Obiekt wiadomości.
	 * @return Obiekt wiadomości uzupełniony o ID.
	 */
	public Messages save(Messages messages) {
		if (messages != null) {
			return messagesRepository.save(messages);
		} else {
			return null;
		}
	}
	
	/**
	 * Zwraca listę wiadomości.
	 * 
	 * @return Zwraca listę wiadomości.
	 */
	public MessagesList findAll() {
		return new MessagesList(messagesRepository.findAll());
	}
	
	/**
	 * Zwraca listę wiadomości dla podanego chatu.
	 * 
	 * @param chat Obiekt chatu.
	 * @return Zwraca listę wiadomości dla podanego chatu.
	 */
	public MessagesList findAllByChatId(Chat chat) {
		return new MessagesList(messagesRepository.findAllByChatId(chat.getId()));
	}
	
	/**
	 * Zwraca listę wiadomości dla podanego chatu od jego ostatniej wiadomości.
	 * 
	 * @param chat Obiekt chatu.
	 * @return Zwraca listę wiadomości dla podanego chatu od jego ostatniej wiadomości.
	 */
	public MessagesList findAllByChatIdAndLastMessageId(Chat chat) {
		if (chat != null && chat.getLastMessage() != null && chat.getLastMessage().getId() != null ) {
			return new MessagesList(messagesRepository.findAllByChatIdAndIdGreaterThan(chat.getId(), chat.getLastMessage().getId()));			
			
		} else if (chat.getLastMessage() == null) {
			return findAllByChatId(chat);
			
		} else {
			return new MessagesList();
		}
	}
	
	public MessagesList findAllByUserIdAndLastMessageId(User user, Messages message) {
		if (message != null) {
			return new MessagesList(messagesRepository.findAllByUserIdAndLastMessageId(user.getId(), message.getId()));			
			
		} else {
			return new MessagesList();
		}
	}
}
