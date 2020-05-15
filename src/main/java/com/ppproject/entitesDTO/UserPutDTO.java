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
public class UserPutDTO {
    private String password;

    private String name;

    private String lastName;

    private Date birthdate;

    public boolean containsEmptyValue() {
        return password == null || name == null || lastName == null || birthdate == null || password.isEmpty() || name.isEmpty() || lastName.isEmpty();
    }

}
