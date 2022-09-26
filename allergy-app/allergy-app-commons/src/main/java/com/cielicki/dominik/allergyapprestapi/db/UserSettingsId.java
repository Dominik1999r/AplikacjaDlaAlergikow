package com.cielicki.dominik.allergyapprestapi.db;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * Klasa reprezentująca złożony klucz pomiędzy klasami Setting a User.
 */
@Embeddable
public class UserSettingsId implements Serializable{
	@ManyToOne(cascade = CascadeType.ALL)
	private User user;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Setting setting;
	
	public UserSettingsId() {
	}
	
	public UserSettingsId(User user, Setting setting) {
		this.user = user;
		this.setting = setting;
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}
	
	@Override
	public boolean equals(Object obj) {
		UserSettingsId that = (UserSettingsId) obj;
		
		return this.getUser().getId() == that.getUser().getId() && this.getSetting().getId() == that.getSetting().getId();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getUser().getId(), this.getSetting().getId());
	}
}
