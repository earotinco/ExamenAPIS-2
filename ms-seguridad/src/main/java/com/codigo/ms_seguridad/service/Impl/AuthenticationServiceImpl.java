package com.codigo.ms_seguridad.service.Impl;

import com.codigo.ms_seguridad.aggregates.constants.Constants;
import com.codigo.ms_seguridad.aggregates.request.SignInRequest;
import com.codigo.ms_seguridad.aggregates.request.SignUpRequest;
import com.codigo.ms_seguridad.aggregates.response.SignInResponse;
import com.codigo.ms_seguridad.entity.Rol;
import com.codigo.ms_seguridad.entity.Role;
import com.codigo.ms_seguridad.entity.Usuario;
import com.codigo.ms_seguridad.repository.RolRepository;
import com.codigo.ms_seguridad.repository.UsuarioRepository;
import com.codigo.ms_seguridad.service.AuthenticationService;
import com.codigo.ms_seguridad.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.hibernate.query.sqm.tree.SqmNode.log;


@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationServiceImpl implements AuthenticationService {

   private  final UsuarioRepository usuarioRepository;
   private final RolRepository rolRepository;
   private final AuthenticationManager authenticationManager;
   private  final JwtService jwtService;


    @Override
    public Usuario signUpUser(SignUpRequest signUpRequest) {

        Usuario usuario = getUsuarioEntity(signUpRequest);
        usuario.setRoles(Collections.singleton(getRoles(Role.USER)));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario signUpAdmin(SignUpRequest signUpRequest) {
        Usuario usuario = getUsuarioEntity(signUpRequest);
        usuario.setRoles(Collections.singleton(getRoles(Role.ADMIN)));
        return usuarioRepository.save(usuario);
    }





    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {

        //autenticamos
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUssername(), signInRequest.getPassword()));

        var user = usuarioRepository.findByUssername(signInRequest.getUssername()).orElseThrow(
                ()->new UsernameNotFoundException("Error usuario no encontrado"));

        var token =  jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);

        return SignInResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public SignInResponse getTokenByRefreshToken(String refreshToken) throws IllegalAccessException {

        log.info("Ejecutando - getTokenByRefreshToken");
        if(!jwtService.isRefreshToken(refreshToken)){
            throw new RuntimeException("Error el token ingresado no es un REFRESH ");
        }

        String ussername = jwtService.extractUsername(refreshToken); /******/


        Usuario usuario = usuarioRepository.findByUssername(ussername).orElseThrow(
                ()-> new UsernameNotFoundException("Error ---- usuario no encontrado"));

        if(!jwtService.validateToken(refreshToken, usuario)){
            throw new IllegalAccessException("Error ---- el token no le pertenece al usuario");
        }
        //GENERAR EL ACCESSTOKEN
        String newToken = jwtService.generateToken(usuario);
        return SignInResponse.builder()
                .token(newToken)
                .refreshToken(refreshToken)
                .build();
    }



    /*@Override
    public List<Usuario> obtenerUsuarioPorRol(String nombreRol) {
        return List.of();
    }*/

    // BUILDER
    private Usuario getUsuarioEntity(SignUpRequest signUpRequest){
        return Usuario.builder()
                .nombres(signUpRequest.getNombres())
                .apellidos(signUpRequest.getApellidos())
                .ussername(signUpRequest.getUssername())
                .password(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()) )
                .tipoDoc(signUpRequest.getTipoDoc())
                .numDoc(signUpRequest.getNumDoc())
                .isAccountNonExpired(Constants.STATUS_ACTIVE)
                .isAccountNonLocked(Constants.STATUS_ACTIVE)
                .isCredentialsNonExpired(Constants.STATUS_ACTIVE)
                .isEnabled(Constants.STATUS_ACTIVE)
                .build();
    }

    private Rol getRoles(Role rolBuscado){
        return rolRepository.findByNombreRol(rolBuscado.name())
                .orElseThrow(() -> new RuntimeException("Error el rol no existe: "+rolBuscado.name()));
    }
}
