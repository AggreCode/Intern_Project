package com.example.Controllers;



import com.example.Repository.clientRepository;

import com.example.Security.jwt.JwtUtils;
import com.example.Service.clientService;
import com.fasterxml.jackson.databind.ObjectMapper;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.jayway.jsonpath.JsonPath;
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

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.HashMap;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    private  clientService c_service;

    @MockBean
    private clientRepository clientRepo;
    @MockBean
    private JwtUtils jwtUtils;


    @Autowired
    public  ObjectMapper objectMapper;
    private String token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJCaXN3YTIiLCJpYXQiOjE2NTM1ODM1OTIsImV4cCI6MTY1MzY2OTk5Mn0.lcXVU5vC5x1Ic_pEnAYeCOZ0wXGx8L6rlXAwwKlAstWwpWIL0bU-lbdWfoO_TT__gpvgGhrqMnIc-fmNvrx9xQ";

    Gson gson=new Gson();


    @Test
    public void TestCreate_Message_With_Valid_Request() throws Exception {

        HashMap<String, String> message = new HashMap<String, String>();

        message.put("msg_id", "123456");
        message.put( "msg", "Hey Biswajit3");
        message.put( "sending_time","2022-05-27 10:05:34");
        message.put( "client_id", "12");
        message.put(  "receiver_phoneno", "9692223942");

        String jsonString = gson.toJson(message);
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        log.info("Test with Valid Request");

        when(jwtUtils.validateJwtToken("xyz")).thenReturn(true);
        when(c_service.findById(12)).thenReturn(true);

        MvcResult result=mvc.perform(post("/api/messages")
                        .header("Authorization","Bearer xyz")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(jsonObject)))
                .andExpect(status().isOk()).andReturn();

        String resp= result.getResponse().getContentAsString();
        assertThat(resp).isEqualTo("successfully saved");
    }
    @Test
    public void TestCreate_Message_With_InValid_Phone() throws Exception {

        HashMap<String, String> message = new HashMap<String, String>();

        message.put("msg_id", "123456");
        message.put( "msg", "Hey Biswajit3");
        message.put( "sending_time","2022-05-27 10:05:34");
        message.put( "client_id", "12");
        message.put(  "receiver_phoneno", "1692223942");

        String jsonString = gson.toJson(message);
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        log.info("Test with wrong phone no");

        when(jwtUtils.validateJwtToken("xyz")).thenReturn(true);
        when(c_service.findById(12)).thenReturn(true);

        MvcResult result=mvc.perform(post("/api/messages")
                        .header("Authorization","Bearer xyz")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(jsonObject)))
                .andExpect(status().isBadRequest()).andReturn();
       String resp = JsonPath.read(result.getResponse().getContentAsString(), "$.message");
        assertThat(resp).isEqualTo("invalid phoneno");
    }
    @Test
    public void TestCreate_Message_With_InValid_Date() throws Exception {

        //Wrong Date format test
        HashMap<String, String> message = new HashMap<String, String>();

        message.put("msg_id", "123456");
        message.put( "msg", "Hey Biswajit3");
        message.put( "sending_time","2021-05-27");
        message.put( "client_id", "12");
        message.put(  "receiver_phoneno", "9692223942");

        String jsonString = gson.toJson(message);
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        log.info("Test with a wrong Date format");

        when(jwtUtils.validateJwtToken("xyz")).thenReturn(true);
        when(c_service.findById(12)).thenReturn(true);

        MvcResult result=mvc.perform(post("/api/messages")
                        .header("Authorization","Bearer xyz")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(jsonObject)))
                .andExpect(status().isBadRequest()).andReturn();
        String resp = result.getResponse().getContentAsString();
        assertThat(resp).isEqualTo("invalid date time format");



        //Old Date as input test

        message.put( "sending_time","2022-05-26 01:45:56");
        jsonString = gson.toJson(message);
        jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        log.info("Test with a old Date");

        when(jwtUtils.validateJwtToken("xyz")).thenReturn(true);
        when(c_service.findById(12)).thenReturn(true);

        result=mvc.perform(post("/api/messages")
                        .header("Authorization","Bearer xyz")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(jsonObject)))
                .andExpect(status().isBadRequest()).andReturn();
        resp = result.getResponse().getContentAsString();
        assertThat(resp).isEqualTo("sending time is already missed");


    }
   





}
