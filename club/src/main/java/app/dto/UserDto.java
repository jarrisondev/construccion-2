package app.dto;

import app.model.Person;

public class UserDto {
	private long id;
	private Person personId;
	private String username;
	private String password;
	private String role;

	public UserDto() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Person getPersonid() {
		return personId;
	}

	public void setPersonid(Person personid) {
		this.personId = personid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
