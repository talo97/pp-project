package com.ppproject.controllers;

import com.ppproject.entitesDTO.UserGetDTO;
import com.ppproject.entitesDTO.UserPostDTO;
import com.ppproject.entitesDTO.UserPutDTO;
import com.ppproject.entities.EntityUser;
import com.ppproject.services.ServiceUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {

    private final ServiceUser serviceUser;
    private final ModelMapper modelMapper;

    public UserController(ServiceUser serviceUser, ModelMapper modelMapper) {
        this.serviceUser = serviceUser;
        this.modelMapper = modelMapper;
    }

    private boolean isLoginTaken(String login) {
        return serviceUser.findByLogin(login).isPresent();
    }

    private boolean isEmailTaken(String email) {
        return serviceUser.findByEmail(email).isPresent();
    }

    private Optional<EntityUser> getUserFromToken() {
        return serviceUser.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[a-zA-Z\\d].*)[a-zA-Z\\d!@#$%&*]{5,}$", Pattern.CASE_INSENSITIVE);

    private final Pattern VALID_LOGIN_REGEX = Pattern.compile("^[a-z0-9_-]{3,25}$", Pattern.CASE_INSENSITIVE);

    private boolean validateLogin(String login) {
        Matcher matcher = VALID_LOGIN_REGEX.matcher(login);
        return matcher.matches();
    }

    private boolean validateEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.matches();
    }

    private boolean validatePassword(String password) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.matches();
    }

    private boolean validateUser(String login, String email, String password) {
        return validateEmail(email) && validatePassword(password) && validateLogin(login);
    }

    @PostMapping("/users")
    public ResponseEntity<UserGetDTO> addUser(@Valid @RequestBody UserPostDTO userPostDTO) {
        if (userPostDTO.containsEmptyValue() || isEmailTaken(userPostDTO.getEmail()) || isLoginTaken(userPostDTO.getLogin())
                || !validateUser(userPostDTO.getLogin(), userPostDTO.getEmail(), userPostDTO.getPassword())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(modelMapper.map(serviceUser.save(userPostDTO), UserGetDTO.class));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserGetDTO>> getUsers() {
        return ResponseEntity.ok(modelMapper.map(serviceUser.getAll(), new TypeToken<List<UserGetDTO>>() {
        }.getType()));
    }

    @GetMapping("/currentUser")
    public ResponseEntity<UserGetDTO> getCurrentUser() {
        return ResponseEntity.ok().body(modelMapper.map(getUserFromToken(), UserGetDTO.class));
    }

    @PutMapping("/users")
    public ResponseEntity<UserGetDTO> editUser(@Valid @RequestBody UserPutDTO userPutDTO) {
        if (userPutDTO.containsEmptyValue()||!validatePassword(userPutDTO.getPassword())) {
            return ResponseEntity.badRequest().build();
        }
        Optional<EntityUser> user = getUserFromToken();
        user.ifPresent(e -> {
            e.setBirthdate(userPutDTO.getBirthdate());
            e.setLastName(userPutDTO.getLastName());
            e.setName(userPutDTO.getName());
            e.setPassword(userPutDTO.getPassword());
            serviceUser.save(e);
        });
        return ResponseEntity.ok(modelMapper.map(user.get(), UserGetDTO.class));
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<UserGetDTO> getUser(@Valid @PathVariable Long id){
        Optional<EntityUser> user = serviceUser.get(id);
        if(user.isPresent()){
            return ResponseEntity.ok(modelMapper.map(user.get(), UserGetDTO.class));
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

}
