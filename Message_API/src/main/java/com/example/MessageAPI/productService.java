package com.example.MessageAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class productService {
    @Autowired
    private productRepository repo;
    public List<Product> listAll(){
        return  repo.findAll();
    }
    public void save(Product product){
        repo.save(product);
    }
    public Product get(Integer id){
        return repo.findById(id).get();
    }
    public void delete(Integer id){
        repo.deleteById(id);
    }





}
