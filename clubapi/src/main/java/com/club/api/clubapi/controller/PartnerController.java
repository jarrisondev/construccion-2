package com.club.api.clubapi.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.club.api.clubapi.controller.requestDto.PersonRequestDto;
import com.club.api.clubapi.dto.GuestDto;
import com.club.api.clubapi.dto.PersonDto;
import com.club.api.clubapi.dto.UserDto;
import com.club.api.clubapi.model.GuestStatus;
import com.club.api.clubapi.model.Role;
import com.club.api.clubapi.service.ClubService;

@RestController
public class PartnerController {
	final private ClubService service;

	public PartnerController() {
		this.service = new ClubService();
	}

	@GetMapping("/guests")
	List<UserDto> listGuests() throws Exception {
		return this.service.listByRole(Role.GUEST);
	}

	@PostMapping("/guests")
	String createGuest(@RequestBody @Validated PersonRequestDto personRequest) throws Exception {
		PersonDto personDto = new PersonDto();
		personDto.setDocument(personRequest.getDocument());
		personDto.setName(personRequest.getName());
		personDto.setCellPhone(personRequest.getCellPhone());

		UserDto userDto = new UserDto();
		userDto.setPersonId(personDto);
		userDto.setUsername(personRequest.getUsername());
		userDto.setPassword(personRequest.getPassword());
		userDto.setRole(Role.GUEST);

		GuestDto guestDto = new GuestDto();
		guestDto.setUserId(userDto);
		guestDto.setStatus(GuestStatus.INACTIVE);

		this.service.createGuest(guestDto);

		return "se ha creado el usuario exitosamente";
	}

	@GetMapping("/guests/changeStatus")
	String changeStatusGuest(
			@RequestBody @Validated long id) throws Exception {
		UserDto userDto = new UserDto();
		userDto.setId(id);

		this.service.updateGuestStatus(userDto);
		return "se ha cambiado el estado del invitado exitosamente";
	}
}
