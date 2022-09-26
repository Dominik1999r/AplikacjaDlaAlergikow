package com.cielicki.dominik.allergyapprestapi.db;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

/**
 * Klasa reprezentująca komentarz o lekarstwie.
 * Na podstawie tej klasy tworzona jest tabela medicine_comment.
 */
@Entity
@AssociationOverrides({
    @AssociationOverride(name = "id.user",
        joinColumns = @JoinColumn(name = "user_id")),
    @AssociationOverride(name = "id.medicine",
        joinColumns = @JoinColumn(name = "medicine_id")) })
public class MedicineComment implements Serializable {
	/**
	 * 
	 */
	private static final Long serialVersionUID = 839069908986053738L;

	@EmbeddedId
	private MedicineCommentId id = new MedicineCommentId();
	
	@Column(nullable = false)
	private BigDecimal rating;
	
	@Column
	private String comment;
	
	public MedicineCommentId getId() {
		return id;
	}

	public void setId(MedicineCommentId id) {
		this.id = id;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
