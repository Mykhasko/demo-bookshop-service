package com.demobookshop.demobookshopservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Demo Bookshop Service API",
				version = "1.0.0",
				description = "API for managing books in a demo bookshop microservice application.",
				contact = @Contact(
						name = "Demo Bookshop Support",
						email = "it.support@demobookshop.com"),
				license = @License(
						name = "Apache 2.0",
						url = "http://www.apache.org/licenses/LICENSE-2.0.html")
		)
)
@SpringBootApplication
public class DemoBookshopServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoBookshopServiceApplication.class, args);
	}

}
