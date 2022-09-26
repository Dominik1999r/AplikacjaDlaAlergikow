package com.cielicki.dominik.allergyapprestapi.db;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TemporalType;
import javax.persistence.Temporal;

/**
 * Klasa reprezentująca wiadomość.
 * Na podstawie tej klasy tworzona jest tabela messages.
 */
@Entity
@Table
public class Messages implements Serializable {
	/**
	 * 
	 */
	private static final Long serialVersionUID = -4397744115527975352L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Column
	private String message;
	
	@Column(nullable = false)
	private Long status;
	
	@ManyToOne
	@JoinColumn(name = "chat_id", referencedColumnName = "id")
	Chat chat;
	
	@OneToOne
	@JoinColumn(name = "sender_id", referencedColumnName = "id")
	private User sender;
	
	@OneToOne
	@JoinColumn(name = "recipient_id", referencedColumnName = "id")
	private User recipient;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getRecipient() {
		return recipient;
	}

	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		
		if (obj instanceof Messages) {
			Messages message = (Messages) obj;
			
			return this.id.compareTo(message.getId()) == 0;
		}
		
		return result;
	}
}
