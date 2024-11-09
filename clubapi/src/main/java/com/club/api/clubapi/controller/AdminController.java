package com.club.api.clubapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	List<UserDto> listPartners() throws Exception {
		return this.service.listByRole(Role.PARTNER);
	}

	@PostMapping("/partners")
	String createPartner(@RequestBody @Validated PersonRequestDto personRequest) throws Exception {

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
			return "se ha creado el usuario exitosamente";
		} catch (Exception e) {
			return e.getMessage();
		}

	}

	@DeleteMapping("/partners")
	String deletePartner(@RequestBody @Validated long id) throws Exception {
		UserDto userDto = new UserDto();
		userDto.setId(id);

		try {
			this.service.deletePartner(userDto);
			return "se ha eliminado el usuario exitosamente";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
