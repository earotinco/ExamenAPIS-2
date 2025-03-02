package com.codigo.ms_empresa.client;


import com.codigo.ms_empresa.aggregates.response.ResponseUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="ms-seguridad")
public interface SeguridadClient {


    @GetMapping("/apis_exam/auth/validate-token")
    Boolean validateToken(@RequestHeader("Authorization") String token);

    @GetMapping("apis_exam/auth/user-info")
    ResponseUserInfo getUserInfo(@RequestHeader("Authorization") String token);








    /*PRUEBA RETORNO DE SALUDO CON API MS-SEGURIDAD*/
    @GetMapping("/apis_exam/auth/prueba")
    String getInfoSaludo(@RequestHeader("Authorization") String authorization);

    /*Prueba Obtener el Valor Propiedad*/
    @GetMapping("/apis_exam/auth/prueba2")
    String getPropiedades(@RequestHeader("Authorization") String authorization);



}
