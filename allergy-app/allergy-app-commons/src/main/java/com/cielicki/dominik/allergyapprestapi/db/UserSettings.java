package com.cielicki.dominik.allergyapprestapi.db;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

/**
 * Klasa reprezentująca ustawienie użytkownika.
 * Na podstawie tej klasy tworzona jest user_settings.
 */
@Entity
@AssociationOverrides({
    @AssociationOverride(name = "id.user",
        joinColumns = @JoinColumn(name = "user_id")),
    @AssociationOverride(name = "id.setting",
        joinColumns = @JoinColumn(name = "setting_id")) })
public class UserSettings implements Serializable {
	/**
	 * 
	 */
	private static final Long serialVersionUID = -3232970619739065224L;

	@EmbeddedId
	private UserSettingsId id = new UserSettingsId();
	
	@Column(nullable = false)
	private String value;

	
	public UserSettingsId getId() {
		return id;
	}

	public void setId(UserSettingsId id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
