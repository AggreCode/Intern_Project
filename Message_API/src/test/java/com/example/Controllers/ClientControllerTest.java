package com.example.Controllers;
import com.example.Entity.Client;
import com.example.Repository.clientRepository;
import com.example.Security.jwt.JwtUtils;
import com.example.Service.clientService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.HashMap;
import java.util.Map;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@AutoConfigureMockMvc
public class ClientControllerTest {
	public static final Logger log = LoggerFactory.getLogger(ClientControllerTest.class);
	@Autowired
	private MockMvc mvc;
	@MockBean
	clientService c_service;

	@MockBean
	private clientRepository clientRepo;
	@MockBean
	private JwtUtils jwtUtils;



	@Autowired
	public ObjectMapper objectMapper;

	Gson gson=new Gson();


	@Test
	public void TestCreate_clientWith_validRequest() throws Exception {

		HashMap<String, String> client = new HashMap<String, String>();

		client.put("client_id", "123456");
		client.put( "username", "Biswajit3");
		client.put( "password","pass@123");
		client.put( "client_name", "12");
		client.put(  "phone", "9692223942");
		client.put(  "email_id", "Biswa2@Biswa.com");


		String jsonString = gson.toJson(client);
		JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
		log.info("Test with Valid client Request");


		MvcResult result=mvc.perform(post("/api/auth/signup")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON)
						.content(String.valueOf(jsonObject)))
				.andExpect(status().isOk()).andReturn();

		String resp= result.getResponse().getContentAsString();
		assertThat(resp).isEqualTo("client added successfully");



	}








}
