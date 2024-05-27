package com.project.school.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtGenerateUtil implements Serializable {


    final long timeValidToken = 5 *60 *60 *1000;

    @Value("${jwt.secret}")
    private String SECRET;


    public String generateToken(UserDetails userDetails){

        Map<String, Object> claims = new HashMap<>();
        claims.put("Roles",(userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining())));

        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + timeValidToken))
                .signWith(getSignKey())
                .compact();

    }

    private Key getSignKey() {

        return new SecretKeySpec(Base64.getDecoder().decode(SECRET),
                                    SignatureAlgorithm.HS512.getJcaName());
    }

    //Validations of token

    private Claims getAllClaimsToken(String token){

        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T  getClaimsFromToken(String token, Function<Claims, T > content) {

        Claims claims = getAllClaimsToken(token);
        return content.apply(claims);
    }

    public String getUserName(String token){

        return getClaimsFromToken(token, Claims::getSubject);
    }

    public Date getDateExpiration(String token){

        return getClaimsFromToken(token, Claims::getExpiration);
    }

    public Boolean verificationExpirationToken(String token){

        return new Date().before(getDateExpiration(token));
    }

    public Boolean validateToken(String token, UserDetails details){
        String userName = getUserName(token);
        return (userName.equalsIgnoreCase
                (details.getUsername()) && verificationExpirationToken(token));
    }

}
