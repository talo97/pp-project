package com.ppproject.entitesDTO;

import java.io.Serializable;

public class JwtResponseDTO implements Serializable {

    private final String jwttoken;

    public JwtResponseDTO(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }
}
