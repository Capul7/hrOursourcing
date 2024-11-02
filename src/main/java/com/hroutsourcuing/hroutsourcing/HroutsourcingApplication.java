package com.hroutsourcuing.hroutsourcing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.hroutsourcuing.hroutsourcing.models"})
public class HroutsourcingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HroutsourcingApplication.class, args);
		// Invocar la clase "principal" para ejecutar el menú
		//principal.mostrarMenu();
	}


}
