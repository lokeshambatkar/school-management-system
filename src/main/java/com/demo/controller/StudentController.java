package com.demo.controller;

import com.demo.dto.request.StudentRequest;
import com.demo.dto.response.StudentResponse;
import com.demo.service.StudentService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    // CREATE
    @PostMapping
    public StudentResponse createStudent(@Valid @RequestBody StudentRequest request) {

        log.info("Request received to create student");

        return studentService.createStudent(request);
    }

    // GET ALL
    @GetMapping
    public List<StudentResponse> getAllStudents() {

        log.info("Request received to get all students");

        return studentService.getAllStudents();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public StudentResponse getStudent(@PathVariable Long id) {

        log.info("Request received to get student {}", id);

        return studentService.getStudentById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public StudentResponse updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequest request) {

        log.info("Request received to update student {}", id);

        return studentService.updateStudent(id, request);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {

        log.info("Request received to delete student {}", id);

        studentService.deleteStudent(id);

        return "Student deleted successfully";
    }
}