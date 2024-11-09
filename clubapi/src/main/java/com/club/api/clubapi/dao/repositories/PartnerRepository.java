
package com.club.api.clubapi.dao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.club.api.clubapi.model.Partner;
import com.club.api.clubapi.model.SuscriptionType;
import com.club.api.clubapi.model.User;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {

    public Partner findByUserId(User user);

    public List<Partner> findByType(SuscriptionType type);
}
