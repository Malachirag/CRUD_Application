package com.example.studentregistry.controller;

import com.example.studentregistry.model.Student;
import com.example.studentregistry.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Web layer: maps HTTP requests to Java methods.
 *
 * @RestController  -> return values are converted to JSON automatically (by Jackson)
 * @RequestMapping  -> every URL in this class starts with /api/students
 * @RequestBody     -> convert the incoming JSON into a Student object
 * @Valid           -> run the @NotBlank / @Email checks; if one fails Spring returns 400
 * @PathVariable    -> take {id} from the URL
 *
 * The controller stays thin: no business rules, it just calls the service.
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    // GET /api/students -> 200 OK + list of students
    @GetMapping
    public List<Student> getAll() {
        return service.findAll();
    }

    // GET /api/students/5 -> 200 OK, or 404 if missing
    @GetMapping("/{id}")
    public Student getOne(@PathVariable Long id) {
        return service.findById(id);
    }

    // POST /api/students -> 201 Created (+ Kafka event)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student create(@Valid @RequestBody Student student) {
        return service.create(student);
    }

    // PUT /api/students/5 -> 200 OK with the updated student
    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @Valid @RequestBody Student student) {
        return service.update(id, student);
    }

    // DELETE /api/students/5 -> 204 No Content
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
