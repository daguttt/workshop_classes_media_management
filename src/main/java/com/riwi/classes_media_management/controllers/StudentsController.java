package com.riwi.classes_media_management.controllers;

import com.riwi.classes_media_management.dtos.StudentDTO;
import com.riwi.classes_media_management.entities.Student;
import com.riwi.classes_media_management.services.StudentsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentsController {

    private final StudentsService studentsService;

    @Autowired
    public StudentsController(StudentsService studentsService) {
        this.studentsService = studentsService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        Student createdStudent = studentsService.createStudent(studentDTO);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentsById(@PathVariable Long id) {
        Optional<Student> students = studentsService.getStudentById(id);
        return students.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Student> getStudentsByEmail(@PathVariable String email) {
        Optional<Student> students = studentsService.getStudentByEmail(email);
        return students.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> studentsList = studentsService.getAllStudents();
        return ResponseEntity.ok(studentsList);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Student>> getAllActiveStudents() {
        List<Student> activeStudents = studentsService.getAllActiveStudents();
        return ResponseEntity.ok(activeStudents);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudents(@PathVariable Long id) {
        try {
            studentsService.deleteStudent(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
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
    public ResponseEntity<Void> updateStudentsStatus(@PathVariable Long id, @RequestParam Boolean active) {
        try {
            studentsService.updateStudentsStatus(id, active);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}