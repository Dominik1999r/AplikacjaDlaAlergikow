package com.cielicki.dominik.allergyapprestapi.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Klasa reprezentująca kategorię ustawień.
 * Na podstawie tej klasy tworzona jest tabela setting_category.
 */
@Entity
@Table
public class SettingCategory implements Serializable {
	/**
	 * 
	 */
	private static final Long serialVersionUID = 8083288389720223017L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String category;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
