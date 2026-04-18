package com.crm.config;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

public class SwaggerConfig{
	
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()		
						.title("CRM Backend APIs")
						.version("1.0")
						.description("Lead + Ticket + Audit Workflow APIs"));
	}	
}