
package com.club.api.clubapi.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.club.api.clubapi.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

    public boolean existsByDocument(long document);

    public Person findByDocument(long document);

}
