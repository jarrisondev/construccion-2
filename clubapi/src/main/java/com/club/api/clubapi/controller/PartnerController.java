package com.club.api.clubapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.club.api.clubapi.controller.requestDto.GuestRequestDto;
import com.club.api.clubapi.dto.GuestDto;
import com.club.api.clubapi.dto.PartnerDto;
import com.club.api.clubapi.dto.PersonDto;
import com.club.api.clubapi.dto.UserDto;
import com.club.api.clubapi.model.GuestStatus;
import com.club.api.clubapi.model.Role;
import com.club.api.clubapi.service.ClubService;

@RestController
public class PartnerController {
	@Autowired
	ClubService service;

	@GetMapping("/guests")
	ResponseEntity<?> listGuests() throws Exception {
		try {
			return ResponseEntity.ok(this.service.listByRole(Role.GUEST));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/guests")
	ResponseEntity<?> createGuest(@RequestBody @Validated GuestRequestDto personRequest) throws Exception {
		PersonDto personDto = new PersonDto();
		personDto.setDocument(personRequest.getDocument());
		personDto.setName(personRequest.getName());
		personDto.setCellPhone(personRequest.getCellPhone());

		UserDto userDto = new UserDto();
		userDto.setPersonId(personDto);
		userDto.setUsername(personRequest.getUsername());
		userDto.setPassword(personRequest.getPassword());
		userDto.setRole(Role.GUEST);

		PartnerDto partnerDto = new PartnerDto();
		partnerDto.setId(personRequest.getPartnerId());

		GuestDto guestDto = new GuestDto();
		guestDto.setUserId(userDto);
		guestDto.setPartnerId(partnerDto);
		guestDto.setStatus(GuestStatus.INACTIVE);

		try {
			this.service.createGuest(guestDto);
			return ResponseEntity.ok("se ha creado el usuario exitosamente");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@GetMapping("/guests/changeStatus")
	ResponseEntity<?> changeStatusGuest(
			@RequestBody @Validated long id) throws Exception {
		UserDto userDto = new UserDto();
		userDto.setId(id);

		try {
			this.service.updateGuestStatus(userDto);
			return ResponseEntity.ok("se ha cambiado el estado del invitado exitosamente");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}
}
