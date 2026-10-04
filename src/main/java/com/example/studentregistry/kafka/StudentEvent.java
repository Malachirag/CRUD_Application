package com.example.studentregistry.kafka;

import java.time.Instant;

/**
 * The message we send to Kafka when something happens to a student.
 *
 * A Java "record" is a short way to write an immutable data class: the compiler
 * generates the constructor, getters (eventType(), studentId(), ...), equals and toString.
 *
 * Sent as JSON, for example:
 *   {"eventType":"STUDENT_CREATED","studentId":1,"name":"Asha Rao",
 *    "email":"asha@example.com","occurredAt":"2026-10-04T10:15:30Z"}
 */
public record StudentEvent(String eventType,
                           Long studentId,
                           String name,
                           String email,
                           Instant occurredAt) { }
