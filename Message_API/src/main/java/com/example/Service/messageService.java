package com.example.Service;

import com.example.Entity.Message;
import com.example.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class messageService {

    @Autowired
    private MessageRepository repo;

    public List<Message> listAll(){
        return repo.findAll();
    }


    public void save(List<Message> messages) {
        repo.saveAll(messages);
    }
}
