package com.ppproject.repository;

import com.ppproject.entities.EntityGarbageDumpPoint;
import com.ppproject.entities.EntityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoGarbageDumpPoint extends JpaRepository<EntityGarbageDumpPoint, Long> {
    List<EntityGarbageDumpPoint> findAllByUser(EntityUser user);
    List<EntityGarbageDumpPoint> findAllByVerifiedFalse();

}
