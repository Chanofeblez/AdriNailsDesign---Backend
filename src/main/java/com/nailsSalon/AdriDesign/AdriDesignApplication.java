package com.nailsSalon.AdriDesign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class AdriDesignApplication {

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(AdriDesignApplication.class);
		Map<String, Object> props = new HashMap<>();

		// Obtenemos el puerto desde las variables de entorno en Heroku
		String port = System.getenv("PORT");
		if (port != null && !port.isEmpty()) {
			props.put("server.port", port);
		} else {
			// Si no está en un entorno como Heroku, usa el puerto por defecto (8081)
			props.put("server.port", "8081");
		}

		app.setDefaultProperties(props);
		app.run(args);
	}

}
