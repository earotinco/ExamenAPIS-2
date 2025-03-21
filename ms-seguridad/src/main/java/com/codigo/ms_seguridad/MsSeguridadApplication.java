package com.codigo.ms_seguridad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient //eureka server - client

public class MsSeguridadApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsSeguridadApplication.class, args);
	}

}
