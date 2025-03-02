package com.codigo.ms_empresa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsEmpresaApplication {

	public static void main(String[] args) {

		SpringApplication.run(MsEmpresaApplication.class, args);
	}

}
