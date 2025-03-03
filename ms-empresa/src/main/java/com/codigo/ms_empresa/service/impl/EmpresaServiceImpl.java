package com.codigo.ms_empresa.service.impl;

import com.codigo.ms_empresa.aggregates.dto.EmpresaDto;
import com.codigo.ms_empresa.aggregates.response.ResponseUserInfo;
import com.codigo.ms_empresa.client.SeguridadClient;
import com.codigo.ms_empresa.entity.EmpresaEntity;
import com.codigo.ms_empresa.mapper.EmpresaMapper;
import com.codigo.ms_empresa.repository.EmpresaRepository;
import com.codigo.ms_empresa.service.EmpresaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;

    private final SeguridadClient seguridadClient;

    private final EmpresaMapper empresaMapper;



    @Override
    public EmpresaEntity registrarEmpresa(EmpresaDto request, String token) {


        if (!seguridadClient.validateToken(token)) {
            throw new RuntimeException("Token inválido o expirado");
        }


        Optional<EmpresaEntity> empresaExistente = Optional.ofNullable(empresaRepository.findByNumeroDocumento(request.getNumeroDocumento()));

        if (empresaExistente.isPresent()) {
            throw new RuntimeException("Ya existe una empresa con el número de documento: " + request.getNumeroDocumento());
        }



        ResponseUserInfo userInfo = seguridadClient.getUserInfo(token);


        EmpresaEntity empresa = empresaMapper.toEntity(request);


        empresa.setUsuarioRegistro(userInfo.getUsername());
        empresa.setFechaRegistro(new Timestamp(System.currentTimeMillis()));

        return empresaRepository.save(empresa);
    }

    @Override
    public EmpresaDto obtenerEmpresaPorRUC(String ruc, String token) {


        if (!seguridadClient.validateToken(token)) {
            throw new RuntimeException("Token inválido o expirado");
        }

        EmpresaEntity empresa = empresaRepository.findByNumeroDocumento(ruc);
        if (empresa == null) {
            throw new EntityNotFoundException("No se encontró una empresa con el RUC: " + ruc);
        }

        return empresaMapper.toDto(empresa);
    }

    @Override
    public EmpresaEntity actualizarEmpresa(Long id, EmpresaDto empresaDto) {
        EmpresaEntity empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la empresa con ID: " + id));

        EmpresaEntity empresaEntity = empresaMapper.toEntity(empresaDto);
        return empresaRepository.save(empresaEntity);
    }

    @Override
    public void  eliminarEmpresa(Long id) {
        EmpresaEntity empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la empresa con ID: " + id));

        empresaRepository.delete(empresa);

    }


}

