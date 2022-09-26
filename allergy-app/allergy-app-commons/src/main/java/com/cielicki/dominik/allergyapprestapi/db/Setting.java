package com.cielicki.dominik.allergyapprestapi.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Klasa reprezentująca ustawienie.
 * Na podstawie tej klasy tworzona jest tabela setting.
 */
@Entity
@Table
public class Setting implements Serializable {
	/**
	 * 
	 */
	private static final Long serialVersionUID = -6649383014519537791L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String name;
	
	@Column
	private String defaultValue;
	
	@OneToOne
	@JoinColumn(name = "category_id", referencedColumnName = "id")
	private SettingCategory settingCategory;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public SettingCategory getSettingCategory() {
		return settingCategory;
	}

	public void setSettingCategory(SettingCategory settingCategory) {
		this.settingCategory = settingCategory;
	}
}
