package com.ppproject.services;

import com.ppproject.common.CommonService;
import com.ppproject.entitesDTO.GarbagePointPostDTO;
import com.ppproject.entities.EntityGarbageDumpPoint;
import com.ppproject.entities.EntityUser;

import java.util.List;

public interface ServiceGarbageDumpPoint extends CommonService<EntityGarbageDumpPoint, Long> {
    List<EntityGarbageDumpPoint> findByEntityUser(EntityUser user);

    List<EntityGarbageDumpPoint> findAllByVerifiedFalse();

    EntityGarbageDumpPoint save(GarbagePointPostDTO garbagePoint, EntityUser user, String fileUrl);
}
