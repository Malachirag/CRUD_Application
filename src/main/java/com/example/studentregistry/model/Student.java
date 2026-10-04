package com.example.studentregistry.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * The Student entity: one object of this class = one row in the "students" table.
 *
 * Hibernate reads these annotations and generates the table:
 *   CREATE TABLE students (id BIGSERIAL PRIMARY KEY, name VARCHAR NOT NULL,
 *                          email VARCHAR NOT NULL UNIQUE, course VARCHAR)
 *
 * Two kinds of annotations live here:
 *   - jakarta.persistence (@Entity, @Id, @Column)  -> how to store it in the database
 *   - jakarta.validation  (@NotBlank, @Email)      -> rules checked when JSON comes in (@Valid)
 */
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // PostgreSQL generates the id (1, 2, 3, ...)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    private String course;  // optional, so no annotations

    public Student() { }  // Hibernate and Jackson need a no-args constructor

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
}
