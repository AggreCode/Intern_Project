package com.example.Service;


import com.example.Entity.Client;
import com.example.Entity.Message;
import com.example.Repository.clientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Service
@Transactional
public class clientService {

    @Autowired
    private clientRepository clientRepo;


    public  void save(Client client) {

            clientRepo.save(client);


    }

    public List<Client> listAll(){
        return clientRepo.findAll();
    }
}
