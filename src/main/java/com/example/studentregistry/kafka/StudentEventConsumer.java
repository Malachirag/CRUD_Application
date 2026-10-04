package com.example.studentregistry.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Reads events from the "student-events" topic.
 *
 * @KafkaListener starts a background thread that keeps polling Kafka. Every new
 * message is passed to consume(). Here we only log it; in a real system this would
 * be a separate service that sends a welcome email or writes an audit record.
 */
@Component
public class StudentEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(StudentEventConsumer.class);

    @KafkaListener(topics = StudentEventProducer.TOPIC, groupId = "student-registry-group")
    public void consume(String message) {
        log.info("Received event (pretend we send a welcome email): {}", message);
    }
}
