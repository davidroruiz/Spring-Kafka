package com.formacionspring.springboot.di.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.formacionspring.springboot.di.entidad.Entidad;
import com.formacionspring.springboot.di.entidad.Entidad.Corona;
import com.formacionspring.springboot.di.entidad.Entidad.Dgorefen;
import com.google.gson.Gson;

@Component
public class KafkaMessageProducer {

	@Autowired
	public KafkaTemplate<String, String> kafkaTemplate;
	/*El topic por defecto, sobre el que enviaremos los mensajes lo definimos en la variable topicname que, 
	 * por defecto, tendrá el valor de message.topic.name establecida en el fichero properties de Spring Boot */
	@Value(value = "${message.topic.name:profesorp}")	
	private String topicName;
	
	String json = "{\r\n" + 
			"  \"ccenint\": \"aaaa\",\r\n" + 
			"  \"cdcanalp\": \"OFI\",\r\n" + 
			"  \"cdcarter\": \"bbbb\",\r\n" + 
			"  \"cdestref\": \"cccc\",\r\n" + 
			"  \"corona\": {\r\n" + 
			"    \"codser\": \"dddd\",\r\n" + 
			"    \"feccor\": 2020-06-15,\r\n" + 
			"    \"nueref\": 1234,\r\n" + 
			"    \"surefe\": \"\"\r\n" + 
			"  },\r\n" + 
			"  \"dgorefen\": {\r\n" + 
			"    \"catrpbat\": \"mmmm\",\r\n" + 
			"    \"cdtrpbat\": \"nnnnnn\",\r\n" + 
			"    \"fedgoref\": \"2020-06-15\",\r\n" + 
			"    \"idcentdg\": \"dsfs45\",\r\n" + 
			"    \"idemprdg\": \"ttgg\",\r\n" + 
			"    \"numdgodg\": 5456,\r\n" + 
			"    \"termbtdg\": \"adfd\"\r\n" + 
			"  },\r\n" + 
			"  \"eempint\": \"123\"\r\n" + 
			"}";

	Gson gson = new Gson();
	Entidad dobleEntidad = gson.fromJson(json, Entidad.class);
	Corona coronas = dobleEntidad.getCorona();
	Dgorefen dgorefens= dobleEntidad.getDgorefen();
	
	
	
	
	//En la función sendMessage sobre el topic mandado mandaremos el mensaje deseado.
	public void sendMessage(String topic) {
		// Le pasa la variable topic y comprueba que este nula y vacia
		
		
		
		
		if (topic==null || topic.trim().equals(""))
			//Despues en la variable topic le guarda los datos de topicName
			topic=topicName;
		//Con el ListenableFuture la llamada al servidor Kafka sera asincrona y luego le pasa en el ListenableFuture el envio del topic y el mensaje
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, json);
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			
			//Envio del Mensaje
			@Override
			public void onSuccess(SendResult<String, String> result) {
				System.out.println("Enviar mensaje=[" + json + "] con despleazamiento=[" + result.getRecordMetadata().offset() + "]");
			}
			
			//Error en el envio del Mensaje
			@Override
			public void onFailure(Throwable ex) {
				System.err.println("No se puede enviar el mensage=[" + json + "] debido a : " + ex.getMessage());
			}
		});
	}	
}
