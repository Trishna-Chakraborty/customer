package com.example.customer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/customers")
    public List findAll(){
        Customer customer= new Customer();
        customer.setBalance(500);
        customer.setId(String.valueOf(UUID.randomUUID()));
        customerRepository.save(customer);
        return customerRepository.findAll();
    }
    @PostMapping("/customers")
    public List post(@RequestBody Customer customer){
        customerRepository.save(customer);
        return customerRepository.findAll();
    }

}
