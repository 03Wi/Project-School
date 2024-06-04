package com.project.school.security.jwt;

import com.project.school.exception.ModelNotFoundException;
import com.project.school.model.User;
import com.project.school.repository.IUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class JwtUserDetailService implements UserDetailsService {

    private final IUserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repo.findByUserName(username);
        if(user == null){
            throw new ModelNotFoundException("User not found :" + username);
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().getName()));

        //This class is native of spring
        return new org.springframework.security.core.userdetails
                    .User(user.getUserName(), user.getPassword(), roles);

    }

}
