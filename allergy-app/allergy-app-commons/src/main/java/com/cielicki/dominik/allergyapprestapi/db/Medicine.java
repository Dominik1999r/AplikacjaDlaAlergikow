package com.cielicki.dominik.allergyapprestapi.db;

import java.io.Serializable;
/**
 * Klasa reprezentująca lekarstwo.
 * Na podstawie tej klasy tworzona jest tabela medicine.
 */
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Klasa reprezentująca lekarstwo.
 * Na podstawie tej klasy tworzona jest tabela medicine.
 */
@Entity
@Table
public class Medicine implements Serializable {
	/**
	 * 
	 */
	private static final Long serialVersionUID = 2689968510657369633L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column
	private BigDecimal priceLow;
	
	@Column
	private BigDecimal priceHigh;
	
	@Column
	private String description;
	
	@Transient
	private BigDecimal averageScore;
	
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

	public BigDecimal getPriceLow() {
		return priceLow;
	}

	public void setPriceLow(BigDecimal priceLow) {
		this.priceLow = priceLow;
	}
	
	public BigDecimal getPriceHigh() {
		return priceHigh;
	}

	public void setPriceHigh(BigDecimal priceHigh) {
		this.priceHigh = priceHigh;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(BigDecimal averageScore) {
		this.averageScore = averageScore;
	}
}
