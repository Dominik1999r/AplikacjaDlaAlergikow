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
 * Klasa reprezentująca złożony klucz pomiędzy klasami Medicine a MedicineComment.
 */
@Embeddable
public class MedicineCommentId implements Serializable{
	@ManyToOne(cascade = CascadeType.ALL)
	private User user;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Medicine medicine;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	public MedicineCommentId() {
	}
	
	public MedicineCommentId(User user, Medicine medicine) {
		this.user = user;
		this.medicine = medicine;
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}
	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public boolean equals(Object obj) {
		MedicineCommentId that = (MedicineCommentId) obj;
		
		return this.getUser().getId() == that.getUser().getId() && this.getMedicine().getId() == that.getMedicine().getId();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getUser().getId(), this.getMedicine().getId());
	}
}
