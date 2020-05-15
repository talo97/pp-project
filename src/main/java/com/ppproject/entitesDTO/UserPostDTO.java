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
public class UserPostDTO {

    private String login;

    private String password;

    private String name;

    private String lastName;

    private Date birthdate;

    private String email;

    public boolean containsEmptyValue() {
        return login == null || password == null || email == null || login.isEmpty() || password.isEmpty() || email.isEmpty();
    }
}
