package com.codigo.ms_seguridad.service.Impl;

import com.codigo.ms_seguridad.entity.Role;
import com.codigo.ms_seguridad.entity.Usuario;
import com.codigo.ms_seguridad.repository.UsuarioRepository;
import com.codigo.ms_seguridad.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private  final UsuarioRepository usuarioRepository;


    @Override
    public List<Usuario> users() {
        return usuarioRepository.findByRole(Role.USER.name());
    }


    @Override
    public List<Usuario> admins() {
        return usuarioRepository.findByRole(Role.ADMIN.name());
    }



    @Override
    public Usuario actualizarUsuarioUser(Long id, Usuario usuarioAct) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validar si el usuario tiene el rol "USER"
        boolean isUser = usuario.getRoles().stream()
                .anyMatch(rol -> rol.getNombreRol().equals("USER"));
        if (!isUser) {
            throw new RuntimeException("No tiene permisos para modificar este USER");
        }




        // Actualizar datos
        usuario.setNombres(usuarioAct.getNombres());
        usuario.setApellidos(usuarioAct.getApellidos());
        usuario.setUssername(usuarioAct.getUssername());
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuarioAct.getPassword()));

        return usuarioRepository.save(usuario);
    }


    @Override
    public Usuario actualizarUsuarioAdmin(Long id, Usuario usuarioAct){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


        boolean isUser = usuario.getRoles().stream()
                .anyMatch(rol -> rol.getNombreRol().equals("ADMIN"));
        if (!isUser) {
            throw new RuntimeException("No tiene permisos para modificar este ADMIN");
        }


        usuario.setNombres(usuarioAct.getNombres());
        usuario.setApellidos(usuarioAct.getApellidos());
        usuario.setUssername(usuarioAct.getUssername());
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuarioAct.getPassword()));


        return usuarioRepository.save(usuario);

    }

    @Override
    public Usuario obtenerUsuarioPorUssername(String username) {
        return usuarioRepository.findByUssername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {


        /***EXTRAE info del usuario y la pasa a SpringSecurity ***/
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return usuarioRepository.findByUssername(username)
                        .orElseThrow(()-> new UsernameNotFoundException("usuario no encontrado en BD"));
            }

        };
    }


}
