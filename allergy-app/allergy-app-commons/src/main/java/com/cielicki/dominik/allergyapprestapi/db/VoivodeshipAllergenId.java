package com.cielicki.dominik.allergyapprestapi.db;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Klasa reprezentująca złożony klucz pomiędzy klasami Voivodeship a Allergen.
 */
@Embeddable
public class VoivodeshipAllergenId implements Serializable{
	@ManyToOne(cascade = CascadeType.ALL)
	private Allergen allergen;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Voivodeship voivodeship;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	
	public VoivodeshipAllergenId() {
	}
	
	public VoivodeshipAllergenId(Allergen allergen, Voivodeship voivodeship) {
		this.allergen = allergen;
		this.voivodeship = voivodeship;
	}
	

	public Allergen getAllergen() {
		return allergen;
	}

	public void setAllergen(Allergen allergen) {
		this.allergen = allergen;
	}

	public Voivodeship getVoivodeship() {
		return voivodeship;
	}

	public void setVoivodeship(Voivodeship voivodeship) {
		this.voivodeship = voivodeship;
	}
	
	@Override
	public boolean equals(Object obj) {
		VoivodeshipAllergenId that = (VoivodeshipAllergenId) obj;
		
		return this.getAllergen().getId() == that.getAllergen().getId() && this.getVoivodeship().getId() == that.getVoivodeship().getId();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getAllergen().getId(), this.getVoivodeship().getId());
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
