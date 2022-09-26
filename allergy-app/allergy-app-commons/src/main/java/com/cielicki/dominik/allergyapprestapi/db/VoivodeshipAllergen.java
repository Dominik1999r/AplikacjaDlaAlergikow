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
 * Klasa reprezentująca ilośc allergenu w województwie.
 * Na podstawie tej klasy tworzona jest tabela voivodeship_allergen.
 */
@Entity
@AssociationOverrides({
    @AssociationOverride(name = "id.allergen",
        joinColumns = @JoinColumn(name = "allergen_id")),
    @AssociationOverride(name = "id.voivodeship",
        joinColumns = @JoinColumn(name = "voivodeship_id")) })
public class VoivodeshipAllergen implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6952106664754444699L;
	
	@EmbeddedId
	private VoivodeshipAllergenId id;
	
	@Column(nullable = false)
	private BigDecimal value;

	public VoivodeshipAllergenId getId() {
		return id;
	}

	public void setId(VoivodeshipAllergenId id) {
		this.id = id;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
}
