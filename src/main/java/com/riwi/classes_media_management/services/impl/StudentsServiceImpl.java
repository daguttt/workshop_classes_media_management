package com.riwi.classes_media_management.services.impl;

import com.riwi.classes_media_management.dtos.StudentDTO;
import com.riwi.classes_media_management.entities.Student;
import com.riwi.classes_media_management.repositories.StudentsRepository;
import com.riwi.classes_media_management.services.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentsServiceImpl implements StudentsService {

    private final StudentsRepository studentRepository;

    @Autowired
    public StudentsServiceImpl(StudentsRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(StudentDTO studentDTO) {
        return null;
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Optional<Student> getStudentByname(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Student> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> getAllActiveStudents() {
        return studentRepository.findByActiveTrue();
    }

    @Override
    public Student updateStudent(Long id, StudentDTO studentDTO) {
        return null;
    }

    @Override
    public void deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Student not found with id: " + id);
        }
    }
}
