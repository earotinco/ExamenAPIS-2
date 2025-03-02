package com.codigo.ms_empresa.aggregates.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUserInfo {
    private String username;
    private String nombre;
    private String apellidos;
    private String rol;
}
