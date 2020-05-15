package com.ppproject.repository;

import com.ppproject.entities.EntityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepoUser extends JpaRepository<EntityUser, Long> {
    Optional<EntityUser> findByLogin(String login);
    Optional<EntityUser> findByEmail(String email);
}
