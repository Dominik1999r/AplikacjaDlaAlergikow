package com.cielicki.dominik.allergyapprestapi.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Klasa reprezentująca temat pytania.
 * Na podstawie tej klasy tworzona jest tabela question.
 */
@Entity
@Table
public class Question implements Serializable {
	private static final Long serialVersionUID = 5083598601690061550L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "designated_user", referencedColumnName = "id")
	private User designatedUser;
	
	@Column(nullable = false)
	private String subject;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public User getDesignatedUser() {
		return designatedUser;
	}

	public void setDesignatedUser(User designatedUser) {
		this.designatedUser = designatedUser;
	}
	
	@Override
		public String toString() {
			return subject;
		}
}
