package com.riwi.classes_media_management.controllers;

import com.riwi.classes_media_management.entities.Student;
import com.riwi.classes_media_management.services.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
