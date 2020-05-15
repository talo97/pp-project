package com.ppproject.controllers;

import com.ppproject.entitesDTO.GarbagePointGetDTO;
import com.ppproject.entitesDTO.GarbagePointPostDTO;
import com.ppproject.entitesDTO.UserGetDTO;
import com.ppproject.entities.EntityGarbageDumpPoint;
import com.ppproject.entities.EntityUser;
import com.ppproject.services.ServiceGarbageDumpPoint;
import com.ppproject.services.ServiceUser;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class GarbageDumpPointController {

    private final ModelMapper modelMapper;
    private final ServiceGarbageDumpPoint serviceGarbageDumpPoint;
    private final ServiceUser serviceUser;

    public GarbageDumpPointController(ModelMapper modelMapper, ServiceGarbageDumpPoint serviceGarbageDumpPoint, ServiceUser serviceUser) {
        this.modelMapper = modelMapper;
        this.serviceGarbageDumpPoint = serviceGarbageDumpPoint;
        this.serviceUser = serviceUser;
    }
    private Optional<EntityUser> getUserFromToken() {
        return serviceUser.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PostMapping("/garbagePoints")
    public ResponseEntity<GarbagePointGetDTO> addGarbagePoint(@Valid @RequestBody GarbagePointPostDTO garbagePoint){
        EntityUser user = getUserFromToken().get();
        GarbagePointGetDTO garbagePointGetDTO = modelMapper.map(serviceGarbageDumpPoint.save(garbagePoint, user), GarbagePointGetDTO.class);
        garbagePointGetDTO.setUser(modelMapper.map(user, UserGetDTO.class));
        return ResponseEntity.ok(garbagePointGetDTO);
    }

    @GetMapping("/garbagePoints")
    public ResponseEntity<List<GarbagePointGetDTO>> getGarbagePoints() {
        List<GarbagePointGetDTO> lst = new ArrayList<>();
        serviceGarbageDumpPoint.getAll().forEach(e -> {
            GarbagePointGetDTO temp = modelMapper.map(e, GarbagePointGetDTO.class);
            temp.setUser(modelMapper.map(e.getUser(), UserGetDTO.class));
            lst.add(temp);
        });
        return ResponseEntity.ok(lst);
    }

    @GetMapping("/garbagePoints/{id}")
    public ResponseEntity<GarbagePointGetDTO> getGarbagePoint(@Valid @PathVariable Long id) {
        Optional<EntityGarbageDumpPoint> dumpPoint = serviceGarbageDumpPoint.get(id);
        if (dumpPoint.isPresent()) {
            GarbagePointGetDTO temp = modelMapper.map(dumpPoint.get(), GarbagePointGetDTO.class);
            temp.setUser(modelMapper.map(dumpPoint.get().getUser(), UserGetDTO.class));
            return ResponseEntity.ok(temp);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/userGarbagePoints")
    public ResponseEntity<List<GarbagePointGetDTO>> getCurrentUserGarbagePoints() {
        Optional<EntityUser> currentUser = getUserFromToken();
        List<GarbagePointGetDTO> lst = new ArrayList<>();
        currentUser.ifPresent(user->{
            serviceGarbageDumpPoint.findByEntityUser(user).forEach(dumpPoint->{
                GarbagePointGetDTO temp = modelMapper.map(dumpPoint, GarbagePointGetDTO.class);
                temp.setUser(modelMapper.map(dumpPoint.getUser(), UserGetDTO.class));
                lst.add(temp);
            });
        });
        return ResponseEntity.ok(lst);
    }

    @GetMapping("/unverifiedGarbagePoints")
    public ResponseEntity<List<GarbagePointGetDTO>> getUnverifiedGarbagePoints(){
        List<GarbagePointGetDTO> lst = new ArrayList<>();
        serviceGarbageDumpPoint.findAllByVerifiedFalse().forEach(e -> {
            GarbagePointGetDTO temp = modelMapper.map(e, GarbagePointGetDTO.class);
            temp.setUser(modelMapper.map(e.getUser(), UserGetDTO.class));
            lst.add(temp);
        });
        return ResponseEntity.ok(lst);
    }

}
