package com.codigo.ms_empresa.ofeign;

import com.codigo.ms_empresa.aggregates.response.ResponseSunat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(name = "sunat-client", url="https://api.apis.net.pe/v2/sunat/")
@FeignClient(name = "sunat-client", url = "${api.sunat.url}")
public interface ReniecClient {

    @GetMapping("/ruc/full")
    ResponseSunat getEmpresaSunat(@RequestParam("numero") String numero,
                                   @RequestHeader("Authorization") String token);
}
