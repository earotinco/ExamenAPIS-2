package com.codigo.ms_seguridad.controller;


import com.codigo.ms_seguridad.aggregates.request.SignInRequest;
import com.codigo.ms_seguridad.aggregates.request.SignUpRequest;
import com.codigo.ms_seguridad.aggregates.response.SignInResponse;
import com.codigo.ms_seguridad.entity.Usuario;
import com.codigo.ms_seguridad.service.AuthenticationService;
import com.codigo.ms_seguridad.service.JwtService;
import com.codigo.ms_seguridad.service.UsuarioService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
@Log4j2
@RefreshScope
public class AuthenticationController {


    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private  final UsuarioService usuarioService;

    @Value("${dato.propiedad}")
    private String valorPropiedad;

    /*REGISTRO DE USUARIO*/
    @PostMapping("/register/user")
    public ResponseEntity <Usuario> signUpUser(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signUpUser(signUpRequest));
    }

    /*REGISTRO DE ADMIN*/
    @PostMapping("/register/admin")
    public ResponseEntity <Usuario> signUpAdmin(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signUpAdmin(signUpRequest));
    }


    /*Validar TOKEN*/
    @GetMapping("/login")
    public ResponseEntity <String> getClave(){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String dato = Base64.getEncoder().encodeToString(key.getEncoded());
        return ResponseEntity.ok(dato);
    }

    @PostMapping ("/signin")
    public ResponseEntity<SignInResponse> signInRequest(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }

    @GetMapping("/prueba")
    public ResponseEntity<String> getSaludo(){
        return ResponseEntity.ok("Hola Admin! ---Probando");
    }


    /*REFRESH TOKEN*/
    @PostMapping("/refreshtoken")
    public ResponseEntity<SignInResponse> refreshToken(
            @RequestParam String refreshToken) throws IllegalAccessException {
        return ResponseEntity.ok(authenticationService.getTokenByRefreshToken(refreshToken));
    }




    /*ENDPOINT PRUEBA CONFIG SERVER*/
    @GetMapping("/prueba2")
    public ResponseEntity<String> getPrueba(){

        return ResponseEntity.ok(valorPropiedad);
    }


    @PostMapping("/validatetoken")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String header) {



        String token = header.replace("Bearer ", "");
        String ussername = jwtService.extractUsername(token);

        UserDetails userDetails = usuarioService.userDetailsService().loadUserByUsername(ussername);

        boolean isValid = jwtService.validateToken(token, userDetails);

        return ResponseEntity.ok(isValid);
    }


    @GetMapping("/user-info")
    public ResponseEntity<Usuario> getUserInfo(@RequestHeader("Authorization") String header) {
        String token = header.replace("Bearer ", "");
        String ussername = jwtService.extractUsername(token);
        Usuario usuario = usuarioService.obtenerUsuarioPorUssername(ussername);
        return ResponseEntity.ok(usuario);
    }

}
