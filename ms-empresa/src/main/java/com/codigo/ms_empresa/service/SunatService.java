package com.codigo.ms_empresa.service;

import com.codigo.ms_empresa.aggregates.dto.EmpresaDto;
import com.codigo.ms_empresa.aggregates.response.ResponseSunat;
import com.codigo.ms_empresa.entity.EmpresaEntity;

import java.io.IOException;

public interface SunatService {

    EmpresaDto obtenerDatosRUC(String ruc, String token) ;

}
