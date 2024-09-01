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
public class StudentServiceImpl implements StudentsService {

    private final StudentsRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentsRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(StudentDTO studentDTO) {
        Student student = Student.builder()
                .name(studentDTO.getName())
                .email(studentDTO.getEmail())
                .active(true)  // Asumiendo que un nuevo estudiante está activo por defecto
                .build();
        return studentRepository.save(student);
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
        Optional<Student> existingStudentOpt = studentRepository.findById(id);
        if (existingStudentOpt.isPresent()) {
            Student existingStudent = existingStudentOpt.get();
            existingStudent.setName(studentDTO.getName());
            existingStudent.setEmail(studentDTO.getEmail());
            // No se actualiza 'active' para no cambiar el estado
            return studentRepository.save(existingStudent);
        } else {
            throw new IllegalArgumentException("Student not found with id: " + id);
        }
    }

    @Override
    public void updateStudentsStatus(Long id, Boolean active) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setActive(active);
            studentRepository.save(student);
        } else {
            throw new IllegalArgumentException("Student not found with id: " + id);
        }
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
