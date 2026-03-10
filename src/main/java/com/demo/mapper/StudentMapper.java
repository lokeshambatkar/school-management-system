package com.demo.mapper;

import com.demo.dto.request.StudentRequest;
import com.demo.dto.response.StudentResponse;
import com.demo.entity.Student;

public class StudentMapper {

    public static Student toEntity(StudentRequest request) {

        Student student = new Student();

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setDateOfBirth(request.getDateOfBirth());

        return student;
    }

    public static StudentResponse toResponse(Student student) {

        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getFirstName() + " " + student.getLastName())
                .email(student.getEmail())
                .phone(student.getPhone())
                .build();
    }

    public static void updateEntity(Student student, StudentRequest request) {

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setDateOfBirth(request.getDateOfBirth());
    }
}