
package com.club.api.clubapi.dao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.club.api.clubapi.model.Guest;
import com.club.api.clubapi.model.Partner;
import com.club.api.clubapi.model.User;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    public List<Guest> findByPartnerId(Partner partner);

    public Guest findByUserId(User user);

}
