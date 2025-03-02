package com.codigo.ms_seguridad.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {

    String extractUsername(String token);

    //user details es una clase de SPRING SECURITY
    String generateToken(UserDetails userDetails);


    boolean validateToken(String token, UserDetails userDetails);


    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);

    boolean isRefreshToken(String token);



}
