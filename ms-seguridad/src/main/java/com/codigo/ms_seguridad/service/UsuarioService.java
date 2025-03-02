package com.codigo.ms_seguridad.service;

import com.codigo.ms_seguridad.entity.Usuario;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {


    UserDetailsService userDetailsService();


    List<Usuario> users();
    List<Usuario> admins();

    Usuario actualizarUsuarioUser(Long id, Usuario usuarioAct);
    Usuario actualizarUsuarioAdmin(Long id, Usuario usuarioAct);

    Usuario obtenerUsuarioPorUssername(String username) ;





}
