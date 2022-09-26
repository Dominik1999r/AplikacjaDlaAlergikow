package com.cielicki.dominik.allergyapprestapi.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Klasa reprezentująca województwo.
 * Na podstawie tej klasy tworzona jest tabela voivodeship.
 */
@Entity
@Table
public class Voivodeship {
	
	public Voivodeship() {
	}
	
	public Voivodeship(Long id) {
		this.id = id;
	}

	@Id
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
