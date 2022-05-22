package com.example;

import com.example.Entity.Message;
import org.slf4j.Logger;


import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example.Auth","com.example.Controller","com.example.Entity","com.example.Repository",
		"com.example.Scheduler","com.example.Service","com.example.ErrorHandler"} )

public class Application  {


	public static final Logger log= LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Bean
	public BlockingQueue<Message> Queue(){
		return new ArrayBlockingQueue<Message>(200);
	}
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

//	@Bean
//	public Message msg(){
//		return new Message();
//	}


};


