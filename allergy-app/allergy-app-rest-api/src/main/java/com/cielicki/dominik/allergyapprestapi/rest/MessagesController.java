package com.cielicki.dominik.allergyapprestapi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cielicki.dominik.allergyapprestapi.db.Chat;
import com.cielicki.dominik.allergyapprestapi.db.Messages;
import com.cielicki.dominik.allergyapprestapi.db.User;
import com.cielicki.dominik.allergyapprestapi.db.model.MessagesList;
import com.cielicki.dominik.allergyapprestapi.db.repository.MessagesService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Endpoint służący do zapisywania i pobierania wiadomości. 
 */
@Component
@RestController
@RequestMapping("/messages")
public class MessagesController {
	@Autowired
	MessagesService messagesService;
	
	/**
	 * Dodaje widomość do bazy.
	 * 
	 * @param messages Wiadomość.
	 */
	@PostMapping(path="/addMessage", consumes="application/json", produces="application/json")
	public ResponseEntity<Messages> addMessage(@RequestBody Messages messages) {
		return new ResponseEntity<>(messagesService.save(messages), HttpStatus.CREATED);
	}
	
	/**
	 * Zwraca listę wiadomości.
	 * 
	 * @param chat Obiekt chatu.
	 * @return Zwraca listę wiadomości dla podanego chatu.
	 */
	@PostMapping(path="/getMessagesByChat", consumes="application/json", produces = "application/json")
	public MessagesList getMessagesByChat(@RequestBody(required = false) Chat chat) {
		return messagesService.findAllByChatId(chat);
	}
	
	/**
	 * Zwraca listę wiadomości.
	 * 
	 * @return Zwraca listę wiadomości dla podanego chatu.
	 */
	@GetMapping(path="/getMessages", produces = "application/json")
	public MessagesList getMessages() {
		return messagesService.findAll();
	}
	
	/**
	 * Zwraca listę wiadomości.
	 * 
	 * @param chat Obiekt chatu.
	 * @return Zwraca listę wiadomości dla podanego chatu, zaczynając od ostatniej wiadomości.
	 */
	@PostMapping(path="/getNewMessagesForChat", consumes="application/json", produces = "application/json")
	public MessagesList getNewMessagesForChat(@RequestBody(required = false) Chat chat) {
		return messagesService.findAllByChatIdAndLastMessageId(chat);
	}
	
	/**
	 * Zwraca listę wiadomości.
	 * 
	 * @param message Obiekt wiadomości.
	 * @return Zwraca listę wiadomości dla podanego chatu, zaczynając od ostatniej wiadomości.
	 */
	@PostMapping(path="/getNewMessagesForUser", consumes="application/json", produces = "application/json")
	public MessagesList getNewMessagesForUser(@RequestBody(required = false) String requestBody) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();
		
		JsonObject json = gson.fromJson(requestBody, JsonObject.class);
		User user = gson.fromJson(json.get("user"), User.class);
		Messages message = gson.fromJson(json.get("message"), Messages.class);
		
		return messagesService.findAllByUserIdAndLastMessageId(user, message);
	}
}
