package com.codigo.ms_empresa.controller;


import com.codigo.ms_empresa.aggregates.dto.EmpresaDto;
import com.codigo.ms_empresa.client.SeguridadClient;
import com.codigo.ms_empresa.entity.EmpresaEntity;
import com.codigo.ms_empresa.service.EmpresaService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empresas")
@RequiredArgsConstructor
@Log4j2
public class EmpresasController {


    private final EmpresaService empresaService;


    @PostMapping("/registrarEmpresa")
    public ResponseEntity<EmpresaEntity> registrarEmpresa(
            @RequestBody EmpresaDto empresa,
            HttpServletRequest request) {


        String token = request.getHeader("Authorization");
        log.info("Token recibido para registrar empresa: " + token);

        return ResponseEntity.ok(empresaService.registrarEmpresa(empresa, token));
    }



    @GetMapping("/{ruc}")
    public ResponseEntity<EmpresaEntity> obtenerEmpresaPorRUC(@PathVariable String ruc) {
        return ResponseEntity.ok(empresaService.obtenerEmpresaPorRUC(ruc));
    }





}
