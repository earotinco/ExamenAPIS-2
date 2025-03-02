package com.codigo.ms_empresa.service.impl;

import com.codigo.ms_empresa.aggregates.dto.EmpresaDto;
import com.codigo.ms_empresa.aggregates.response.ResponseUserInfo;
import com.codigo.ms_empresa.client.SeguridadClient;
import com.codigo.ms_empresa.entity.EmpresaEntity;
import com.codigo.ms_empresa.repository.EmpresaRepository;
import com.codigo.ms_empresa.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;


@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;

    private final SeguridadClient seguridadClient;


    @Override
    public EmpresaEntity registrarEmpresa(EmpresaDto request, String token) {



        Boolean tokenValido = seguridadClient.validateToken(token);
        if (Boolean.FALSE.equals(tokenValido)) {
            throw new RuntimeException("Token inválido.");
        }
        System.out.println("RegistrarEmpresa EmpresaServiceImpl: Paso el Filtro Validar TOken");

        ResponseUserInfo userInfo = seguridadClient.getUserInfo(token);
        System.out.println("Registrar Empresa userInfo: "+userInfo);

        EmpresaEntity empresa = EmpresaEntity.builder()
                .razonSocial(request.getRazonSocial())
                .tipoDocumento(request.getTipoDocumento())
                .numeroDocumento(request.getNumeroDocumento())
                .estado(request.getEstado())
                .condicion(request.getCondicion())
                .direccion(request.getDireccion())
                .ubigeo(request.getUbigeo())
                .viaTipo(request.getViaTipo())
                .viaNombre(request.getViaNombre())
                .zonaCodigo(request.getZonaCodigo())
                .zonaTipo(request.getZonaTipo())
                .numero(request.getNumero())
                .interior(request.getInterior())
                .lote(request.getLote())
                .dpto(request.getDpto())
                .manzana(request.getManzana())
                .kilometro(request.getKilometro())
                .distrito(request.getDistrito())
                .provincia(request.getProvincia())
                .departamento(request.getDepartamento())
                .esAgenteRetencion(request.getEsAgenteRetencion())
                .esBuenContribuyente(request.getEsBuenContribuyente())
                .tipo(request.getTipo())
                .actividadEconomica(request.getActividadEconomica())
                .numeroTrabajadores(request.getNumeroTrabajadores())
                .tipoFacturacion(request.getTipoFacturacion())
                .tipoContabilidad(request.getTipoContabilidad())
                .comercioExterior(request.getComercioExterior())
                .usuarioRegistro("Earotinco")
                .fechaRegistro(new Timestamp(System.currentTimeMillis()))
                .build();

        return empresaRepository.save(empresa);
    }

    @Override
    public EmpresaEntity obtenerEmpresaPorRUC(String ruc) {
        return empresaRepository.findByNumeroDocumento(ruc)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
    }
    }

