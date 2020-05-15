package com.ppproject.services.impl;


import com.ppproject.common.CommonServiceImpl;
import com.ppproject.entitesDTO.GarbagePointPostDTO;
import com.ppproject.entities.EntityGarbageDumpPoint;
import com.ppproject.entities.EntityUser;
import com.ppproject.repository.RepoGarbageDumpPoint;
import com.ppproject.services.ServiceGarbageDumpPoint;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceGarbageDumpPointImpl extends CommonServiceImpl<EntityGarbageDumpPoint, RepoGarbageDumpPoint, Long> implements ServiceGarbageDumpPoint {

    private final ModelMapper modelMapper;

    public ServiceGarbageDumpPointImpl(RepoGarbageDumpPoint repository, ModelMapper modelMapper) {
        super(repository);
        this.modelMapper = modelMapper;
    }

    @Override
    public List<EntityGarbageDumpPoint> findByEntityUser(EntityUser user) {
        return repository.findAllByUser(user);
    }

    @Override
    public List<EntityGarbageDumpPoint> findAllByVerifiedFalse() {
        return repository.findAllByVerifiedFalse();
    }

    @Override
    public EntityGarbageDumpPoint save(GarbagePointPostDTO garbagePoint, EntityUser user) {
        EntityGarbageDumpPoint toSave = modelMapper.map(garbagePoint, EntityGarbageDumpPoint.class);
        toSave.setUser(user);
        return save(toSave);
    }
}
