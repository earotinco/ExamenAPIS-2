package com.codigo.ms_empresa.retrofit;

import com.codigo.ms_empresa.aggregates.response.ResponseSunat;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ClientSunatService {

    @GET("/v2/sunat/ruc/full")
    Call<ResponseSunat> getDatosSunat(@Header("Authorization") String token,
                                      @Query("numero") String numero);

    @GET("/v2/sunat/ruc/full")
    Call<ResponseSunat> obtenerDatosEmpresa(@Query("numero") String numeroDocumento);
}
