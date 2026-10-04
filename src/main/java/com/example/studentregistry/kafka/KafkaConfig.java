package com.example.studentregistry.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Creates the "student-events" topic when the app starts (if it does not exist yet).
 *
 * Spring Boot sees a NewTopic bean and asks Kafka to create it.
 * 1 partition and 1 replica are enough because we run a single Kafka broker.
 */
@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic studentEventsTopic() {
        return TopicBuilder.name(StudentEventProducer.TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
