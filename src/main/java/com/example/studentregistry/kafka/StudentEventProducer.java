package com.example.studentregistry.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Sends StudentEvents to Kafka.
 *
 * KafkaTemplate is Spring's helper for producing messages (Spring Boot creates it
 * from the spring.kafka settings in application.yml).
 * ObjectMapper (Jackson) converts the Java record into a JSON string.
 */
@Component
public class StudentEventProducer {

    public static final String TOPIC = "student-events";

    private static final Logger log = LoggerFactory.getLogger(StudentEventProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    // Constructor injection: Spring passes in the beans it already created
    public StudentEventProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(StudentEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);

            // send(topic, key, value)
            // The key is the student id: messages with the same key always go to the
            // same partition, so events for one student stay in order.
            kafkaTemplate.send(TOPIC, String.valueOf(event.studentId()), json);

            log.info("Published event: {}", json);
        } catch (JsonProcessingException e) {
            log.error("Could not serialize event", e);
        }
    }
}
