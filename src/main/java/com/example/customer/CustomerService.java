package com.example.customer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ChannelProxy;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;


    @Autowired
    AccountRepository accountRepository;


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

    @RabbitListener(queues = "postCustomer" )
    @SendTo("reply_queue")
    public  String postCustomer(String str,  Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{
       ObjectMapper objectMapper= new ObjectMapper();
        Customer customer=objectMapper.readValue(str,Customer.class);
        System.out.println("Got request to post"+ customer);
        customerRepository.save(customer);
        //Channel actualChannel = ((ChannelProxy) channel).getTargetChannel();
        //channel.basicAck(tag,false);
        System.out.println("Sent response from post"+ customer);
        return str;

    }
    @RabbitListener(queues = "updateCustomer" )
    @SendTo("reply_queue")
    public  String updateCustomer(String str, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{
        ObjectMapper objectMapper= new ObjectMapper();
        Customer customer=objectMapper.readValue(str,Customer.class);
        System.out.println("Got request to update "+ customer);
       // customerRepository.deleteById(customer.getId());

        customerRepository.save(customer);
        //channel.basicAck(tag,false);
        System.out.println("Sent response from update"+ customer);
         return str;

    }


    @RabbitListener(queues = "checkAccount" )
    @SendTo("reply_queue")
    public  String checkAccount(String str, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{
        ObjectMapper objectMapper= new ObjectMapper();
        Account account=objectMapper.readValue(str,Account.class);
        System.out.println("Got request to check "+ account);
        Account newAccount=accountRepository.findById(account.getId()).orElse(null);

        if(newAccount!=null) {
            System.out.println("Sent response from check : true");
            return String.valueOf(true);
        }
        else {
            System.out.println("Sent response from check : false");
            return String.valueOf(false);
        }




    }











}
