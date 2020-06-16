package com.ppproject.entitesDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GarbagePointGetDTO {
    private Long id;
    private Double latitude;
    private Double longitude;
    private String imageUrl;
    private UserGetDTO user;
    private boolean verified;
}
