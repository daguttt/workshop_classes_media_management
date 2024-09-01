package com.riwi.classes_media_management.services;

import com.riwi.classes_media_management.dtos.StudentDTO;
import com.riwi.classes_media_management.entities.Student;

import java.util.List;
import java.util.Optional;

public interface StudentsService {

    Student createStudent(StudentDTO studentDTO);

    void updateStudentsStatus(Long id, Boolean active);

    Optional<Student> getStudentById(Long id);

    List<Student> getAllActiveStudents();

    Student updateStudent(Long id, StudentDTO studentDTO);

}
