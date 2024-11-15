package com.club.api.clubapi.dao;

import com.club.api.clubapi.dto.GuestDto;
import com.club.api.clubapi.dto.PartnerDto;
import com.club.api.clubapi.dto.PersonDto;
import com.club.api.clubapi.dto.UserDto;
import com.club.api.clubapi.model.Guest;
import com.club.api.clubapi.model.Partner;
import com.club.api.clubapi.model.Person;
import com.club.api.clubapi.model.User;

public abstract class Helper {

    public static PersonDto parse(Person person) {
        if (person == null) {
            return null;
        }
        PersonDto personDto = new PersonDto();
        personDto.setId(person.getId());
        personDto.setDocument(person.getDocument());
        personDto.setName(person.getName());
        personDto.setCellPhone(person.getCellPhone());
        return personDto;
    }

    public static Person parse(PersonDto personDto) {
        if (personDto == null) {
            return null;
        }
        Person person = new Person();
        person.setId(personDto.getId());
        person.setDocument(personDto.getDocument());
        person.setName(personDto.getName());
        person.setCellPhone(personDto.getCellPhone());
        return person;
    }

    public static UserDto parse(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());
        userDto.setUsername(user.getUserName());
        userDto.setPersonId(parse(user.getPersonId()));
        return userDto;
    }

    public static User parse(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setId(userDto.getId());
        user.setPassword(userDto.getPassword());
        user.setPersonId(parse(userDto.getPersonId()));
        user.setRole(userDto.getRole());
        user.setUserName(userDto.getUsername());
        return user;
    }

    public static PartnerDto parse(Partner partner) {
        if (partner == null) {
            return null;
        }
        PartnerDto partnerDto = new PartnerDto();
        partnerDto.setId(partner.getId());
        partnerDto.setAmount(partner.getAmount());
        partnerDto.setType(partner.getType());
        partnerDto.setCreationDate(partner.getCreationDate());
        partnerDto.setUserId(parse(partner.getUserId()));
        return partnerDto;
    }

    public static Partner parse(PartnerDto partnerDto) {
        if (partnerDto == null) {
            return null;
        }
        Partner partner = new Partner();
        partner.setId(partnerDto.getId());
        partner.setAmount(partnerDto.getAmount());
        partner.setType(partnerDto.getType());
        partner.setCreationDate(partnerDto.getCreationDate());
        partner.setUserId(parse(partnerDto.getUserId()));
        return partner;
    }

    public static GuestDto parse(Guest guest) {
        if (guest == null) {
            return null;
        }
        GuestDto guestDto = new GuestDto();
        guestDto.setId(guest.getId());
        guestDto.setStatus(guest.getStatus());
        guestDto.setUserId(parse(guest.getUserId()));
        guestDto.setPartnerId(parse(guest.getPartnerId()));
        return guestDto;
    }

    public static Guest parse(GuestDto guestDto) {
        if (guestDto == null) {
            return null;
        }
        Guest guest = new Guest();
        guest.setId(guestDto.getId());
        guest.setStatus(guestDto.getStatus());
        guest.setUserId(parse(guestDto.getUserId()));
        guest.setPartnerId(parse(guestDto.getPartnerId()));
        return guest;
    }
}
