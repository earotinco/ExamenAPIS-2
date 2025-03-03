package com.codigo.ms_empresa.controller;

import com.codigo.ms_empresa.client.SeguridadClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
@RefreshScope
public class PruebaController {


    @Value("${dato.propiedad}")
    private String valorPropiedad;


    private final SeguridadClient seguridadClient;


    @GetMapping("/user-info")
    public ResponseEntity<String> getSaludo(@RequestHeader("Authorization") String token){

        System.out.println(seguridadClient.getInfoSaludo(token));
        return  ResponseEntity.ok(seguridadClient.getInfoSaludo(token));
    }

    @GetMapping("/pruebacomp")
    public ResponseEntity<String> getPorpiedad(@RequestHeader("Authorization") String token){
        System.out.println(seguridadClient.getPropiedades(token));
        return ResponseEntity.ok(seguridadClient.getPropiedades(token));
    }

    @GetMapping("/pruebapropiedad")
    public ResponseEntity<String> getPorpiedadConfig(){
        return ResponseEntity.ok(valorPropiedad);
    }



}
