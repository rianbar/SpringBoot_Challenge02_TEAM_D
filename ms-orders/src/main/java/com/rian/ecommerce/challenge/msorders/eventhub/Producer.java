package com.rian.ecommerce.challenge.msorders.eventhub;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Producer {

  private final KafkaTemplate<Integer, String> kafkaTemplate;

  public void sendMessage(String topic, String message) {
    kafkaTemplate.send(topic, message);
  }
}
