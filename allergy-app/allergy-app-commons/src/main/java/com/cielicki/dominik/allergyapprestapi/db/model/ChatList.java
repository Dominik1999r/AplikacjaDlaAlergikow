package com.cielicki.dominik.allergyapprestapi.db.model;

import java.util.ArrayList;
import java.util.List;

import com.cielicki.dominik.allergyapprestapi.db.Chat;

/**
 * Klasa reprezentująca tablicę obiektów typu Chat.
 * Została stworzona w celu umożliwienia wysyłania listy obiektów przez endpoint.
 */
public class ChatList {
	private List<Chat> chatList;
	
	public ChatList() {
		chatList = new ArrayList<Chat>();
	};
	
	public ChatList(List<Chat> chatList) {
		this.chatList = chatList;
	}

	public List<Chat> getChatList() {
		return chatList;
	}

	public void setChatList(List<Chat> chatList) {
		this.chatList = chatList;
	}
}
