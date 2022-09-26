package com.cielicki.dominik.allergyapprestapi.db.model;

import java.util.ArrayList;
import java.util.List;

import com.cielicki.dominik.allergyapprestapi.db.Messages;

/**
 * Klasa reprezentująca tablicę obiektów typu Messages.
 * Została stworzona w celu umożliwienia wysyłania listy obiektów przez endpoint.
 */
public class MessagesList {
	private List<Messages> messagesList;
	
	public MessagesList() {
		messagesList = new ArrayList<Messages>();
	};
	
	public MessagesList(List<Messages> messagesList) {
		this.messagesList = messagesList;
	}

	public List<Messages> getMessagesList() {
		return messagesList;
	}

	public void setMessagesList(List<Messages> messagesList) {
		this.messagesList = messagesList;
	}
}
