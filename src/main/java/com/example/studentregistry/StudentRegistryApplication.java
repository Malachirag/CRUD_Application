package com.example.studentregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the application.
 *
 * @SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan.
 * It scans this package (and sub-packages) for classes like @RestController, @Service
 * and @Component, creates one object (a "bean") of each, and wires them together.
 */
@SpringBootApplication
public class StudentRegistryApplication {

    public static void main(String[] args) {
        // Starts the Spring container and the embedded Tomcat web server on port 8080
        SpringApplication.run(StudentRegistryApplication.class, args);
    }
}
