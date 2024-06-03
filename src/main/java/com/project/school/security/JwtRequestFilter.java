package com.project.school.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Profile(value = {"dev", "prod"})
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtGenerateUtil jwtGenerateUtil;
    private final JwtUserDetailService jwtUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {
        String username = null;
        String token = null;

        final String tokenHeader = request.getHeader("Authorization");

        if (isNotNull(tokenHeader)) {
            if (tokenHeader.startsWith("bearer") || tokenHeader.startsWith("Bearer")){
                token = tokenHeader.split(" ")[1];
                try {
                     username = jwtGenerateUtil.getUserName(token);
                }catch (Exception e){
                    request.setAttribute("Exception", e.getMessage());
                }
            }
        }

        if (isNotNull(username)){

            UserDetails details = jwtUserDetailService.loadUserByUsername(username);
            if (jwtGenerateUtil.validateToken(token, details)){

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(details.getUsername(), null, details.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);

    }

    private <T> boolean isNotNull(T t){

        return t != null;
    }

}
