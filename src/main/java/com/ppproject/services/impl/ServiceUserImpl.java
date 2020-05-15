package com.ppproject.services.impl;

import com.ppproject.common.CommonServiceImpl;
import com.ppproject.common.EnumUserGroup;
import com.ppproject.entitesDTO.UserPostDTO;
import com.ppproject.entities.EntityUser;
import com.ppproject.repository.RepoUser;
import com.ppproject.services.ServiceUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceUserImpl extends CommonServiceImpl<EntityUser, RepoUser, Long> implements ServiceUser {

    private final ModelMapper modelMapper;

    public ServiceUserImpl(RepoUser repository, ModelMapper modelMapper) {
        super(repository);
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<EntityUser> findByLogin(String login) {
        return repository.findByLogin(login);
    }

    @Override
    public Optional<EntityUser> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public EntityUser save(UserPostDTO userPostDTO) {
        EntityUser user = modelMapper.map(userPostDTO, EntityUser.class);
        user.setPoints(0L);
        user.setUserGroup(EnumUserGroup.DEFAULT);
        return repository.save(user);
    }
}
