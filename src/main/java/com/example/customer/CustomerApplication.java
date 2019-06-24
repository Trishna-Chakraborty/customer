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
    Queue customerQueue() {

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-dead-letter-exchange", "dead_exchange");
        args.put("x-message-ttl", 60000);
        return new Queue("customer", false, false, false, args);
    }

    @Bean
    DirectExchange customerExchange() {
        return new DirectExchange("customer_exchange");
    }

    @Bean
    Binding customerBinding(Queue customerQueue, DirectExchange customerExchange) {
        return BindingBuilder.bind(customerQueue).to(customerExchange).with("");
    }


    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }

}
