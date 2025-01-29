package com.rian.ecommerce.challenge.msorders.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String booststrapAddress;

  @Bean
  KafkaAdmin kafkaAdmin() {
    Map<String, Object> configs = new HashMap<>();
    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, booststrapAddress);
    return new KafkaAdmin(configs);
  }

  @Bean
  KafkaAdmin.NewTopics topics() { // remember to configure the topics
    return new KafkaAdmin.NewTopics(
      TopicBuilder.name("topic1").build(),
      TopicBuilder.name("topic2").build(),
      TopicBuilder.name("topic3").build());
  }
}
