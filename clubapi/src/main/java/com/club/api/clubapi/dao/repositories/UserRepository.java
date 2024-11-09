
package com.club.api.clubapi.dao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.club.api.clubapi.model.Role;
import com.club.api.clubapi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUserName(String userName);

    public boolean existsByUserName(String userName);

    public List<User> findByRole(Role role);
}
