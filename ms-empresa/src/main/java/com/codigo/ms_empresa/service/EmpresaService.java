package com.codigo.ms_empresa.service;

import com.codigo.ms_empresa.aggregates.dto.EmpresaDto;
import com.codigo.ms_empresa.entity.EmpresaEntity;

public interface EmpresaService {

    EmpresaEntity registrarEmpresa(EmpresaDto empresadto, String token);
    EmpresaEntity obtenerEmpresaPorRUC(String ruc);
}
