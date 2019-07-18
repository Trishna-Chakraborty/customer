package com.example.customer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class CustomerApplication {

    @Bean
    Queue postCustomerQueue() {

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-dead-letter-exchange", "dead_exchange");
        args.put("x-message-ttl", 60000);
        //args.put("x-dead-letter-routing-key","post.#");
        return new Queue("postCustomer", false, false, false, args);
    }


    @Bean
    Queue updateCustomerQueue() {

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-dead-letter-exchange", "dead_exchange");
        args.put("x-message-ttl", 60000);
        //args.put("x-dead-letter-routing-key","post.#");
        return new Queue("updateCustomer", false, false, false, args);
    }


    @Bean
    Queue checkAccountQueue() {

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-dead-letter-exchange", "dead_exchange");
        args.put("x-message-ttl", 60000);
        //args.put("x-dead-letter-routing-key","post.#");
        return new Queue("checkAccount", false, false, false, args);
    }

    @Bean
    DirectExchange customerExchange() {
        return new DirectExchange("customer_exchange");
    }

    @Bean
    DirectExchange accountExchange() {
        return new DirectExchange("account_exchange");
    }


    @Bean
    Binding postCustomerBinding(Queue postCustomerQueue, DirectExchange customerExchange) {
        return BindingBuilder.bind(postCustomerQueue).to(customerExchange).with("post.customer");
    }
    @Bean
    Binding updateCustomerBinding(Queue updateCustomerQueue, DirectExchange customerExchange) {
        return BindingBuilder.bind(updateCustomerQueue).to(customerExchange).with("update.customer");
    }
    @Bean
    Binding checkAccountBinding(Queue checkAccountQueue, DirectExchange accountExchange) {
        return BindingBuilder.bind(checkAccountQueue).to(accountExchange).with("check.account");
    }


    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }

}
