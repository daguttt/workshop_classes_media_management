package com.riwi.classes_media_management.services;

import com.riwi.classes_media_management.dtos.StudentDTO;
import com.riwi.classes_media_management.entities.Student;

import java.util.List;
import java.util.Optional;

public interface StudentsService {

    Student createStudent(StudentDTO studentDTO);

    Optional<Student> getStudentById(Long id);

    Optional<Student> getStudentByEmail(String email);

    List<Student> getAllStudents();

    List<Student> getAllActiveStudents();

    Student updateStudent(Long id, StudentDTO studentDTO);

    void deleteStudent(Long id);
}
