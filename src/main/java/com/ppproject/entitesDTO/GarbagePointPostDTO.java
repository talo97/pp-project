package com.ppproject.entitesDTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class GarbagePointPostDTO {
    private Double latitude;
    private Double longitude;

}
