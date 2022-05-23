package com.example.Service;


import com.example.Entity.Client;
import com.example.Repository.clientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetailsService;
@Service
@Transactional
public class clientService  implements  UserDetailsService{

    @Autowired
    private clientRepository clientRepo;


    public Optional<Client> findById(Integer client_id){
        return clientRepo.findById(client_id);
    }
    public Client findByUsername(String username){
        return clientRepo.findByUsername(username);
    }


    public  void save(Client client) {

            clientRepo.save(client);


    }

    public List<Client> listAll(){
        return clientRepo.findAll();
    }

    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client;
        try {
            client = clientRepo.findByUsername(username);
        }
        catch(UsernameNotFoundException ex){
            throw new UsernameNotFoundException("User Not found");
        }
     return ClientDetailsImpl.build(client);


    }
}
