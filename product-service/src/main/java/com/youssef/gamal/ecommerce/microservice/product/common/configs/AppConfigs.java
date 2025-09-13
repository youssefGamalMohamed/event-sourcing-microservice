package com.youssef.gamal.ecommerce.microservice.product.common.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class AppConfigs {

	
	@Bean
	@Primary
	public ObjectMapper httpObjectMapper() {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.findAndRegisterModules();
	    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	    return mapper; // ❌ no default typing
	}

	
}
