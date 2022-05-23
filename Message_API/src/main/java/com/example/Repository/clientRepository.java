package com.example.Repository;


import com.example.Entity.Client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface clientRepository extends JpaRepository<Client,Integer> {
    @Query("SELECT c FROM Client c WHERE c.username=?1")
    Client findByUsername(String username);

}
