package com.example.Controller;

import com.example.Entity.Message;
import com.example.Service.clientService;
import com.example.Service.messageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@RestController
public class MessageController {
    @Autowired
    private messageService service;

    @Autowired
    clientService cService;

    @GetMapping("/api/messages")
    public List<Message> getAll(){
        return service.listAll();
    }
    @PostMapping("/api/messages")
    public ResponseEntity post(@Valid @RequestBody HashMap<String,String> req){
         Message msg = new Message();
        LocalDateTime date;
         try {
             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
             date = LocalDateTime.parse(req.get("sending_time"), formatter);
         }
         catch (Exception e){
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid date time format");
         }

        Integer c_id = Integer.valueOf(req.get("client_id"));

        if(date.isBefore(LocalDateTime.now())||date.equals(LocalDateTime.now()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("sending time is already missed");
        if(!cService.findById(c_id))
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("client id doesn't exists");

         msg.setMsg(req.get("msg"));
         msg.setMsg_id(Integer.valueOf(req.get("msg_id")));
         msg.setGupshup_api_id(null);
         msg.setSent_time(null);
         msg.setReceiver_phoneno(req.get("receiver_phoneno"));
         msg.setSending_time(date);
         msg.setClient_id(c_id);
         msg.setStatus_id(0);

        service.save(msg);

        return ResponseEntity.ok("successfully saved");
    }



}
