package com.codigo.ms_seguridad.controller;

import com.codigo.ms_seguridad.entity.Usuario;
import com.codigo.ms_seguridad.service.AuthenticationService;
import com.codigo.ms_seguridad.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/users/user/")
@RequiredArgsConstructor
public class UserController {

    private final UsuarioService usuarioService;



    /*OBTENER USERS*/
    @GetMapping("/all")
    public ResponseEntity <List<Usuario>> getAllUser(){
        return ResponseEntity.ok(usuarioService.users());
    }

    /*UPDATE ADMIN*/
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUser(@PathVariable Long id, @RequestBody Usuario usuarioAct) {
        Usuario usuario = usuarioService.actualizarUsuarioUser(id, usuarioAct);
        return ResponseEntity.ok(usuario);
    }


    @GetMapping()
    public ResponseEntity<String> getSaludo(){
        return ResponseEntity.ok("Hola User! ---Probando");
    }
}
