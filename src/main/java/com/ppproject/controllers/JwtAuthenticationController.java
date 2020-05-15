package com.ppproject.controllers;

import com.ppproject.entitesDTO.JwtRequestDTO;
import com.ppproject.entitesDTO.JwtResponseDTO;
import com.ppproject.secuityJWT.JwtTokenUtil;
import com.ppproject.secuityJWT.JwtUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final JwtUserDetailsService userDetailsService;

    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * used to log in
     *
     * @param authenticationRequest username and password
     * @return JWT token
     * @throws Exception disabled/invalid credentials
     */
    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestDTO authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getLogin(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getLogin());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponseDTO(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
