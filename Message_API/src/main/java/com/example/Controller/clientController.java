package com.example.Controller;

import com.example.Entity.Client;
import com.example.Service.clientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

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
    public ResponseEntity Signup(@RequestBody HashMap<String,String> req){

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
