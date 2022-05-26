package com.example.MessageAPI;

import com.example.Security.jwt.AuthTokenFilter;
import com.example.Security.jwt.JwtUtils;
import com.example.Service.clientService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootTest
@TestConfiguration
class ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Bean
	public clientService c_service(){
		return new clientService();
	}
	@Bean
	public AuthTokenFilter authFilter(){
		return new AuthTokenFilter();
	}
	@Bean
	public JwtUtils jwtUtils(){
		return new JwtUtils();
	}

}
