package com.formacionspring.springboot.di.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.formacionspring.springboot.di.kafka.producer.KafkaMessageProducer;

@RestController
public class KafkaTestController {

	@Autowired
	KafkaMessageProducer kafkaMessageProducer;
	
	@PostMapping("/add/{topic}")
	public void addIdCustomer( @PathVariable String topic)
	{
		kafkaMessageProducer.sendMessage(topic);
	}
}
