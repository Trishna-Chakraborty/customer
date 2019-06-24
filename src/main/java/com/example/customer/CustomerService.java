package com.example.customer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;


    /*public List post(Customer customer){
        customerRepository.save(customer);
        return customerRepository.findAll();
    }*/


   /* @RabbitListener(queues = "customer" )
    @SendTo("reply_queue")
    public  double deductBalance(Double money, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{
        System.out.println("Got request "+ money);
        Customer customer=new Customer();
        customer.setId(String.valueOf(UUID.randomUUID()));
        customer.setBalance(money);
        customerRepository.save(customer);
        channel.basicAck(tag,false);
        System.out.println("Sent response "+ money);
        return money;

    }*/

    @RabbitListener(queues = "customer" )
    @SendTo("reply_queue")
    public  String deductBalance(String str, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{
       ObjectMapper objectMapper= new ObjectMapper();
        Customer customer=objectMapper.readValue(str,Customer.class);
        System.out.println("Got request "+ customer);
        customer.setId(String.valueOf(UUID.randomUUID()));
        customerRepository.save(customer);
        channel.basicAck(tag,false);
        System.out.println("Sent response "+ customer);
        return str;

    }











}
