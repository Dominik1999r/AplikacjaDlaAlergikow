package com.cielicki.dominik.allergyapprestapi.rest;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cielicki.dominik.allergyapprestapi.db.Chat;
import com.cielicki.dominik.allergyapprestapi.db.User;
import com.cielicki.dominik.allergyapprestapi.db.model.ChatList;
import com.cielicki.dominik.allergyapprestapi.db.repository.ChatService;

/**
 * Endpoint służący do zapisywania i pobierania chatów.
 */
@Component
@RestController
@RequestMapping("/chat")
public class ChatController {
	@Autowired
	ChatService chatService;
	
	/**
	 * Dodaje chat do bazy.
	 * 
	 * @param chat Chat.
	 */
	@PostMapping(path="/addChat", consumes="application/json", produces="application/json")
	public Chat addChat(@RequestBody Chat chat) {
		return chatService.save(chat);
	}
	
	/**
	 * Zwraca listę chatów dla podanego użytkownika.
	 * 
	 * @param user Obiekt użytkownika.
	 * @return Zwraca listę chatów dla podanego użytkownika.
	 */
	@PostMapping(path="/getChatsForUser", consumes="application/json", produces = "application/json")
	public ChatList getChatsForUser(@RequestBody(required = false) User user) {
		return chatService.findAllByUser(user);
	}
	
	/**
	 * Zwraca listę chatów dla podanego użytkownika oraz chaty globalne.
	 * 
	 * @param user Obiekt użytkownika.
	 * @return Zwraca listę chatów dla podanego użytkownika oraz chaty globalne.
	 */
	@PostMapping(path="/getChatsForUserAndGlobal", consumes="application/json", produces = "application/json")
	public ChatList getChatsForUserAndGlobal(@RequestBody(required = false) User user) {
		ChatList chatListForUser = chatService.findAllByUser(user);
		ChatList chatListGlobal = chatService.findGlobalChats();
		chatListForUser.getChatList().addAll(chatListGlobal.getChatList());
		
		chatListForUser.setChatList(chatListForUser.getChatList().stream().distinct().collect(Collectors.toList()));
		
		return chatListForUser;
	}
	
	/**
	 * Zwraca listę chatów.
	 * 
	 * @return Zwraca listę chatów.
	 */
	@GetMapping(path="/getChats", produces = "application/json")
	public ChatList getChats() {
		return chatService.findAll();
	}
}
