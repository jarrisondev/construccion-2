
package com.club.api.clubapi.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.api.clubapi.dao.repositories.PersonRepository;
import com.club.api.clubapi.dto.PersonDto;
import com.club.api.clubapi.model.Person;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Service
@NoArgsConstructor

public class PersonDao {

	@Autowired
	public PersonRepository personRepository;

	public boolean existsByDocument(PersonDto personDto) throws Exception {
		return personRepository.existsByDocument(personDto.getDocument());
	}

	public void createPerson(PersonDto personDto) throws Exception {
		Person person = Helper.parse(personDto);
		personRepository.save(person);
	}

	public void deletePerson(long id) throws Exception {
		personRepository.deleteById(id);
	}

	public PersonDto findByDocument(PersonDto personDto) throws Exception {
		Person person = personRepository.findByDocument(personDto.getDocument());
		if (person == null) {
			return null;
		}
		return Helper.parse(person);
	}
}
