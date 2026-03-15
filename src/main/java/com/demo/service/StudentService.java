package com.demo.service;

import com.demo.dto.request.StudentRequest;
import com.demo.dto.response.StudentResponse;
import com.demo.entity.Student;
import com.demo.mapper.StudentMapper;
import com.demo.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    // CREATE
    public StudentResponse createStudent(StudentRequest request) {

        log.info("Creating student with email: {}", request.getEmail());

        Student student = StudentMapper.toEntity(request);

        Student saved = studentRepository.save(student);

        return StudentMapper.toResponse(saved);
    }

    // GET ALL
    public List<StudentResponse> getAllStudents() {

        log.info("Fetching all students");

        return studentRepository.findAll()
                .stream()
                .map(StudentMapper::toResponse)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public StudentResponse getStudentById(Long id) {

        log.info("Fetching student with id {}", id);

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return StudentMapper.toResponse(student);
    }

    // UPDATE
    public StudentResponse updateStudent(Long id, StudentRequest request) {

        log.info("Updating student with id {}", id);

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        StudentMapper.updateEntity(student, request);

        Student updated = studentRepository.save(student);

        return StudentMapper.toResponse(updated);
    }

    // DELETE
    public void deleteStudent(Long id) {

        log.info("Deleting student with id {}", id);

        studentRepository.deleteById(id);
    }
}