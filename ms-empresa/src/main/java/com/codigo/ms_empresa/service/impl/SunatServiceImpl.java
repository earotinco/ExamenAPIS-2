package com.codigo.ms_empresa.service.impl;

import com.codigo.ms_empresa.aggregates.dto.EmpresaDto;
import com.codigo.ms_empresa.aggregates.response.ResponseSunat;
import com.codigo.ms_empresa.client.SeguridadClient;
import com.codigo.ms_empresa.entity.EmpresaEntity;
import com.codigo.ms_empresa.mapper.EmpresaMapper;
import com.codigo.ms_empresa.ofeign.ReniecClient;
import com.codigo.ms_empresa.service.SunatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Log4j2
public class SunatServiceImpl implements SunatService {

    @Value("${token.api}")
    private String tokenApi;
    private final ReniecClient reniecClient;

    private final SeguridadClient seguridadClient;

    private final EmpresaMapper empresaMapper;






    @Override
    public EmpresaDto obtenerDatosRUC(String ruc, String token) {


        /*if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }*/
        log.info("token: "+token);
        boolean isValid = seguridadClient.validateToken(token);
        log.info("Resultado Validate Token: "+isValid);

        if (!isValid) {

            log.info("Token inválido o expirado");
            throw new RuntimeException("Token inválido o expirado");
        }

        ResponseSunat responseSunat = reniecClient.getEmpresaSunat(ruc, tokenApi);

        EmpresaDto empresa = empresaMapper.toDto2(responseSunat);


        return  empresa;
    }
}





