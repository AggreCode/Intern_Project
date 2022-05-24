package com.example.Controller;

import com.example.Entity.Message;
import com.example.Service.messageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {
    @Autowired
    private messageService service;

    @GetMapping("/api/messages")
    public List<Message> getAll(){
        return service.listAll();
    }
    @PostMapping("/messages")
    public void post(@RequestBody List<Message> messages){
        service.save(messages);
    }



}
