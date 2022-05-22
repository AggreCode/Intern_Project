package com.example.Controller;

import com.example.Entity.Client;
import com.example.Service.clientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class clientController {
    @Autowired
    private clientService Service;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/api/clients")
    public List<Client> get(){
        return Service.listAll();
    }

    @PostMapping("/api/signup")
    public ResponseEntity Signup(@Valid @RequestBody HashMap<String,String> req){

        Client client= new Client();
        client.setClient_id(Integer.valueOf(req.get("client_id")));
        client.setClient_name(req.get("client_name"));
        client.setUsername(req.get("username"));
        client.setPassword(passwordEncoder.encode(req.get("password")));
        client.setPhone(req.get("phone"));
        client.setEmail_id(req.get("email_id"));

            Service.save(client);





        return ResponseEntity.ok("client added successfully");
    }


}
