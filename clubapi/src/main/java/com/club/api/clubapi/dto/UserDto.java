package com.club.api.clubapi.dto;

import com.club.api.clubapi.model.Role;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
public class UserDto {

	private long id;
	private PersonDto personId;
	private String username;
	private String password;
	private Role role;
}
