package com.cielicki.dominik.allergyapprestapi.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Klasa reprezentująca chat.
 * Na podstawie tej klasy tworzona jest tabela chat.
 */
@Entity
@Table
public class Chat {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "user2_id", referencedColumnName = "id")
	private User user2;
	
	@Column
	private String subject;
	
	@OneToOne
	@JoinColumn(name = "last_message_id", referencedColumnName = "id")
	private Messages lastMessage;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Messages getLastMessage() {
		return lastMessage;
	}
	public void setLastMessage(Messages lastMessage) {
		this.lastMessage = lastMessage;
	}
	public User getUser2() {
		return user2;
	}
	public void setUser2(User user2) {
		this.user2 = user2;
	}
	
	public User getRecipient(User sender) {
		User recipient;
		
		if (sender.getId().longValue() == user.getId().longValue()) {
			recipient = user2;
			
		} else {
			recipient = user;
		}
		
		if (recipient.getId().longValue() == User.GLOBAL_CHAT_USER.getId().longValue()) {
			return sender;
			
		} else {
			return recipient;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		
		if (obj instanceof Chat) {
			Chat chat = (Chat) obj;
			
			return this.id.compareTo(chat.getId()) == 0;
		}
		
		return result;
	}
}
