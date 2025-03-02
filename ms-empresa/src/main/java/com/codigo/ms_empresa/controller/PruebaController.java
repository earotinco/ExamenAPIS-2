package com.codigo.ms_empresa.controller;

import com.codigo.ms_empresa.client.SeguridadClient;
import com.codigo.ms_empresa.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@RefreshScope
public class PruebaController {




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


    @GetMapping("/suma")
    public ResponseEntity<Integer> getSuma(){
        int a = 4;
        int b = 5;
        log.info("Suma: "+a+b);
        return ResponseEntity.ok(a+b) ;
    }

}
