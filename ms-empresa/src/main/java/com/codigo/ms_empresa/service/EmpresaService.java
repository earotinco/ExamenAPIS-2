package com.codigo.ms_empresa.service;

import com.codigo.ms_empresa.aggregates.dto.EmpresaDto;
import com.codigo.ms_empresa.entity.EmpresaEntity;

public interface EmpresaService {

    EmpresaEntity registrarEmpresa(EmpresaDto empresadto, String token);
    EmpresaDto obtenerEmpresaPorRUC(String ruc, String token);
    EmpresaEntity actualizarEmpresa(Long id, EmpresaDto empresaDto);
    void eliminarEmpresa(Long id);
}
