package com.codigo.ms_empresa.service.impl;

import com.codigo.ms_empresa.aggregates.constants.Constants;
import com.codigo.ms_empresa.aggregates.response.ResponseSunat;
import com.codigo.ms_empresa.retrofit.ClientSunat;
import com.codigo.ms_empresa.retrofit.ClientSunatService;
import com.codigo.ms_empresa.service.SunatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SunatServiceImpl implements SunatService {

    ClientSunatService retrofitPreConfig = ClientSunat
            .getRetrofit()
            .create(ClientSunatService.class);

    @Value("${token.api}")
    private String token;




    @Override
    public ResponseSunat getInfoSunat(String ruc) throws IOException {
        ResponseSunat objResponse = new ResponseSunat();
        //Ejecutando la consulta con retrofit
        Response<ResponseSunat> executeSunat = preparacionClientSunat(ruc).execute();
        //validamos la respuesta de la transacción
        if(executeSunat.isSuccessful() && Objects.nonNull(executeSunat.body())){
            objResponse = executeSunat.body();
        }
        return objResponse;
    }

    //Metodo de apoyo para Preparar a retrofit
    private Call<ResponseSunat> preparacionClientSunat(String ruc){
        return retrofitPreConfig.getDatosSunat(Constants.BEARER+token,
                ruc);
    }




}
