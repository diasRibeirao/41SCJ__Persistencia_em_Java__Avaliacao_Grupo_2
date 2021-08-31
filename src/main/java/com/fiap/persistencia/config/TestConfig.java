package com.fiap.persistencia.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.fiap.persistencia.services.db.DBEmersonService;
import com.fiap.persistencia.services.db.DBMarcoService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBEmersonService dbEmersonService;
	
	@Autowired
	private DBMarcoService dbMarcoService;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbEmersonService.instantiateTestDatabase();
		dbMarcoService.instantiateTestDatabase();
		return true;

	}
}
