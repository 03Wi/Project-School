package com.project.school.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthServiceImpl {


    public boolean hasAccess(String[] user){

        boolean status;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();
        auth.getAuthorities().forEach(x -> log.info(String.valueOf(x)));
        List<String> rolesList = Arrays.stream(user).toList();
        return auth.getAuthorities()
                .stream()
                .anyMatch(authority -> rolesList.stream()
                        .anyMatch(role -> role.equalsIgnoreCase(authority.getAuthority())));
    }
}
