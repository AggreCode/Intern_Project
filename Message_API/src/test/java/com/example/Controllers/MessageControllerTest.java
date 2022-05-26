package com.example.Controllers;


import com.example.Formats.Message_Request;
import com.example.Repository.clientRepository;
import com.example.Security.jwt.AuthTokenFilter;
import com.example.Security.jwt.JwtUtils;
import com.example.Service.clientService;
import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Before;
//import org.junit.Test;

import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@AutoConfigureMockMvc
public class MessageControllerTest {


    public static final Logger log = LoggerFactory.getLogger(MessageControllerTest.class);

    @Autowired
    private MockMvc mvc;


    @MockBean
    private  clientService userDetailsService;

    @MockBean
    private clientRepository clientRepo;
    @MockBean
    private JwtUtils jwtUtils;



    public static ObjectMapper objectMapper = new ObjectMapper();
    private String token;




    @Test
    public void TestCreate_Message_Without_Token_Request() throws Exception {

        Message_Request req_body = new Message_Request("12", "Your account balance is xxx", "2022-05-26 18:47:56", "1", "6370150139");
        log.info(token);
        mvc.perform(post("/api/messages")
                        .header("token","Bearer "+token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(req_body.toString()))
                .andExpect(status().is4xxClientError());


    }

    @Test
    public void TestCreate_Message_With_validToken_Request() throws Exception {
        Message_Request req_body = new Message_Request("12", "Your account balance is xxx", "2022-05-25 18:47:56", "1", "6370150139");
        when(jwtUtils.validateJwtToken("xyz")).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken("xyz")).thenReturn("Biswa2");
        mvc.perform(post("/api/messages").header("Authorization", "Bearer "+"xyz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(req_body)))
                .andExpect(status().isOk())
                .andReturn();
    }


}
