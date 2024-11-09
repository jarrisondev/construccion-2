package com.club.api.clubapi.dto;

import com.club.api.clubapi.model.Role;

public class UserDto {

	private long id;
	private PersonDto personId;
	private String username;
	private String password;
	private Role role;

	public UserDto() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public PersonDto getPersonId() {
		return personId;
	}

	public void setPersonId(PersonDto personId) {
		this.personId = personId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
