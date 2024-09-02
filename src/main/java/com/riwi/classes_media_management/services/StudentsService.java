package com.riwi.classes_media_management.services;

import com.riwi.classes_media_management.dtos.StudentDTO;
import com.riwi.classes_media_management.dtos.StudentResponseDTO;
import com.riwi.classes_media_management.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StudentsService {

    StudentResponseDTO updateStudentActiveStatus(Long id, Boolean active);

    Student createStudent(StudentDTO studentDTO);

    Student updateStudentsStatus(Long id, Boolean active);

    Optional<Student> getStudentById(Long id);

    List<Student> getAllActiveStudents();

    Student updateStudent(Long id, StudentDTO studentDTO);

    Page<StudentResponseDTO> getAllStudents(Pageable pageable);

    Page<StudentResponseDTO> getAllActiveStudents(Pageable pageable);
    boolean existsByEmail(String email);

}
