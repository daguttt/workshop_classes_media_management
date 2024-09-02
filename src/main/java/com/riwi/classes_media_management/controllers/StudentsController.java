package com.riwi.classes_media_management.controllers;

import com.riwi.classes_media_management.dtos.StudentDTO;
import com.riwi.classes_media_management.dtos.StudentResponseDTO;
import com.riwi.classes_media_management.entities.Student;
import com.riwi.classes_media_management.services.StudentsService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/students")
public class StudentsController {

    private final StudentsService studentsService;

    public StudentsController(StudentsService studentsService) {
        this.studentsService = studentsService;
    }

    @PostMapping
    public ResponseEntity<String> createStudent(@RequestBody StudentDTO student) {
        if (studentsService.existsByEmail(student.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }
        studentsService.createStudent(student); // Asume que tienes un método para crear el estudiante
        return ResponseEntity.status(HttpStatus.CREATED).body("Student created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> students = studentsService.getStudentById(id);
        return students.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> studentsList = studentsService.getAllActiveStudents();
        return ResponseEntity.ok(studentsList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDTO studentDTO) {
        try {
            Student updatedStudent = studentsService.updateStudent(id, studentDTO);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStudentStatus(@PathVariable Long id, @RequestParam Boolean active) {
        try {
            studentsService.updateStudentsStatus(id, active);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("/{id}/active")
    public ResponseEntity<StudentResponseDTO> updateStudentActiveStatus(@PathVariable Long id, @RequestParam Boolean active) {
        StudentResponseDTO updatedStudent = studentsService.updateStudentActiveStatus(id, active);
        return ResponseEntity.ok(updatedStudent);
    }


    @GetMapping
    public ResponseEntity<Page<StudentResponseDTO>> getAllStudents(Pageable pageable) {
        Page<StudentResponseDTO> studentsPage = studentsService.getAllStudents(pageable);
        return ResponseEntity.ok(studentsPage);
    }

    @GetMapping("/active")
    public ResponseEntity<Page<StudentResponseDTO>> getAllActiveStudents(Pageable pageable) {
        Page<StudentResponseDTO> activeStudentsPage = studentsService.getAllActiveStudents(pageable);
        return ResponseEntity.ok(activeStudentsPage);
    }
}