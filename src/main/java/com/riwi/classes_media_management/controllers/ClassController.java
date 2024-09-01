package com.riwi.classes_media_management.controllers;

import com.riwi.classes_media_management.dtos.ClassDTO;
import com.riwi.classes_media_management.services.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/class")
public class ClassController {

    private final ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping("/active")
    public ResponseEntity<List<ClassDTO>> getActiveClasses() {
        List<ClassDTO> activeClasses = classService.getAllActiveClasses();
        return new ResponseEntity<>(activeClasses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassDTO> getClassById(@PathVariable Long id) {
        ClassDTO classDTO = classService.getClassById(id);
        if (classDTO != null) {
            return new ResponseEntity<>(classDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ClassDTO> createClass(@RequestBody ClassDTO classDTO) {
        ClassDTO createdClassDTO = classService.createClass(classDTO);
        return new ResponseEntity<>(createdClassDTO, HttpStatus.CREATED);
    }
}
