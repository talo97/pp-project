package com.ppproject.controllers;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.ppproject.common.AmazonClient;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
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
    private AmazonClient amazonClient;

    public GarbageDumpPointController(ModelMapper modelMapper, ServiceGarbageDumpPoint serviceGarbageDumpPoint, ServiceUser serviceUser, AmazonClient amazonClient) {
        this.modelMapper = modelMapper;
        this.serviceGarbageDumpPoint = serviceGarbageDumpPoint;
        this.serviceUser = serviceUser;
        this.amazonClient = amazonClient;
    }

    private Optional<EntityUser> getUserFromToken() {
        return serviceUser.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    //@RequestMapping(value = "/garbagePoints", method = RequestMethod.POST, headers = ("content-type=multipart/form-data;boundary=059h2BBM-KlM_XP2rY8W1X3_jnzFLcYY;charset=UTF-8") )
    //public ResponseEntity<GarbagePointGetDTO> addGarbagePoint(@Valid @ModelAttribute GarbagePointPostDTO garbagePoint, @Valid @RequestPart MultipartFile image) throws IOException {
    @PostMapping("/garbagePoints")
    public ResponseEntity<?> addGarbagePoint(@RequestParam("image") MultipartFile image) throws IOException {
//        EntityUser user = getUserFromToken().get();
//        String fileUrl = "";
//        try {
//            fileUrl = amazonClient.uploadFile(image);
//        } catch (AmazonServiceException ase) {
//            System.out.println("Caught an AmazonServiceException from GET requests, rejected reasons:");
//            System.out.println("Error Message:    " + ase.getMessage());
//            System.out.println("HTTP Status Code: " + ase.getStatusCode());
//            System.out.println("AWS Error Code:   " + ase.getErrorCode());
//            System.out.println("Error Type:       " + ase.getErrorType());
//            System.out.println("Request ID:       " + ase.getRequestId());
//            return ResponseEntity.badRequest().build();
//        } catch (AmazonClientException ace) {
//            System.out.println("Caught an AmazonClientException: ");
//            System.out.println("Error Message: " + ace.getMessage());
//            return ResponseEntity.badRequest().build();
//        } catch (IOException ioe) {
//            System.out.println("IOE Error Message: " + ioe.getMessage());
//            return ResponseEntity.badRequest().build();
//        }
//        GarbagePointGetDTO garbagePointGetDTO = modelMapper.map(serviceGarbageDumpPoint.save(garbagePoint, user, fileUrl), GarbagePointGetDTO.class);
//        garbagePointGetDTO.setUser(modelMapper.map(user, UserGetDTO.class));
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/garbagePoints")
    public ResponseEntity<List<GarbagePointGetDTO>> getGarbagePoints() {
        List<GarbagePointGetDTO> lst = new ArrayList<>();
        serviceGarbageDumpPoint.findAllByVerifiedTrue().forEach(e -> {
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
        currentUser.ifPresent(user -> {
            serviceGarbageDumpPoint.findByEntityUser(user).forEach(dumpPoint -> {
                GarbagePointGetDTO temp = modelMapper.map(dumpPoint, GarbagePointGetDTO.class);
                temp.setUser(modelMapper.map(dumpPoint.getUser(), UserGetDTO.class));
                lst.add(temp);
            });
        });
        return ResponseEntity.ok(lst);
    }

    @GetMapping("/unverifiedGarbagePoints")
    public ResponseEntity<List<GarbagePointGetDTO>> getUnverifiedGarbagePoints() {
        List<GarbagePointGetDTO> lst = new ArrayList<>();
        serviceGarbageDumpPoint.findAllByVerifiedFalse().forEach(e -> {
            GarbagePointGetDTO temp = modelMapper.map(e, GarbagePointGetDTO.class);
            temp.setUser(modelMapper.map(e.getUser(), UserGetDTO.class));
            lst.add(temp);
        });
        return ResponseEntity.ok(lst);
    }

    @DeleteMapping("/garbagePoint/{id}")
    public ResponseEntity<?> deleteGarbagePoint(@Valid @PathVariable Long id) {
        Optional<EntityGarbageDumpPoint> garbageDumpPoint = serviceGarbageDumpPoint.get(id);
        return garbageDumpPoint.map(entityGarbageDumpPoint -> {
            serviceGarbageDumpPoint.delete(entityGarbageDumpPoint);
            return ResponseEntity.ok("Successfully deleted");
        }).orElse(ResponseEntity.badRequest().body("There is no garbage point with given ID"));
    }

    @PutMapping("verifyGarbagePoint/{garbagePointId}")
    public ResponseEntity<?> verifyGarbagePoint(@Valid @PathVariable Long garbagePointId) {
        Optional<EntityGarbageDumpPoint> garbageDumpPoint = serviceGarbageDumpPoint.get(garbagePointId);
        return garbageDumpPoint.map(entityGarbageDumpPoint -> {
            entityGarbageDumpPoint.setVerified(true);
            serviceGarbageDumpPoint.save(entityGarbageDumpPoint);
            return ResponseEntity.ok("Successfully verified");
        }).orElse(ResponseEntity.badRequest().body("There is no garbage point with given ID"));
    }

}
