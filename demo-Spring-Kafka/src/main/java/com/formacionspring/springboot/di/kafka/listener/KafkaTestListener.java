package com.formacionspring.springboot.di.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaTestListener {

	/* En la función listenTopic1 con la etiqueta @KafkaListener, definiremos el topics , 
	 * en plural pues pueden ser varios, que queremos escuchar.
	 *  En este caso escucharemos los definidos en la variable message.topic.name del fichero properties 
	 *  de Spring Boot*/
	
	
	 @KafkaListener(topics = "${message.topic.name:profesorp}", groupId = "${message.group.name:profegroup}")
	 public void listenTopic1(String message) {
	     System.out.println("Recibe el Mensaje de topic1 en  listener: " + message);
	 }
		
		   @KafkaListener(topics = "${message.topic.name2:profesorp-group}", groupId = "${message.group.name:profegroup}")
		   public void listenTopic2(String message) {
			   System.out.println("Recibe el Mensaje de topic2 en listener "+message);
		   }
	
}

