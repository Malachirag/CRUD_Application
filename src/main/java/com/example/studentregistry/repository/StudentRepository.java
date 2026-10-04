package com.example.studentregistry.repository;

import com.example.studentregistry.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data access layer. We only write an interface; Spring Data creates the
 * implementation at startup.
 *
 * JpaRepository<Student, Long> gives us for free:
 *   save(), findById(), findAll(), delete(), count(), ...
 *
 * existsByEmail is a "derived query": Spring reads the method name and builds
 *   SELECT ... FROM students WHERE email = ?
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);
}
