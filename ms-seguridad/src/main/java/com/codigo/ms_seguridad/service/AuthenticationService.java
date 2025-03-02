package com.codigo.ms_seguridad.service;

import com.codigo.ms_seguridad.aggregates.request.SignInRequest;
import com.codigo.ms_seguridad.aggregates.request.SignUpRequest;
import com.codigo.ms_seguridad.aggregates.response.SignInResponse;
import com.codigo.ms_seguridad.entity.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthenticationService {

    //Registrar USUARIO
    Usuario signUpUser (SignUpRequest signUpRequest);

    //Registrar ADMIN
    Usuario signUpAdmin (SignUpRequest signUpRequest);




    //Meotodo para login
    SignInResponse signIn(SignInRequest signInRequest);

    SignInResponse getTokenByRefreshToken(String refreshToken) throws IllegalAccessException;
}
