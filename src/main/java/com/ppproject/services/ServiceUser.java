package com.ppproject.services;

import com.ppproject.common.CommonService;
import com.ppproject.entitesDTO.UserPostDTO;
import com.ppproject.entities.EntityUser;

import java.util.Optional;

public interface ServiceUser extends CommonService<EntityUser, Long> {
    Optional<EntityUser> findByLogin(String login);
    Optional<EntityUser> findByEmail(String email);
    EntityUser save(UserPostDTO userPostDTO);
}
