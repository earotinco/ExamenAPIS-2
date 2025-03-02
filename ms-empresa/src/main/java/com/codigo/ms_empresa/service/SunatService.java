package com.codigo.ms_empresa.service;

import com.codigo.ms_empresa.aggregates.response.ResponseSunat;

import java.io.IOException;

public interface SunatService {

    ResponseSunat getInfoSunat(String ruc) throws IOException;
}
