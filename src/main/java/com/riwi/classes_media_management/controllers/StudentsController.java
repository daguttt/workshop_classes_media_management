package com.riwi.classes_media_management.controllers;

import com.riwi.classes_media_management.dtos.StudentDTO;
import com.riwi.classes_media_management.dtos.StudentResponseDTO;
import com.riwi.classes_media_management.entities.Student;
import com.riwi.classes_media_management.services.StudentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create a new student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student created successfully"),
            @ApiResponse(responseCode = "409", description = "Email already in use")
    })
    @PostMapping
    public ResponseEntity<String> createStudent(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Student data", required = true,
                    content = @Content(schema = @Schema(implementation = StudentDTO.class)))
            @Valid @RequestBody StudentDTO student) {
        if (studentsService.existsByEmail(student.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }
        studentsService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body("Student created successfully");
    }

    @Operation(summary = "Get student by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the student",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Student.class)) }),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(
            @Parameter(description = "ID of the student to be retrieved", required = true)
            @PathVariable Long id) {
        Optional<Student> student = studentsService.getStudentById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all students")
    @ApiResponse(responseCode = "200", description = "List of all students")
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> studentsList = studentsService.getAllActiveStudents();
        return ResponseEntity.ok(studentsList);
    }

    @Operation(summary = "Update a student by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student updated successfully",
                    content = @Content(schema = @Schema(implementation = Student.class))),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @Parameter(description = "ID of the student to be updated", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated student data", required = true,
                    content = @Content(schema = @Schema(implementation = StudentDTO.class)))
            @Valid @RequestBody StudentDTO studentDTO) {
        try {
            Student updatedStudent = studentsService.updateStudent(id, studentDTO);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update student's active status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Student status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStudentStatus(
            @Parameter(description = "ID of the student to update the status", required = true)
            @PathVariable Long id,
            @Parameter(description = "New active status", required = true)
            @RequestParam Boolean active) {
        try {
            studentsService.updateStudentsStatus(id, active);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Update and return the student's active status")
    @ApiResponse(responseCode = "200", description = "Student active status updated",
            content = @Content(schema = @Schema(implementation = StudentResponseDTO.class)))
    @PatchMapping("/{id}/active")
    public ResponseEntity<StudentResponseDTO> updateStudentActiveStatus(
            @Parameter(description = "ID of the student to update the active status", required = true)
            @PathVariable Long id,
            @Parameter(description = "New active status", required = true)
            @RequestParam Boolean active) {
        StudentResponseDTO updatedStudent = studentsService.updateStudentActiveStatus(id, active);
        return ResponseEntity.ok(updatedStudent);
    }

    @Operation(summary = "Get all students with pagination")
    @ApiResponse(responseCode = "200", description = "List of all students with pagination",
            content = @Content(schema = @Schema(implementation = Page.class)))
    @GetMapping
    public ResponseEntity<Page<StudentResponseDTO>> getAllStudents(
            @Parameter(description = "Pagination information") Pageable pageable) {
        Page<StudentResponseDTO> studentsPage = studentsService.getAllStudents(pageable);
        return ResponseEntity.ok(studentsPage);
    }

    @Operation(summary = "Get all active students with pagination")
    @ApiResponse(responseCode = "200", description = "List of all active students with pagination",
            content = @Content(schema = @Schema(implementation = Page.class)))
    @GetMapping("/active")
    public ResponseEntity<Page<StudentResponseDTO>> getAllActiveStudents(
            @Parameter(description = "Pagination information") Pageable pageable) {
        Page<StudentResponseDTO> activeStudentsPage = studentsService.getAllActiveStudents(pageable);
        return ResponseEntity.ok(activeStudentsPage);
    }
}
