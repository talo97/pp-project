package com.ppproject.entitesDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserGetDTO {
    private Long id;

    private String login;

    private String name;

    private String lastName;

    private Date birthdate;

    private String email;
}
