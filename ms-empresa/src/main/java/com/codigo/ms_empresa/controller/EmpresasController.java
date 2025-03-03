package com.codigo.ms_empresa.controller;


import com.codigo.ms_empresa.aggregates.dto.EmpresaDto;
import com.codigo.ms_empresa.entity.EmpresaEntity;
import com.codigo.ms_empresa.service.EmpresaService;
import com.codigo.ms_empresa.service.SunatService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/empresas/")
@RequiredArgsConstructor
@Log4j2
public class EmpresasController {


    private final EmpresaService empresaService;
    private final SunatService sunatService;


    @PostMapping("/registrarEmpresa")
    public ResponseEntity<?> registrarEmpresa(
            @RequestBody EmpresaDto empresa,
            HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        String ruc = empresa.getNumeroDocumento();
        log.info("Controlador - RUC EMPRESA: "+ruc);

        try {

            EmpresaDto empresaDto = sunatService.obtenerDatosRUC(ruc, token);
            EmpresaEntity empresaRegistrada = empresaService.registrarEmpresa(empresaDto, token);
            return ResponseEntity.ok(empresaRegistrada);

        } catch (Exception e) {
            log.error("Error al registrar empresa: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar empresa: " + e.getMessage());
        }
    }




    @GetMapping("/{ruc}")
    public ResponseEntity<?> obtenerEmpresa(@PathVariable String ruc, HttpServletRequest request) {

        String token = request.getHeader("Authorization");

        try {
            EmpresaDto empresa = empresaService.obtenerEmpresaPorRUC(ruc, token);
            return ResponseEntity.ok(empresa);
        } catch (Exception e) {
            log.error("Error al obtener empresa con RUC {}: ", ruc, e);
            log.info("TOKEN ERROR: "+token);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener empresa: " + e.getMessage());
        }
    }


    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarEmpresa(@PathVariable Long id, @RequestBody EmpresaDto empresaDto) {


        log.info("Controller - Empresa DTO interior: "+empresaDto.getInterior());

        try{
            EmpresaEntity updateEmpresa = empresaService.actualizarEmpresa(id,empresaDto);

            log.info("Controller - Empresa UPDATE interior: "+updateEmpresa.getInterior());
            return ResponseEntity.ok(updateEmpresa);

    }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "mensaje", "Empresa no encontrada con ID: " + id,
                    "error", e.getMessage()
            ));

            } catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Controlador - Error al actualizar empresa: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarEmpresa(@PathVariable Long id) {
        try {
            empresaService.eliminarEmpresa(id);
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Empresa eliminada exitosamente",
                    "id", id
            ));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "mensaje", "No se encontró la empresa con ID: " + id,
                    "error", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "mensaje", "Ocurrió un error al eliminar la empresa",
                    "error", e.getMessage()
            ));
        }
    }




    }
