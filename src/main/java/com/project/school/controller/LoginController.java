package com.project.school.controller;

import com.project.school.security.jwt.JwtGenerateUtil;
import com.project.school.security.jwt.JwtRequest;
import com.project.school.security.jwt.JwtResponse;
import com.project.school.security.jwt.JwtUserDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Profile({"dev", "prod"})
@PreAuthorize("permitAll()")
@Slf4j
@Tag(name = "LOGIN Controller", description = "Authorization of login, username and password")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtGenerateUtil jwtGenerateUtil;
    private final JwtUserDetailService jwtUserDetailService;

    @PostMapping("/school/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) throws Exception {

        authenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = jwtUserDetailService.loadUserByUsername(request.getUsername());
        final String token = jwtGenerateUtil.generateToken(userDetails);
        log.info(token);
        log.info("Problema tecnico");
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch (DisabledException e){
            throw new Exception("USER_DISABLE", e);
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
