package com.ppproject.entities;

import com.ppproject.common.CommonEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;

@Entity
@Table(name = "garbage_point")
@Setter
@Getter
public class EntityGarbageDumpPoint  extends CommonEntity {

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name="date")
    private Date date = new Date(Calendar.getInstance().getTimeInMillis());

    //TODO::fix this later
    @Column(name="image_url")
    private String imageUrl;

    @Column(name = "verified")
    private boolean verified = false;

    @ManyToOne
    @JoinColumn(name = "entity_user")
    private EntityUser user;





}
