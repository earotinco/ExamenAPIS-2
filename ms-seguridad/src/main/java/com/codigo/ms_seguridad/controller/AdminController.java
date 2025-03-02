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
@RequestMapping ("/users/admin/")
@RequiredArgsConstructor
public class AdminController {

    private final UsuarioService usuarioService;




    @GetMapping("/prueba")
    public ResponseEntity<String> getSaludo(){
        return ResponseEntity.ok("Hola Admin! ---Probando");
    }


    /*OBTENER ADMINS*/
    @GetMapping("/all")
    public ResponseEntity <List<Usuario>> getAllAdmin(){
        return ResponseEntity.ok(usuarioService.admins());
    }


    /*UPDATE ADMIN*/
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarAdmin(@PathVariable Long id, @RequestBody Usuario usuarioAct) {
        Usuario usuario = usuarioService.actualizarUsuarioAdmin(id, usuarioAct);
        return ResponseEntity.ok(usuario);
    }

}
