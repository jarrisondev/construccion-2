package com.club.api.clubapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.club.api.clubapi.Utils.Utils;
import com.club.api.clubapi.controller.requestDto.PersonRequestDto;
import com.club.api.clubapi.dto.PartnerDto;
import com.club.api.clubapi.dto.PersonDto;
import com.club.api.clubapi.dto.UserDto;
import com.club.api.clubapi.model.Role;
import com.club.api.clubapi.model.SuscriptionType;
import com.club.api.clubapi.service.ClubService;

import lombok.NoArgsConstructor;

@RestController
@NoArgsConstructor
public class AdminController {
	@Autowired
	ClubService service;

	@GetMapping("/partners")
	ResponseEntity<?> listPartners() throws Exception {
		try {
			return ResponseEntity.ok(this.service.listByRole(Role.PARTNER));

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/guests")
	ResponseEntity<?> listGuests() throws Exception {
		try {
			return ResponseEntity.ok(this.service.listByRole(Role.GUEST));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/partners")
	ResponseEntity<?> createPartner(@RequestBody @Validated PersonRequestDto personRequest) throws Exception {

		PersonDto personDto = new PersonDto();
		personDto.setName(personRequest.getName());
		personDto.setDocument(personRequest.getDocument());
		personDto.setCellPhone(personRequest.getCellPhone());

		UserDto userDto = new UserDto();
		userDto.setPersonId(personDto);
		userDto.setUsername(personRequest.getUsername());
		userDto.setPassword(personRequest.getPassword());
		userDto.setRole(Role.PARTNER);

		PartnerDto partnerDto = new PartnerDto();
		partnerDto.setUserId(userDto);
		partnerDto.setAmount(0);
		partnerDto.setType(SuscriptionType.REGULAR);
		partnerDto.setCreationDate(Utils.getCurrentDate());

		try {
			this.service.createPartner(partnerDto);
			return ResponseEntity.ok("se ha creado el usuario exitosamente");
		} catch (Exception e) {

			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@DeleteMapping("/persons/{id}")
	ResponseEntity<?> deletePerson(@PathVariable("id") long id) throws Exception {
		try {
			this.service.deletePerson(id);
			return ResponseEntity.ok("se ha eliminado el usuario exitosamente");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
