package com.ppproject.entities;

import com.ppproject.common.CommonEntity;
import com.ppproject.common.EnumUserGroup;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "entity_user")
@Setter
@Getter
public class EntityUser extends CommonEntity {

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "user_group")
    private EnumUserGroup userGroup;

    @Column(name = "points")
    private Long points;

}
