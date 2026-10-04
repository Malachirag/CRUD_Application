package com.example.studentregistry.service;

import com.example.studentregistry.kafka.StudentEvent;
import com.example.studentregistry.kafka.StudentEventProducer;
import com.example.studentregistry.model.Student;
import com.example.studentregistry.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

/**
 * Business logic layer. The controller handles HTTP, the repository handles SQL,
 * and this class holds the rules in between (e.g. "email must be unique",
 * "publish an event after creating").
 *
 * ResponseStatusException lets us stop with an HTTP error code (404, 409);
 * Spring turns it into the matching HTTP response.
 */
@Service
public class StudentService {

    private final StudentRepository repository;
    private final StudentEventProducer producer;

    public StudentService(StudentRepository repository, StudentEventProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    public List<Student> findAll() {
        return repository.findAll();
    }

    public Student findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student " + id + " not found"));
    }

    @Transactional
    public Student create(Student student) {
        if (repository.existsByEmail(student.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }
        student.setId(null);                       // never trust an id sent by the client

        Student saved = repository.save(student);  // Hibernate runs the INSERT; saved now has an id

        producer.publish(new StudentEvent("STUDENT_CREATED", saved.getId(),
                saved.getName(), saved.getEmail(), Instant.now()));
        return saved;
    }

    /**
     * Note: there is no save() call here.
     * findById returns a "managed" entity. Inside a @Transactional method Hibernate
     * remembers its original values; when the method ends and the transaction commits,
     * it compares them and runs an UPDATE for whatever changed. This is "dirty checking".
     */
    @Transactional
    public Student update(Long id, Student input) {
        Student existing = findById(id);

        boolean emailChanged = !existing.getEmail().equals(input.getEmail());
        if (emailChanged && repository.existsByEmail(input.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        existing.setName(input.getName());
        existing.setEmail(input.getEmail());
        existing.setCourse(input.getCourse());
        return existing;                           // dirty checking writes the UPDATE on commit
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findById(id));           // 404 if it does not exist, otherwise DELETE
    }
}
