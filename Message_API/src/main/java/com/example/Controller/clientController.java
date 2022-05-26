package com.example.Controller;

import com.example.Entity.Client;
import com.example.Security.jwt.JwtUtils;
import com.example.Service.ClientDetailsImpl;
import com.example.Service.clientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
public class clientController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private clientService clientService;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/api/clients")
    public List<Client> get(){
        return clientService.listAll();
    }

    @PostMapping("/api/auth/signup")
    public ResponseEntity Signup(@Valid @RequestBody HashMap<String,String> req){

        Client c= clientService.findByUsername(req.get("username"));
        if(c!=null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with that username already exists.");

        Client client= new Client();
//        client.setClient_id(Integer.valueOf(req.get("client_id")));
        client.setClient_name(req.get("client_name"));
        client.setUsername(req.get("username"));
        client.setPassword(passwordEncoder.encode(req.get("password")));
        client.setPhone(req.get("phone"));
        client.setEmail_id(req.get("email_id"));
        clientService.save(client);







        return ResponseEntity.ok("client added successfully");
    }
    @PostMapping("/api/auth/signin")
    public ResponseEntity  Login(@RequestBody HashMap<String,String> req) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.get("username"), req.get("password")));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        ClientDetailsImpl userDetails = (ClientDetailsImpl) authentication.getPrincipal();

        HashMap<String,String> response= new HashMap<>();
        response.put("token",jwt);

        return ResponseEntity.ok(response);

    }



}
